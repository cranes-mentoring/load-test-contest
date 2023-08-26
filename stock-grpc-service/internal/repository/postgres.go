package repository

import (
	"context"
	"github.com/jackc/pgx/v4/pgxpool"
	"github.com/pkg/errors"
	"stock-grpc-service/internal/models"
)

const (
	createQuery = `INSERT INTO stocks (name, price, description) 
					VALUES ($1, $2, $3) RETURNING id, name, price, description`
	getByCodeQuery = `SELECT id, name, price, descriptio FROM stocks WHERE name = $1`
	getAllQuery    = `SELECT id, name, price, descriptio FROM stocks`
)

type PGRepository struct {
	db *pgxpool.Pool
}

// NewPGRepository Email postgresql repository constructor
func NewPGRepository(db *pgxpool.Pool) *PGRepository {
	return &PGRepository{db: db}
}

// Create new stock.
func (e *PGRepository) Create(ctx context.Context, stock *models.Stock) (*models.Stock, error) {

	var stockDto models.Stock
	if err := e.db.QueryRow(
		ctx,
		createQuery,
		&stock.Name,
		&stock.Price,
		&stock.Description,
	).Scan(&stock.Name, &stock.Price, &stock.Description); err != nil {
		return nil, errors.Wrap(err, "Scan")
	}

	return &stockDto, nil
}

// GetByID get stock by code
func (e *PGRepository) GetByID(ctx context.Context, code string) (*models.Stock, error) {

	var stock models.Stock
	if err := e.db.QueryRow(ctx, getByCodeQuery, code).Scan(
		&stock.ID, &stock.Name, &stock.Price, &stock.Description,
	); err != nil {
		return nil, errors.Wrap(err, "Scan")
	}

	return &stock, nil
}
