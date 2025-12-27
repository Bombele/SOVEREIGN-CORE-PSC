#!/bin/bash
# Script pour pousser la migration sur GitHub

echo "📦 Préparation du commit de migration..."
git add gradle/ gradlew gradlew.bat build.gradle.kts settings.gradle.kts

git commit -m "chore: upgrade to Gradle 9.0 and JDK 17"

echo "📤 Envoi vers la branche main..."
git push origin main

echo "🚀 Dépôt mis à jour !"
