#!/bin/bash

# clean will remove the datastore indexes, not use it.
# mvn clean install

# ~/appsdk/bin/appcfg.sh -A uclac3s-seminars update target/seminars-1.0
export JAVA_HOME=/usr/lib/jvm/java-7-oracle
mvn appengine:update
