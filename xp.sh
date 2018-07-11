#!/usr/bin/env bash
mvn clean
mvn package

mvn exec:java@snob-50
mvn exec:java@snob-100
mvn exec:java@snob-200