#!/bin/bash

docker-compose up -d --remove-orphans --force-recreate

printf 'Containers started'