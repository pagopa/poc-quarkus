#!/bin/bash

mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.native.builder-image=graalbase

docker build -f src/main/docker/Dockerfile.native -t quarkus/poc-quarkus-native:native-aarch64 .