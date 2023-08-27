package email

import (
	"context"

	"stock-grpc-service/internal/models"
)

// PGRepository Email postgresql repository interface
type PGRepository interface {
	Create(ctx context.Context, stock *models.Stock) (*models.Stock, error)
	GetByID(ctx context.Context, code string) (*models.Stock, error)
}
