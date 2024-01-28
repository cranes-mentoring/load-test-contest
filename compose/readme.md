Links:

actuator:
http://localhost:8084/actuator/prometheus

pg admin:
http://localhost:8080/?pgsql=db&username=postgres&db=postgres&ns=public

prom:
http://localhost:9090

grafana:
http://localhost:3000

count 200: rate(http_server_requests_seconds_count{outcome="SUCCESS"}[$__rate_interval])
count error: rate(http_server_requests_seconds_count{outcome="SERVER_ERROR"}[$__rate_interval])
r2dbc: 

nginx:
curl -X GET http://localhost/api/v1/stocks

test:
curl -X GET http://localhost:8084/api/v1/stocks