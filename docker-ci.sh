#!/bin/bash

./mvnw package

docker build --no-cache -f src/main/docker/Dockerfile.jvm -t quarkus/denis-back .
docker tag quarkus/denis-back denisdartcontainerregistry.azurecr.io/quarkus/denis-back
docker push denisdartcontainerregistry.azurecr.io/quarkus/denis-back