#!/bin/bash

sed -i '' "s/127.0.0.1/host.docker.internal/g" src/main/resources/application.properties

docker build -f src/main/docker/Dockerfile.graalbase -t graalbase .

mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.native.builder-image=graalbase

docker build -f src/main/docker/Dockerfile.native -t quarkus/poc-quarkus-native:native-aarch64 .

sed -i '' "s/host.docker.internal/127.0.0.1/g" src/main/resources/application.properties