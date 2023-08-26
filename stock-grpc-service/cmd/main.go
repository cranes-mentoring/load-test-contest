package main

import (
	"github.com/labstack/gommon/log"
	config "stock-grpc-service/cfg"
	"stock-grpc-service/internal/server"
	"stock-grpc-service/pkg/postgres"
)

func main() {
	cfg, err := config.ParseConfig()
	if err != nil {
		log.Fatal(err)
	}
	pgxPool, err := postgres.NewPgxConn(cfg)
	if err != nil {
		log.Fatalf("NewPgxConn: %+v", err)
	}

	s := server.NewServer(cfg, pgxPool)
	log.Fatal(s.Run())
}
