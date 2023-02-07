#!/bin/bash

docker build -f docker/Dockerfile.graalbase -t graalbase .

mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=graalbase -Dquarkus.profile=docker-native-local

docker build -f docker/Dockerfile.native -t quarkus/poc-quarkus-native:local .