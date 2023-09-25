#!/bin/sh
#
#  demo.sh
#
# Created by Chaman Lal Sabharwal on 10/15/19.
#To execute it, on commandline type
# it is generic, add the name of jar file to execute
# (no jar with Demo
# sh demo.sh Demo
# to create fresh jar file
# Change the name Demo to name of your program driver

if [ "$1" != "" ]; then
# save the original Demo.jar
cp $1.jar DemoOriginal.jar
#
# Open the jar file
jar -xf $1.jar
#
# remove the jar file
rm -r $1.jar
#
# remove all code except the source code
rm -r code
rm -r docs
#
  echo "javac -d . source/*.java"

# compile
javac -d . source/*.java
#
# create java style documentation in Docs directory
javadoc -d docs -version -author -private -quiet source/*.java
#
# create jar file
#jar -cfm $1.jar source/$1.txt code source
jar -cfm $1.jar source/$1.txt *
#
# execute
java -cp "data/*" -jar $1.jar
#
else
    echo "Parameter 1 is empty"
fi
