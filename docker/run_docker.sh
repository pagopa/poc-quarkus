# sh ./run_docker.sh <local|dev|uat|prod>

ENV=$1

if [ -z "$ENV" ]
then
  ENV="local"
  echo "No environment specified: local is used."
fi


if [ "$ENV" = "local" ]; then
  ENVREG="dev"
  containerRegistry="pagopadcommonacr.azurecr.io"
  echo "Running local image and dev dependencies"
else

  if [ "$ENV" = "dev" ]; then
    containerRegistry="pagopadcommonacr.azurecr.io"
    ENVREG=$ENV
    echo "Running all dev images"
  else
    echo "Error with parameter: use <local|dev>"
    exit 1
  fi
fi

pip3 install yq
repository=$(yq -r '."microservice-chart".image.repository' ../helm/values-$ENVREG.yaml)
image="${repository}:latest"

export containerRegistry=${containerRegistry}
export image=${image}

stack_name=$(cd .. && basename "$PWD")
docker-compose -p "${stack_name}" -e ORGANIZATIONS_TABLE=organizations up -d --remove-orphans --force-recreate
