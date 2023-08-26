package cfg

import (
	"log"
	"os"
	"time"

	"github.com/spf13/viper"
)

// Config microservice config
type Config struct {
	AppVersion string
	GRPC       GRPC
	PostgreSQL PostgreSQL
}

// PostgreSQL config
type PostgreSQL struct {
	PostgresqlHost     string
	PostgresqlPort     string
	PostgresqlUser     string
	PostgresqlPassword string
	PostgresqlDBName   string
	PostgresqlSSLMode  string
	PgDriver           string
}

// GRPC gRPC service config
type GRPC struct {
	Port              string
	MaxConnectionIdle time.Duration
	Timeout           time.Duration
	MaxConnectionAge  time.Duration
}

func exportConfig() error {
	viper.SetConfigType("yaml")
	viper.AddConfigPath("./cfg")
	viper.SetConfigName("config.yaml")

	if err := viper.ReadInConfig(); err != nil {
		return err
	}
	return nil
}

// ParseConfig Parse config file
func ParseConfig() (*Config, error) {
	if err := exportConfig(); err != nil {
		return nil, err
	}

	var c Config
	err := viper.Unmarshal(&c)
	if err != nil {
		log.Printf("unable to decode into struct, %v", err)
		return nil, err
	}

	postgresPORT := os.Getenv(POSTGRES_HOST)
	if postgresPORT != "" {
		c.PostgreSQL.PostgresqlHost = postgresPORT
	}
	postgresHost := os.Getenv(POSTGRES_HOST)
	if postgresHost != "" {
		c.PostgreSQL.PostgresqlHost = postgresHost
	}
	postgresqlPort := os.Getenv(POSTGRES_PORT)
	if postgresqlPort != "" {
		c.PostgreSQL.PostgresqlPort = postgresqlPort
	}
	postgresUser := os.Getenv(POSTGRES_USER)
	if postgresUser != "" {
		c.PostgreSQL.PostgresqlUser = postgresUser
	}
	postgresPassword := os.Getenv(POSTGRES_PASSWORD)
	if postgresPassword != "" {
		c.PostgreSQL.PostgresqlPassword = postgresPassword
	}
	postgresDB := os.Getenv(POSTGRES_DB)
	if postgresDB != "" {
		c.PostgreSQL.PostgresqlDBName = postgresDB
	}
	postgresSSL := os.Getenv(POSTGRES_SSL)
	if postgresSSL != "" {
		c.PostgreSQL.PostgresqlSSLMode = postgresSSL
	}

	gRPCPort := os.Getenv(GRPC_PORT)
	if gRPCPort != "" {
		c.GRPC.Port = gRPCPort
	}

	return &c, nil
}
