#!/bin/bash
#This script must be run with architecture x86_64
mvn package -Pnative \
-Dquarkus.native.container-build=true \
-Dquarkus.container-image.build=true \
-Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.2-java11

docker build -f src/main/docker/Dockerfile.native -t quarkus/poc-quarkus-native:native-amd64 .