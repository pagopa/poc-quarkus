#!/bin/bash

docker build -f dockerOrigin/Dockerfile.graalbase -t graalbase .

mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=graalbase #-Dquarkus.profile=docker-native-local

docker build -f dockerOrigin/Dockerfile.native -t quarkus/poc-quarkus-native:local .