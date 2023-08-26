package usecase

import (
	"context"
	email "stock-grpc-service/internal"

	"github.com/pkg/errors"
	"stock-grpc-service/internal/models"
)

type StockUseCase struct {
	pgrep email.PGRepository
}

// NewStockUseCase email use case constructor
func NewStockUseCase(emailPGRepo email.PGRepository) *StockUseCase {
	return &StockUseCase{pgrep: emailPGRepo}
}

// Create create new email saves in db.
func (e *StockUseCase) Create(ctx context.Context, stock *models.Stock) error {
	_, err := e.pgrep.Create(ctx, stock)
	if err != nil {
		return errors.Wrap(err, "pgrep.Create")
	}

	return nil
}

// GetByID find email by id.
func (e *StockUseCase) GetByID(ctx context.Context, code string) (*models.Stock, error) {
	stock, err := e.pgrep.GetByID(ctx, code)
	if err != nil {
		return nil, errors.Wrap(err, "pgrep.GetByID")
	}
	return stock, nil
}
