cd .. && cd stock-grpc-service/proto

ghz --insecure \
  --proto ./stocks.proto \
  --call stocks.StocksService.Save \
  -d '{"stocks": { "name":"APPL", "price": "1.3", "description": "apple stocks"} }' \
  -n 2000 \
  -c 20 \
  --connections=10 \
  0.0.0.0:5007