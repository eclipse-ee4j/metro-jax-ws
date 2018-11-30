@echo off

REM
REM  Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
REM
REM  This program and the accompanying materials are made available under the
REM  terms of the Eclipse Distribution License v. 1.0, which is available at
REM  http://www.eclipse.org/org/documents/edl-v10.php.
REM
REM  SPDX-License-Identifier: BSD-3-Clause
REM

rem
rem Infer JAXWS_HOME if not set
rem
if not "%JAXWS_HOME%" == "" goto CHECKJAVAHOME

rem Try to locate JAXWS_HOME
set JAXWS_HOME=%~dp0
set JAXWS_HOME=%JAXWS_HOME%\..
if exist %JAXWS_HOME%\lib\jaxws-tools.jar goto CHECKJAVAHOME

rem Unable to find it
echo JAXWS_HOME must be set before running this script
goto END

:CHECKJAVAHOME
if not "%JAVA_HOME%" == "" goto USE_JAVA_HOME

set JAVA=java
goto LAUNCH

:USE_JAVA_HOME
set JAVA="%JAVA_HOME%\bin\java"
goto LAUNCH

:LAUNCH
%JAVA% %WSGEN_OPTS% -cp "%JAXWS_HOME%\lib\jaxws-tools.jar" com.sun.tools.ws.WsGen %*

:END
%COMSPEC% /C exit %ERRORLEVEL%
