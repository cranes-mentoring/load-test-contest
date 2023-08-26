package email

import (
	"context"

	"stock-grpc-service/internal/models"
)

// UseCase Email use case interface
type UseCase interface {
	Create(ctx context.Context, stocks *models.Stock) error
	GetByID(ctx context.Context, code string) (*models.Stock, error)
}
