#!/bin/bash

# ==============================================================================
# Script de Migration et Vérification Gradle 9
# Objectif : Automatiser le passage de 8.2 vers 9.0 et lister les dépréciations.
# ==============================================================================

set -e # Arrête le script en cas d'erreur

echo "🚀 Démarrage de la vérification pour Gradle 9..."

# 1. Vérification de l'environnement Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)

if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Erreur : JDK 17+ est requis pour Gradle 9. Version actuelle : $JAVA_VERSION"
    exit 1
else
    echo "✅ Java version $JAVA_VERSION détectée."
fi

# 2. Nettoyage des anciens artefacts
echo "🧹 Nettoyage des anciens wrappers et du cache..."
rm -rf gradle/wrapper/gradle-wrapper.jar
rm -rf gradle/wrapper/gradle-wrapper.properties
rm -f gradlew gradlew.bat

# 3. Migration vers Gradle 9
echo "📥 Génération du wrapper Gradle 9.0..."
# On utilise la version locale de gradle pour générer le nouveau wrapper
gradle wrapper --gradle-version 9.0 --distribution-type bin
chmod +x gradlew

# 4. Validation de la version
echo "⚙️ Vérification de l'installation..."
./gradlew -v

# 5. Build de test avec rapport de dépréciation complet
echo "🔍 Analyse des API obsolètes et compilation..."
echo "--------------------------------------------------------"
./gradlew clean build --warning-mode all --stacktrace

# 6. Conclusion
if [ $? -eq 0 ]; then
    echo "--------------------------------------------------------"
    echo "✅ Migration réussie ! Le projet compile sous Gradle 9."
    echo "👉 Analyse les warnings ci-dessus pour anticiper Gradle 10."
else
    echo "--------------------------------------------------------"
    echo "❌ Échec de la compilation. Vérifie les logs ci-dessus."
    exit 1
fi
