#!/bin/bash

SOURCE=$(pwd)/target/test-classes/projects/it1/project/app
TARGET=$(pwd)/src/main/resources/archetype-resources
echo "Source: ${SOURCE}"
echo "Target: ${TARGET}"
echo

echo "================================================================================"
echo "/src"
echo "--------------------------------------------------------------------------------"
find "${TARGET}" -maxdepth 1 -type f -exec rm -fv {} \;
find "${SOURCE}" -maxdepth 1 -type f ! -name "*.log" -exec cp -v {} "${TARGET}" \;
echo "================================================================================"
echo

echo "================================================================================"
echo "/.mvn"
echo "--------------------------------------------------------------------------------"
rm -rfv "${TARGET}"/.mvn
cp -rv "${SOURCE}"/.mvn "${TARGET}"
echo "================================================================================"
echo

echo "================================================================================"
echo "/src/main"
echo "--------------------------------------------------------------------------------"
rm -rfv "${TARGET}"/src/main
mkdir -pv "${TARGET}"/src/main/docker
cp -rv "${SOURCE}"/src/main/docker "${TARGET}"/src/main
mkdir -pv "${TARGET}"/src/main/java
cp -rv "${SOURCE}"/src/main/java/com/example/app/cdi "${TARGET}"/src/main/java
cp -rv "${SOURCE}"/src/main/java/com/example/app/web "${TARGET}"/src/main/java
mkdir -pv "${TARGET}"/src/main/resources
cp -rv "${SOURCE}"/src/main/resources "${TARGET}"/src/main
echo "================================================================================"
echo

echo "================================================================================"
echo "/src/test"
echo "--------------------------------------------------------------------------------"
rm -rfv "${TARGET}"/src/test
mkdir -pv "${TARGET}"/src/test/java
cp -rv "${SOURCE}"/src/test/java/com/example/app/web "${TARGET}"/src/test/java
echo "================================================================================"
echo

echo "================================================================================"
echo "Replace"
echo "--------------------------------------------------------------------------------"
find "${TARGET}" -type f -exec sed -i "s/com.example.app/\${package}/" {} \;
sed -i "s/<groupId>com.example<\/groupId>/<groupId>\${groupId}<\/groupId>/" "${TARGET}"/pom.xml
sed -i "s/<artifactId>app<\/artifactId>/<artifactId>\${artifactId}<\/artifactId>/" "${TARGET}"/pom.xml
sed -i "s/<version>1.0.0-SNAPSHOT<\/version>/<version>\${version}<\/version>/" "${TARGET}"/pom.xml
sed -i "s/<name>app<\/name>/<name>\${artifactId}<\/name>/" "${TARGET}"/pom.xml
echo "================================================================================"
echo

echo "================================================================================"
echo "Achtung bei Images und Markdown-Dateien -> Separat anschauen."
echo "================================================================================"
