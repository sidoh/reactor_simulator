#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [ -z $(which thrift) ]; then
  echo "Could not find thrift compiler. Please ensure that it is in your PATH."
  exit 1
fi

for thrift_file in $(ls $DIR/../thrift/**.thrift); do
  thrift --gen java -out $DIR/../java $thrift_file
done

git add $DIR/../java/org/sidoh/reactor_simulator/thrift
