package grpc

import (
	"context"
	stock "stock-grpc-service/internal"
	"stock-grpc-service/internal/models"
	pb "stock-grpc-service/proto"
	stocks "stock-grpc-service/proto"
)

// Server is used to implement stocks.UnimplementedStocksServiceServer.
type Server struct {
	pb.UnimplementedStocksServiceServer
	stockUC stock.UseCase
}

// NewStockGRPCService stock gRPC service constructor
func NewStockGRPCService(emailUC stock.UseCase) *Server {
	return &Server{stockUC: emailUC}
}

func (e *Server) Save(ctx context.Context, request *stocks.SaveRequest) (*stocks.SaveResponse, error) {
	model := request.Stock

	stockDto := &models.Stock{
		ID:          nil,
		Price:       model.Price,
		Name:        model.Name,
		Description: model.Description,
	}

	err := e.stockUC.Create(ctx, stockDto)
	if err != nil {
		return nil, err
	}

	return &stocks.SaveResponse{Code: "ok"}, nil
}

func (e *Server) Find(ctx context.Context, request *stocks.FindRequest) (*stocks.FindResponse, error) {
	code := request.GetByName().GetName()

	model, err := e.stockUC.GetByID(ctx, code)
	if err != nil {
		return nil, err
	}

	response := &stocks.FindResponse{Stock: &stocks.Stock{
		Name:        model.Name,
		Price:       model.Price,
		Description: model.Description,
	}}

	return response, nil
}
