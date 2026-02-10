#!/bin/bash -ex
#
# Copyright (c) 2024 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0, which is available at
# http://www.eclipse.org/org/documents/edl-v10.php.
#
# SPDX-License-Identifier: BSD-3-Clause
#

[[ -z ${1} ]] && SUMMARY_FILE_NAME='SUMMARY.TXT' || SUMMARY_FILE_NAME=${1}

EE4J_PARENT_URL='https://repo1.maven.org/maven2/org/eclipse/ee4j/project/1.0.9/project-1.0.9.pom'

TCK_NAME=xml-ws-tck

# Download and extract TCK tests
wget -q ${JAXWS_TCK_BUNDLE} --no-check-certificate -O ${TCK_NAME}.zip && unzip ${TCK_NAME}.zip
mkdir ${TCK_NAME}/JTwork
mkdir ${TCK_NAME}/JTreport

# Download JAXWS API dependencies from Maven Central
mkdir 'jaxws'
mkdir 'ri-download'
echo '<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.eclipse.ee4j</groupId>
    <artifactId>project</artifactId>
    <version>1.0.9</version>
  </parent>
  <groupId>download</groupId>
  <artifactId>ri</artifactId>
  <version>1.0.0</version>
  <dependencies>
    <dependency>
      <groupId>com.sun.xml.messaging.saaj</groupId>
      <artifactId>saaj-impl</artifactId>
      <version>'${SAAJ_RI_VERSION}'</version>
    </dependency>
    <dependency>
      <groupId>com.sun.xml.ws</groupId>
      <artifactId>jaxws-ri</artifactId>
      <type>zip</type>
      <exclusions>
        <exclusion>
             <groupId>jakarta.xml.ws</groupId>
             <artifactId>jakarta.xml.ws-api</artifactId>
        </exclusion>
        <exclusion>
             <groupId>jakarta.xml.soap</groupId>
             <artifactId>jakarta.xml.soap-api</artifactId>
        </exclusion>
      </exclusions>
      <version>'${JAXWS_RI_VERSION}'</version>
    </dependency>
    <dependency>
      <groupId>org.glassfish.metro</groupId>
      <artifactId>webservices-osgi</artifactId>
	  <version>'${WSIT_RI_VERSION}'</version>
    </dependency>
    <dependency>
      <groupId>jakarta.xml.ws</groupId>
      <artifactId>jakarta.xml.ws-api</artifactId>
      <version>${JAXWS_API_VERSION}</version>
    </dependency>
    <dependency>
      <groupId>jakarta.xml.soap</groupId>
      <artifactId>jakarta.xml.soap-api</artifactId>
      <version>${SAAJ_API_VERSION}</version>
    </dependency>
  </dependencies>
</project>
' > 'ri-download/pom.xml'

mvn -f ri-download/pom.xml \
    -Psnapshots \
    -DoutputDirectory="${WORKSPACE}/jaxws" \
    -Dmdep.stripVersion=true \
    org.apache.maven.plugins:maven-dependency-plugin:3.1.2:copy-dependencies

# Download and extract Tomcat
wget -q ${TOMCAT_URL} --no-check-certificate -O - | tar xfz -

AS_API_NAME='apache-tomcat-api'
AS_RI_NAME='apache-tomcat-ri'

mv apache-tomcat-* ${AS_API_NAME}
cp -a ${AS_API_NAME} ${AS_RI_NAME}

sed -i 's/8080/9080/g' ${AS_RI_NAME}/conf/server.xml
sed -i 's/8181/9181/g' ${AS_RI_NAME}/conf/server.xml
sed -i 's/8005/9005/g' ${AS_RI_NAME}/conf/server.xml
sed -i 's/8443/9443/g' ${AS_RI_NAME}/conf/server.xml

# Define local variables
CATALINA_API_HOME="${WORKSPACE}/${AS_API_NAME}"
CATALINA_API_DEPLOY_DIR="${CATALINA_API_HOME}/webapps"
CATALINA_API_LIB_HOME="${CATALINA_API_HOME}/shared/lib"

