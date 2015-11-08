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

mvn appengine:devserver
