#!/bin/bash

unamestr=`uname`
if [[ "$unamestr" == 'Linux' ]]; then
  # linux
  export JAVA_HOME=/usr/lib/jvm/java-7-oracle
else
  # mac osx
  export JAVA_HOME=$(/usr/libexec/java_home -v 1.7)
fi

echo "JAVA_HOME (1.7): " $JAVA_HOME

# clean will remove the datastore indexes, not use it.
# mvn clean install

# ~/appsdk/bin/appcfg.sh -A uclac3s-seminars update target/seminars-1.0

mvn appengine:update
