#!/bin/bash -x
#
# Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0, which is available at
# http://www.eclipse.org/org/documents/edl-v10.php.
#
# SPDX-License-Identifier: BSD-3-Clause
#

function initdir() {
    if [ -d "$1" ]; then
        rm -rf $1
    fi;

    mkdir -p $1
}

function initlib() {
    cp ../lib/ext/$1.jar SHARED/lib
    cp ../lib/ext/$1.jar SHARED/classes
    cd SHARED/classes
    jar -xvf $1.jar
    rm -rf $1.jar
    cd ../..
}

initdir SHARED/classes
initdir SHARED/lib

initlib jaxwsTestUtil
initlib xmlunit1.0

