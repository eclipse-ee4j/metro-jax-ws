#!/bin/bash
#
# Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0, which is available at
# http://www.eclipse.org/org/documents/edl-v10.php.
#
# SPDX-License-Identifier: BSD-3-Clause
#

export JAKE_HOME=`pwd`/../../../../../jake
export WS_RI_SRC=`pwd`/../../../
export NO_HARNESS=`pwd`

#. build-harness.sh

cd $NO_HARNESS
. build-tests.sh $1

cd $NO_HARNESS
. run-tests.sh $1
