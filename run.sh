#!/bin/bash
source /etc/profile.d/maven.sh
source /etc/profile.d/authform.sh
cd /root/authentication-form
git pull
mvn clean install
mvn spring-boot:run