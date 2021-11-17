#!/bin/sh
mvn package -DskipTests=true
mkdir -p /workspaces/quizweb/build/deploy
cp -Pf /workspaces/quizweb/target/quizWeb.war /workspaces/quizweb/build/deploy/quizWeb.war