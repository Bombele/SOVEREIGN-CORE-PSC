#!/bin/bash
set -euo pipefail

echo "🔒 Verrouillage Gradle 8.2 pour SOVEREIGN-CORE-PSC..."

# Supprime les wrappers instables
rm -rf gradle gradlew gradlew.bat .gradle

# Génère le wrapper blindé
gradle wrapper --gradle-version 8.2 --distribution-type bin
chmod +x gradlew

# Vérifie la version
./gradlew -v | grep "Gradle 8.2" || {
  echo "❌ Échec du verrouillage. Gradle 8.2 non détecté."
  exit 1
}

echo "✅ Gradle 8.2 verrouillé avec succès."