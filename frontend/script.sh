#!/bin/bash

SERVICE_NAME="frontend"

docker build -f Dockerfile -t iriquelme/${SERVICE_NAME}:latest .
docker push iriquelme/${SERVICE_NAME}:latest