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

rm -rf async/
rm -rf benchmark/
rm -rf bugs/
rm -rf client/
rm -rf customization/
rm -rf epr/
rm -rf fromjava/
rm -rf fromwsdl/
rm -rf handler/
rm -rf mtom/
rm -rf provider/
rm -rf server/
rm -rf whitebox/
rm -rf wsa/
rm -rf wsimport/
rm -rf xop/

rm -rf runall
rm -rf runall-failed

G_STATUS=0
wget http://127.0.0.1:8888/stop

echo "==================================================================== global variables ============"
echo "      G_STATUS=[$G_STATUS] ~ result so far of current testcase"
echo "      useagent=[$useagent] ~ if true then java and tools use aggent "
echo "         debug=[$debug] ~ silent / verbose mode"
echo "      failFast=[$failFast] ~ if true it won't continue with other tests for given webservice"
echo "     skipTests=[$skipTests] ~ run tests just up to deploy / all (inncluding client tests)"
echo "useNamedModule=[$useNamedModule] ~ run as unnamed module / named (including module-info.java)"
echo "=================================================================================================="

