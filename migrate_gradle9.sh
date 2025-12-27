#!/bin/bash
# Script pour forcer le passage à Gradle 9.0

echo "🛠 Suppression de l'ancien Wrapper..."
rm -rf gradle/wrapper/gradle-wrapper.jar
rm -rf gradle/wrapper/gradle-wrapper.properties
rm -f gradlew gradlew.bat

echo "📥 Installation de Gradle 9.0..."
# Utilise la version système de gradle pour installer le wrapper 9.0
gradle wrapper --gradle-version 9.0 --distribution-type bin

echo "🔐 Sécurisation du script d'exécution..."
chmod +x gradlew

echo "✅ Terminé. Vérification de la version :"
./gradlew -v
