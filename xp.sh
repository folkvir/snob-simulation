#!/usr/bin/env bash
sh install.sh

mvn exec:java@snob-50
mvn exec:java@snob-100
mvn exec:java@snob-200