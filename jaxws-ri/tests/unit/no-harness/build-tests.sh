#!/bin/bash -e
#
# Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0, which is available at
# http://www.eclipse.org/org/documents/edl-v10.php.
#
# SPDX-License-Identifier: BSD-3-Clause
#

# pass as parametere what test should be run (all by default), i.e. fromjava/nosei
TESTS=$1

export WS_RI_SRC=`pwd`/../../../

sh -x prepare.sh

cd ..

export JAVA_HOME=$JAVA8_HOME
export PATH=$JAVA_HOME/bin:$PATH

#export DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005
#-Dws.jvmOpts=$DEBUG \

export MAVEN_OPTS=
mvn -o clean test \
  -P jaxwsInJDK9 \
  -Dws.args=-generateTestSources \
  -Dws.test=testcases/$TESTS 2>&1 |tee no-harness/`date +%Y-%m-%d_%H%M`-harness-run.txt
#  -Dws.jvmOpts=$DEBUG \

