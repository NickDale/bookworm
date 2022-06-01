@rem Helper script for managing docker containers
@echo off

pushd docker
if "%1" == "start" (
    set COMPOSE_PROJECT_NAME="bookworm"
    docker-compose up -d --build
) else if "%1" == "prune" (
    docker-compose rm -fsv
) else if "%1" == "stop" (
    docker-compose rm -fsv
) else (
    echo Unknown command: %1 ^(start, prune, stop^)
)
popd
