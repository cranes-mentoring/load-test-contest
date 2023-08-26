package server

import (
	"context"
	grpc_middleware "github.com/grpc-ecosystem/go-grpc-middleware"
	"github.com/labstack/echo/v4"
	"log"
	"net"
	"os"
	"os/signal"
	"stock-grpc-service/internal/repository"
	"stock-grpc-service/internal/usecase"
	prt "stock-grpc-service/proto"
	"syscall"
	"time"

	config "stock-grpc-service/cfg"
	grpsService "stock-grpc-service/internal/service"

	"github.com/jackc/pgx/v4/pgxpool"
	"github.com/pkg/errors"
	"google.golang.org/grpc"
	"google.golang.org/grpc/keepalive"

	grpcrecovery "github.com/grpc-ecosystem/go-grpc-middleware/recovery"
	grpc_ctxtags "github.com/grpc-ecosystem/go-grpc-middleware/tags"
	grpc_opentracing "github.com/grpc-ecosystem/go-grpc-middleware/tracing/opentracing"
	grpc_prometheus "github.com/grpc-ecosystem/go-grpc-prometheus"
)

type Server struct {
	cfg     *config.Config
	pgxPool *pgxpool.Pool
	echo    *echo.Echo
}

// NewServer constructor
func NewServer(
	cfg *config.Config,
	pgxPool *pgxpool.Pool,
) *Server {
	return &Server{cfg: cfg, pgxPool: pgxPool, echo: echo.New()}
}

// Run start application
func (s *Server) Run() error {
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	l, err := net.Listen("tcp", s.cfg.GRPC.Port)
	if err != nil {
		return errors.Wrap(err, "net.Listen")
	}
	defer l.Close()

	grpcServer := grpc.NewServer(
		grpc.KeepaliveParams(keepalive.ServerParameters{
			MaxConnectionIdle: s.cfg.GRPC.MaxConnectionIdle * time.Minute,
			Timeout:           s.cfg.GRPC.Timeout * time.Second,
			MaxConnectionAge:  s.cfg.GRPC.MaxConnectionAge * time.Minute,
			Time:              s.cfg.GRPC.Timeout * time.Minute,
		}),
		grpc.UnaryInterceptor(grpc_middleware.ChainUnaryServer(
			grpc_ctxtags.UnaryServerInterceptor(),
			grpc_opentracing.UnaryServerInterceptor(),
			grpc_prometheus.UnaryServerInterceptor,
			grpcrecovery.UnaryServerInterceptor(),
		),
		),
	)

	repo := repository.NewPGRepository(s.pgxPool)
	stockUC := usecase.NewStockUseCase(repo)
	stocksServiceServer := grpsService.NewStockGRPCService(stockUC)

	prt.RegisterStocksServiceServer(grpcServer, stocksServiceServer)
	grpc_prometheus.Register(grpcServer)

	log.Printf("GRPC Server is listening on port: %s", s.cfg.GRPC.Port)
	log.Println(grpcServer.Serve(l))

	quit := make(chan os.Signal, 1)
	signal.Notify(quit, os.Interrupt, syscall.SIGTERM)

	select {
	case v := <-quit:
		log.Printf("signal.Notify: %v", v)
	case done := <-ctx.Done():
		log.Printf("ctx.Done: %v", done)
	}

	if err := s.echo.Server.Shutdown(ctx); err != nil {
		return errors.Wrap(err, "echo.Server.Shutdown")
	}

	grpcServer.GracefulStop()
	log.Printf("Server Exited Properly")

	return nil
}
