#!/bin/bash

# =============================================================================
# SRC - SCRIPT DE DÉMONSTRATION TACTIQUE (SIMULATION)
# Objectif : Valider la fusion BFT/SIGINT et l'UI sans hardware réel.
# =============================================================================

set -e

# Couleurs pour le terminal
BLUE='\033[0;34m'
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${BLUE}[DEMO] Initialisation de la simulation tactique...${NC}"

# 1. Préparation de l'environnement
mkdir -p data/keys
mkdir -p core/audit/logs

# 2. Génération de clés temporaires si absentes
if [ ! -f data/keys/master.key ]; then
    echo -e "${BLUE}[DEMO] Création de clés de test...${NC}"
    echo "7b3a12f98c4e2d1a" > data/keys/master.key
fi

# 3. Compilation rapide du projet
echo -e "${BLUE}[DEMO] Compilation des modules...${NC}"
make build

# 4. Lancement de la simulation
echo -e "${GREEN}[OK] Démarrage de l'interface de combat (COP)...${NC}"
echo -e "${BLUE}------------------------------------------------------------${NC}"
echo -e "SIMULATION ACTIVE :"
echo -e " - Unité Locale : ALPHA-01 (Position: Goma, RDC)"
echo -e " - Allié Détecté (BFT) : BRAVO-02 (Position: +500m Nord)"
echo -e " - Menace Radio (SIGINT) : Signal DMR détecté à 800m Est"
echo -e "${BLUE}------------------------------------------------------------${NC}"

# Lancement du binaire avec un flag de simulation
# On passe des arguments fictifs que le Main.kt peut interpréter pour simuler des données
java -jar build/sigint-engine.jar --demo-mode --unit-id ALPHA-01
