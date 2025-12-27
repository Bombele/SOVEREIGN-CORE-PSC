#!/bin/bash
# Script d'activation des capacités Cyber

echo "🔨 Compilation du moteur souverain..."
./gradlew shadowJar

# On définit une cible de test (ton localhost ou un serveur de test dédié)
TARGET="127.0.0.1"

echo "🔎 Étape 1 : Audit des ports ouverts"
java -jar build/libs/sovereign-core.jar --audit $TARGET

echo "⚡ Étape 2 : Test de résilience (Stress)"
java -jar build/libs/sovereign-core.jar --stress $TARGET