CATALINA_RI_HOME="${WORKSPACE}/${AS_RI_NAME}"
CATALINA_RI_DEPLOY_DIR="${CATALINA_RI_HOME}/webapps"
CATALINA_RI_LIB_HOME="${CATALINA_RI_HOME}/shared/lib"

TS_HOME="${WORKSPACE}/${TCK_NAME}"
TS_WORK_DIR="${TS_HOME}/JTwork"
TS_REPORT_DIR="${TS_HOME}/JTreport"
JAXWS_HOME="${WORKSPACE}/jaxws"

# Build local.classes classpath

mkdir -p ${CATALINA_API_LIB_HOME}

mkdir -p ${CATALINA_RI_LIB_HOME}

#cp -a ${JAXWS_HOME}/*.jar ${CATALINA_API_LIB_HOME}
#cp -a ${JAXWS_HOME}/*.jar ${CATALINA_RI_LIB_HOME}

# Configure TCK tests
echo step1
ANT_OPTS="-Djavax.xml.accessExternalStylesheet=all -Djavax.xml.accessExternalSchema=all -Djavax.xml.accessExternalDTD=file,http,https"


cp ${TS_HOME}/bin/ts.jte.tomcat ${TS_HOME}/bin/ts.jte.orig
#wget --no-check-certificate -nv https://raw.githubusercontent.com/senivam/jakartaee-tck/jaxws-ts-jte-tomcat/install/jaxws/bin/ts.jte.tomcat.jdk18 -O ${TS_HOME}/bin/ts.jte.orig
#mv soap-tck/bin/ts.jte.jdk11 soap-tck/bin/ts.jte.jdk11.orig
#mv soap-tck/bin/ts.jte.jdk11 soap-tck/bin/ts.jte


echo step2

    
sed -i "s|^webcontainer.home=.*|webcontainer.home=${CATALINA_API_HOME}|" ${TCK_NAME}/bin/ts.jte.orig
sed -i "s|^webcontainer.home.ri=.*|webcontainer.home.ri=${CATALINA_RI_HOME}|" ${TCK_NAME}/bin/ts.jte.orig
sed -i 's|webServerHost\.2=.*|webServerHost.2=localhost|' ${TCK_NAME}/bin/ts.jte.orig
sed -i 's|webServerPort\.2=.*|webServerPort.2=9080|' ${TCK_NAME}/bin/ts.jte.orig
sed -i "s|work.dir=.*|work.dir=${TS_WORK_DIR}|" ${TCK_NAME}/bin/ts.jte.orig
sed -i "s|report.dir=.*|report.dir=${TS_REPORT_DIR}|" ${TCK_NAME}/bin/ts.jte.orig

sed -i 's#-Djava.security.policy=.*# \\#g' ${TCK_NAME}/bin/ts.jte.orig
sed -i 's#-Djava.security.manager \\# \\#g' ${TCK_NAME}/bin/ts.jte.orig

cp ${TCK_NAME}/bin/ts.jte.orig  ${TCK_NAME}/bin/ts.jte
# Print TCK tests configuration
echo '- [ ts.jte ]--------------------------------------------------------------------'
cat ${TS_HOME}/bin/ts.jte
echo '--------------------------------------------------------------------------------'

mkdir -p ${TS_WORK_DIR}
mkdir -p ${TS_REPORT_DIR}

## Copy TCK web applications to Tomcat for deployment
#cp -v ${TS_HOME}/dist/*.war ${CATALINA_DEPLOY_DIR}

# Copy TCK dependencies to API Tomcat
mkdir -p "${CATALINA_API_HOME}/shared/lib"

cp -v ${TS_HOME}/lib/tsharness.jar \
      ${TS_HOME}/lib/jaxwstck.jar \
      ${JAXWS_HOME}/*.jar \
   ${CATALINA_API_HOME}/shared/lib

mv -v ${CATALINA_API_HOME}/conf/catalina.properties ${CATALINA_API_HOME}/conf/catalina.properties.orig
sed -e 's/shared.loader=.*/shared.loader="\$\{catalina.base\}\/shared\/lib\/\*\.jar"/' \
    ${CATALINA_API_HOME}/conf/catalina.properties.orig > ${CATALINA_API_HOME}/conf/catalina.properties

