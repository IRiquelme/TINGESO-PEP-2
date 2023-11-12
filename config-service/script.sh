#!/bin/bash

SERVICE_NAME="config-server"

docker build -f Dockerfile -t iriquelme/${SERVICE_NAME}:latest .
docker push iriquelme/${SERVICE_NAME}:latest