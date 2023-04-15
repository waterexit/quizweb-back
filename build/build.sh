#!/bin/sh
mvn clean
mvn package -DskipTests=true
# mvn package spring-boot:repackage -DskipTests=true
rm -rf /workspaces/quizweb/build/deploy/quizWeb.war
mkdir -p /workspaces/quizweb/build/deploy
cp -Pf /workspaces/quizweb/target/quizWeb.war /workspaces/quizweb/build/deploy/quizWeb.war