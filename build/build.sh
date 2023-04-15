#!/bin/sh
mvn package -DskipTests=true
# mvn package spring-boot:repackage -DskipTests=true
mkdir -p /workspaces/quizweb/build/deploy
cp -Pf /workspaces/quizweb/target/quizWeb.war /workspaces/quizweb/build/deploy/quizWeb.war