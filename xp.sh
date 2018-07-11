#!/usr/bin/env bash
mvn package

mvn exec:java@snob-50
mvn exec:java@snob-100
mvn exec:java@snob-200