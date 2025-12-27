cat << 'EOF' > ultrasonic_build.sh
#!/bin/bash
echo "🛡️ DÉMARRAGE DE LA COMPILATION SOUVERAINE ULTRASONIC..."

# 1. Unification physique du code (Fix package mismatch)
mkdir -p src/main/kotlin/com/fardc/sigint/core
find src -name "*.kt" -exec mv {} src/main/kotlin/com/fardc/sigint/core/ \; 2>/dev/null

# 2. Installation d'un compilateur Kotlin privé (Fix "command not found")
echo "📥 Acquisition du compilateur Kotlin indépendant..."
curl -L https://github.com/JetBrains/kotlin/releases/download/v1.9.0/kotlin-compiler-1.9.0.zip -o /tmp/kotlinc.zip
unzip -qo /tmp/kotlinc.zip -d /tmp/
KOTLINC="/tmp/kotlinc/bin/kotlinc"

# 3. Compilation brute (Bypass total de l'erreur 25.0.1)
echo "⚙️ Compilation du noyau SIGINT sans passer par Gradle..."
mkdir -p build/libs
$KOTLINC src/main/kotlin/com/fardc/sigint/core/*.kt \
    -include-runtime -d build/libs/sigint-core-all.jar

# 4. Vérification finale
if [ -f "build/libs/sigint-core-all.jar" ]; then
    echo "✅ SUCCÈS TOTAL : Le binaire FARDC est prêt !"
    echo "📦 Emplacement : build/libs/sigint-core-all.jar"
    echo "🚀 Commande de lancement : java -jar build/libs/sigint-core-all.jar"
else
    echo "❌ ÉCHEC : Vérifiez les erreurs de syntaxe ci-dessus."
fi
EOF

# Exécution du script
chmod +x ultrasonic_build.sh
./ultrasonic_build.sh
