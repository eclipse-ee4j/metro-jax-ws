REM
REM  Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
REM
REM  This program and the accompanying materials are made available under the
REM  terms of the Eclipse Distribution License v. 1.0, which is available at
REM  http://www.eclipse.org/org/documents/edl-v10.php.
REM
REM  SPDX-License-Identifier: BSD-3-Clause
REM

xjc -httpproxy www-proxy.us.oracle.com:80 -d ../../ -p bugs.jaxws620.client.soap11 http://schemas.xmlsoap.org/soap/envelope/
