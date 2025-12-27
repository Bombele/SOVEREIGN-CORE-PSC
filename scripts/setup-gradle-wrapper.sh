#!/usr/bin/env bash
set -euo pipefail

GRADLE_VERSION="8.2"
WRAPPER_DIR="gradle/wrapper"
PROPERTIES_FILE="${WRAPPER_DIR}/gradle-wrapper.properties"
JAR_FILE="${WRAPPER_DIR}/gradle-wrapper.jar"

echo "🔍 Vérification du Gradle Wrapper..."

# 1. Vérifier si gradlew existe
if [ ! -f "./gradlew" ]; then
  echo "⚠️ Le script ./gradlew est absent. Génération..."
  gradle wrapper --gradle-version ${GRADLE_VERSION}
fi

# 2. Vérifier le fichier properties
if [ ! -f "${PROPERTIES_FILE}" ]; then
  echo "⚠️ ${PROPERTIES_FILE} absent. Création..."
  mkdir -p "${WRAPPER_DIR}"
  cat > "${PROPERTIES_FILE}" <<EOF
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
EOF
fi

# 3. Vérifier le JAR
if [ ! -f "${JAR_FILE}" ]; then
  echo "⚠️ ${JAR_FILE} absent. Régénération du wrapper..."
  ./gradlew wrapper --gradle-version ${GRADLE_VERSION}
fi

# 4. Validation finale
if [ -f "${JAR_FILE}" ] && [ -f "${PROPERTIES_FILE}" ]; then
  echo "✅ Gradle Wrapper prêt et complet."
else
  echo "❌ Échec de la génération du Gradle Wrapper."
  exit 1
fi