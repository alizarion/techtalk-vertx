#!/usr/bin/env bash

RED='\033[0;31m'
NC='\033[0m' # No Color
YELLOW='\033[0;33m'
BLUE='\033[0;34m'

PROJECT=techalk-vertx

echo -e "${BLUE}Creating project ${PROJECT} ${NC}"
oc new-project ${PROJECT}
echo -e "${BLUE}Adding permissions to ${PROJECT} ${NC}"
oc policy add-role-to-user view admin -n ${PROJECT}
oc policy add-role-to-user view -n ${PROJECT} -z default
oc policy add-role-to-group view system:serviceaccounts -n ${PROJECT}

echo -e "${BLUE}Deploying Redis ${NC}"
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $DIR
oc create -f ${DIR}/templates/redis/redis.yaml
oc create -f ${DIR}/templates/redis/redis-rc.yaml
oc create -f ${DIR}/templates/redis/redis-service.yaml