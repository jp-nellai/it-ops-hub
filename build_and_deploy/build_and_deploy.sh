#!/bin/bash
# build-and-up.sh
set -e

cd ..

# Run Maven build on host
mvn clean package -DskipTests

# Start docker-compose services
docker-compose up --build