echo '- [ catalina.properties ]-------------------------------------------------------'
cat ${CATALINA_API_HOME}/conf/catalina.properties
echo '--------------------------------------------------------------------------------'

# Start API Tomcat
${CATALINA_API_HOME}/bin/startup.sh

cd ${TS_HOME}/bin
ant config.vi
cd ${WORKSPACE}


# Copy TCK dependencies to RI Tomcat

mkdir -p "${CATALINA_RI_HOME}/shared/lib"
cp -v ${TS_HOME}/lib/tsharness.jar \
      ${TS_HOME}/lib/jaxwstck.jar \
      ${JAXWS_HOME}/*.jar \
   ${CATALINA_RI_HOME}/shared/lib

mv -v ${CATALINA_RI_HOME}/conf/catalina.properties ${CATALINA_RI_HOME}/conf/catalina.properties.orig
sed -e 's/shared.loader=.*/shared.loader="\\$\{catalina.base\}\/shared\/lib\/\*\.jar"/' \
    ${CATALINA_RI_HOME}/conf/catalina.properties.orig > ${CATALINA_RI_HOME}/conf/catalina.properties

echo '- [ catalina.properties ]-------------------------------------------------------'
cat ${CATALINA_RI_HOME}/conf/catalina.properties
echo '--------------------------------------------------------------------------------'

# Start RI Tomcat
${CATALINA_RI_HOME}/bin/startup.sh

cd ${TS_HOME}/bin
ant config.ri
cd ${WORKSPACE}

#restart Apaches
${CATALINA_API_HOME}/bin/shutdown.sh
${CATALINA_API_HOME}/bin/startup.sh

${CATALINA_RI_HOME}/bin/shutdown.sh
${CATALINA_RI_HOME}/bin/startup.sh

# Run TCK tests
cd $TS_HOME/src/com/sun/ts/tests/jaxws
ant -Dkeywords=all -Dbuild.vi=true build
cd $TS_HOME/bin
ant -Dkeywords=all deploy.all
ant -Dkeywords=all run.all | tee run.log

export NAME=${JAXWS_TCK_BUNDLE##*/}

#wget -q ${JAXWS_TCK_BUNDLE} -O ${NAME}

echo '***********************************************************************************' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo '***                        TCK bundle information                               ***' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo "*** Name:       ${NAME}                                     ***" >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo "*** Download URL:	${JAXWS_TCK_BUNDLE} ***"  >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo '*** Date and size: '`stat -c "date: %y, size(b): %s" ${WORKSPACE}/${TCK_NAME}.zip`'        ***'>> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo "*** SHA256SUM: "`sha256sum ${WORKSPACE}/${TCK_NAME}.zip | awk '{print $1}'`' ***' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo '***                                                                             ***' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo '***                        MVN/JDK info                                         ***' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
mvn -v | tee -a ${WORKSPACE}/${SUMMARY_FILE_NAME} || true
echo '***********************************************************************************' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
echo '***                        TCK results summary                                  ***' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
cat run.log | sed -e '1,/Completed running/d' >> ${WORKSPACE}/${SUMMARY_FILE_NAME}
cat ${WORKSPACE}/${SUMMARY_FILE_NAME}
PASSED_COUNT=`head -10 ${WORKSPACE}/${SUMMARY_FILE_NAME} | tail -1 | sed 's/.*=\s\(.*\)/\1/'`
FAILED_COUNT=`head -11 ${WORKSPACE}/${SUMMARY_FILE_NAME} | tail -1 | sed 's/.*=\s\(.*\)/\1/'`
ERROR_COUNT=`head -12  ${WORKSPACE}/${SUMMARY_FILE_NAME} | tail -1 | sed 's/.*=\s\(.*\)/\1/'`

  echo "ERROR_COUNT:  ${ERROR_COUNT}"
  echo "FAILED_COUNT: ${FAILED_COUNT}"
  echo "PASSED_COUNT: ${PASSED_COUNT}"

${CATALINA_API_HOME}/bin/shutdown.sh
${CATALINA_RI_HOME}/bin/shutdown.sh
