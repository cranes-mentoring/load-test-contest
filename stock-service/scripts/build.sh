# move to common dir
cd ..
# build project
./gradlew clean build
# delete old
docker rmi ere/stock-service:latest
# build docker
docker build -t ere/stock-service .
