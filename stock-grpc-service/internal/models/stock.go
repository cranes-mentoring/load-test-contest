package models

// Stock – base dto
type Stock struct {
	ID          *int64  `json:"Id"`
	Price       float32 `json:"Price"`
	Name        string  `json:"Name"`
	Description string  `json:"Description"`
}
