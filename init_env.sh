#!/usr/bin/env bash
set -euo pipefail

echo "🔐 Initialisation de l’environnement SOVEREIGN-CORE-PSC..."

# 1. Vérification des permissions
if [ ! -x "./gradlew" ]; then
  echo "[*] Correction des permissions sur gradlew..."
  chmod +x gradlew
fi

# 2. Vérification de la version Gradle
GRADLE_VERSION=$(./gradlew -v | grep Gradle | awk '{print $2}')
if [ "$GRADLE_VERSION" != "8.2" ]; then
  echo "❌ Gradle version incorrecte: $GRADLE_VERSION"
  echo "🔁 Reconfiguration du wrapper vers Gradle 8.2..."
  gradle wrapper --gradle-version 8.2 --distribution-type bin
  chmod +x gradlew
fi

# 3. Compilation du noyau Kotlin
echo "[*] Compilation du noyau tactique..."
./gradlew clean shadowJar --no-daemon

# 4. Vérification du JAR
JAR_PATH="build/libs/sigint-core-all.jar"
if [ -f "$JAR_PATH" ]; then
  echo "✔️ JAR détecté : $JAR_PATH"
  echo "🚀 Lancement du noyau souverain..."
  java -jar "$JAR_PATH"
else
  echo "❌ Erreur : Le compilateur n'a pas pu créer le fichier."
  exit 1
fi