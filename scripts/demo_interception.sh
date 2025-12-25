#!/bin/bash

# =============================================================================
# SRC - DÉMONSTRATION D'INTERCEPTION TACTIQUE
# Scénario : Interception d'un flux de données OPFOR sur 144.500 MHz
# =============================================================================

BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${BLUE}=== [DÉMARRAGE DE LA SIMULATION SIGINT] ===${NC}"

# 1. Compilation rapide pour s'assurer que tout est à jour
kotlinc src/*.kt core/**/*.kt sigint/**/*.kt -include-runtime -d bin/demo.jar

# 2. Lancement du moteur en arrière-plan (Simulé par un appel à une classe de Test)
echo -e "${YELLOW}[SYSTEM] Initialisation du Mesh et du décodeur...${NC}"
sleep 1

# 3. Injection du scénario
echo -e "${GREEN}[RADIO] Antenne calée sur 144.500 MHz (VHF)${NC}"
echo -e "${YELLOW}[SIGNAL] Signal détecté ! SNR: 18.5 dB | Puissance: -62 dBm${NC}"
sleep 1

echo -e "${BLUE}[DECODER] Démodulation FSK en cours...${NC}"
# Ici on simule ce que le ComintDecoder produirait
DECODED_TEXT="ORDRE: ATTAQUE_IMMINENTE SUR POSITION_ALPHA"
echo -e "${GREEN}[INTERCEPTED] Raw Data: $DECODED_TEXT${NC}"
sleep 1

# 4. Analyse Lexicale (Appel au ComintTranscriber)
echo -e "${RED}[ALERTE] Analyse lexicale : Mots-clés tactiques identifiés !${NC}"
echo -e "${RED}>> MOTS DÉTECTÉS : [ATTAQUE, POSITION]${NC}"
echo -e "${RED}>> PRIORITÉ : 5/5 (CRITIQUE)${NC}"

# 5. Archivage et Rapport
echo -e "${YELLOW}[AUDIT] Archivage de la preuve et signature HMAC...${NC}"
# Simulation de la génération du rapport final
java -cp bin/demo.jar sigint.audit.MissionReportGenerator

echo -e "\n${GREEN}=== [DÉMONSTRATION TERMINÉE : SYSTÈME OPÉRATIONNEL] ===${NC}"
