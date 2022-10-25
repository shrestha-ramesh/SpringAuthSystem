#!/bin/bash
echo "removing docker image app"
docker image rm app

echo "clean build project ignore test cases"
bash gradlew clean build -x test

echo "build a image with app name"
docker image build -t app:1.0 .
#docker run --rm -ti --name=app -p 8080:8080 app