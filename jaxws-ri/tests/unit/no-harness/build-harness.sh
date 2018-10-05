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

export WS_HARNESS_SRC=`pwd`/../../../../../ws-harness

if [ ! -d "$WS_HARNESS_SRC" ]; then
    svn co https://svn.java.net/svn/ws-test-harness~svn/trunk $WS_HARNESS_SRC
fi;
cd $WS_HARNESS_SRC/test-harness
mvn clean install -DskipTests=true

cd $WS_HARNESS_SRC/harness-maven-plugin
mvn clean install -DskipTests=true
