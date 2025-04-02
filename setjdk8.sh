#!/bin/bash

# Set Java 8 home directory
export JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home

# Add Java 8 to the PATH
export PATH=$JAVA_HOME/bin:$PATH

# Set Java 8 as the default Java version
export JAVA_VERSION=1.8

# Print confirmation
echo "Java 8 environment has been set up:"
echo "JAVA_HOME: $JAVA_HOME"
echo "Java version:"
java -version
