#!/usr/bin/env bash

export BUSROUTE_MYSQL_DATABASE=busroute 
export BUSROUTE_MYSQL_USERNAME=busroute 
export BUSROUTE_MYSQL_PASSWORD=etuorsub123
baseUrl="http://localhost:8088"

# This script will run under bus_route_challenge folder irrespective of where it was invoked from
cd $(dirname $0)

dev_build() {
  echo "Building..."
  mvn package
}

dev_run() {
  echo "Running..."
  #MVN_RUN_ARGUMENTS=""
  #for CURR_ARG in "$@"
  #do
  #  MVN_RUN_ARGUMENTS="${MVN_RUN_ARGUMENTS}${CURR_ARG},"
  #done
  mvn spring-boot:run -Drun.arguments="importPath,$1"

  # this will never get executed, 
  # you need to delete existing data and reimport at execution time.
  
  
  #curl --data-urlencode "file_path={$1}" "${baseUrl}/api/import"
  #mvn spring-boot:run -Drun.arguments="${MVN_RUN_ARGUMENTS}"
}

dev_smoke() {
  if _run_smoke; then
    echo "Tests Passed"
    exit 0
  else
    echo "Tests Failed"
    exit 1
  fi
}

_run_smoke() {
  echo "Running smoke tests on $baseUrl..." && \
    (curl -fsS "$baseUrl/api/direct?dep_sid=3&arr_sid=4" | grep -E 'true|false') && \
    (curl -fsS "$baseUrl/api/direct?dep_sid=0&arr_sid=1" | grep -E 'true|false')
}

docker_build() {
  # to rebuild --no-cache 
  docker build --no-cache -t goeuro:devtest .
}

docker_run() {
  echo "Starting MySQL..."
  docker run --name busroutemysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_DATABASE=$BUSROUTE_MYSQL_DATABASE -e MYSQL_USER=$BUSROUTE_MYSQL_USERNAME -e MYSQL_PASSWORD=$BUSROUTE_MYSQL_PASSWORD -d mysql:latest
  docker run --rm -it --name busrouteapp --link busroutemysql:mysql -p 8088:8088 goeuro:devtest
}

docker_smoke() {
  echo "Waiting 10 seconds for service to start..."
  sleep 10
  docker exec busrouteapp /src/service.sh dev_smoke
  retval=$?
  docker rm -f busrouteapp
  exit $retval
}

usage() {
  cat <<EOF
Usage:
  $0 <command> <args>
Local machine commands:
  dev_build        : builds and packages your app
  dev_run <file>   : starts your app in the foreground
  dev_smoke        : runs our integration test suite on localhost
Docker commands:
  docker_build     : packages your app into a docker image
  docker_run       : runs your app using a docker image
  docker_smoke     : runs same smoke tests inside a docker container
EOF
}

action=$1
action=${action:-"usage"}
action=${action/help/usage}
shift
if type -t $action >/dev/null; then
  echo "Invoking: $action"
  $action $*
else
  echo "Unknown action: $action"
  usage
  exit 1
fi
