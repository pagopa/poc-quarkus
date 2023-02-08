stack_name=$(cd .. && basename "$PWD")
docker-compose -p "${stack_name}" up -d --remove-orphans --force-recreate

echo 'Containers started'