#!/bin/bash
# ==============================================================================
# Script : lock_sovereign_v8.sh
# Objectif : Verrouillage tactique du projet sur Gradle 8.2 (Version Blindée)
# ==============================================================================

set -e

echo "🛡️ Début du verrouillage sur Gradle 8.2..."

# 1. Nettoyage radical des résidus de la v9
echo "🧹 Purge des caches et des fichiers temporaires..."
rm -rf .gradle build
rm -rf gradle/wrapper/gradle-wrapper.jar
rm -rf gradle/wrapper/gradle-wrapper.properties
rm -f gradlew gradlew.bat

# 2. Installation forcée du Wrapper 8.2
echo "📥 Installation du Wrapper Gradle 8.2 (LTS Stability)..."
# Si gradle n'est pas installé, on tente d'utiliser une version système sécurisée
gradle wrapper --gradle-version 8.2 --distribution-type bin

# 3. Sécurisation des permissions
chmod +x gradlew

# 4. Validation du binaire sigint-core-all.jar
echo "🔨 Compilation du binaire opérationnel..."
./gradlew clean shadowJar --no-daemon

# 5. Vérification finale
if [ -f "build/libs/sigint-core-all.jar" ]; then
    echo "--------------------------------------------------------"
    echo "✅ SUCCÈS : Projet verrouillé sur Gradle 8.2."
    echo "📦 Binaire généré : build/libs/sigint-core-all.jar"
    echo "🛡️ Mission sécurisée pour la DRM."
    echo "--------------------------------------------------------"
else
    echo "❌ ERREUR : La génération du binaire a échoué. Vérifiez build.gradle.kts."
    exit 1
fi
