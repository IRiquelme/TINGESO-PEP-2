#!/bin/bash

SERVICE_NAME="config-service"

chmod +x mvnw
./mvnw clean package -DskipTests
docker build -f Dockerfile -t iriquelme/${SERVICE_NAME}:latest .
docker push iriquelme/${SERVICE_NAME}:latest
