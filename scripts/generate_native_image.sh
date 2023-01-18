#!/bin/bash

docker build -f src/main/docker/Dockerfile.graalbase -t graalbase .

mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.native.builder-image=graalbase -Dquarkus.profile=docker-native

docker build -f src/main/docker/Dockerfile.native -t quarkus/poc-quarkus-native:native-aarch64 .