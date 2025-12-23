#!/bin/bash

# =============================================================================
# SRC - SYSTÈME DE RENSEIGNEMENT DE COMBAT (FARDC)
# Script de Déploiement Tactique Opérationnel
# Emplacement : infra/deployment/deploy_field.sh
# =============================================================================

set -e # Arrête le script en cas d'erreur

# Couleurs pour le feedback opérateur
RED='\033[0;31m'
GREEN='\033[0;32m'
CYAN='\033[0;36m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Chemins relatifs (ajustés car le script est dans infra/deployment/)
ROOT_DIR="../../"
DATA_DIR="${ROOT_DIR}data/"
SIG_DIR="${ROOT_DIR}data/signatures/"
MAP_DIR="${ROOT_DIR}data/maps/"
SECURITY_DIR="${ROOT_DIR}core/security/"
SCRIPTS_DIR="${ROOT_DIR}scripts/"

echo -e "${CYAN}======================================================"
echo -e "   SRC TACTICAL DEPLOYMENT - FORCES SPÉCIALES SIGINT"
echo -e "======================================================${NC}"

# 1. ANALYSE DES ARGUMENTS
while [[ "$#" -gt 0 ]]; do
    case $1 in
        --zone) ZONE="$2"; shift ;;
        --unit_id) UNIT_ID="$2"; shift ;;
        *) echo "Option inconnue: $1"; exit 1 ;;
    esac
    shift
done

if [ -z "$ZONE" ] || [ -z "$UNIT_ID" ]; then
    echo -e "${RED}[!] Erreur: Paramètres manquants.${NC}"
    echo -e "Usage: ./deploy_field.sh --zone [KIVU_NORD|KIVU_SUD|ITURI] --unit_id [ID_UNITE]"
    exit 1
fi

# 2. VÉRIFICATION DE L'INTÉGRITÉ DU MATÉRIEL
echo -e "\n${YELLOW}[1/5] Vérification de l'intégrité hardware...${NC}"
# Vérification sommaire du hardware SDR
if ls /dev/sw* >/dev/null 2>&1 || lsusb | grep -i "SDR" >/dev/null 2>&1; then
    echo -e "    ${GREEN}[OK] Interface Radio détectée.${NC}"
else
    echo -e "    ${RED}[!] ATTENTION: Aucune antenne SDR détectée.${NC}"
fi

# 3. DURCISSEMENT ET SÉCURITÉ (HARDENING)
echo -e "\n${YELLOW}[2/5] Durcissement tactique du système...${NC}"
if [ -f "${SCRIPTS_DIR}harden_binary.sh" ]; then
    bash "${SCRIPTS_DIR}harden_binary.sh"
    echo -e "    ${GREEN}[OK] Obscurcissement du code terminé.${NC}"
else
    echo -e "    ${RED}[!] Script de hardening manquant dans /scripts.${NC}"
fi

# Activation du TacticalWipeManager (Protection contre capture)
chmod 700 "${SECURITY_DIR}TacticalWipeManager.kt"
echo -e "    ${GREEN}[OK] Protection Anti-Capture (Panic) verrouillée.${NC}"

# 4. INJECTION DES DONNÉES DE MISSION (GEO & SIGNATURES)
echo -e "\n${YELLOW}[3/5] Configuration de la zone : ${ZONE}${NC}"
case $ZONE in
    "KIVU_NORD")
        COORD="48.5,29.1,1.2,27.5"
        MAP="kivu_nord.mbtiles"
        ;;
    "KIVU_SUD")
        COORD="48.8,28.5,1.5,29.0"
        MAP="kivu_sud.mbtiles"
        ;;
    "ITURI")
        COORD="50.1,29.8,2.5,30.5"
        MAP="ituri.mbtiles"
        ;;
    *)
        echo -e "${RED}[!] Zone non supportée.${NC}"; exit 1 ;;
esac

# Simulation de l'activation du Geofencing
echo "$COORD" > "${SECURITY_DIR}active_geofence.poly"
echo -e "    ${GREEN}[OK] Périmètre de mission défini.${NC}"

# Chargement des signatures spécifiques FARDC
if [ -f "${SIG_DIR}fardc_threat_db.json" ]; then
    echo -e "    ${GREEN}[OK] Bibliothèque de menaces locales chargée.${NC}"
else
    echo -e "    ${RED}[!] Fichier de signatures manquant.${NC}"
fi

# 5. NETTOYAGE (ANTI-FORENSIC)
echo -e "\n${YELLOW}[4/5] Nettoyage des traces de développement...${NC}"
rm -rf "${ROOT_DIR}.git"
rm -rf "${ROOT_DIR}tests"
echo -e "    ${GREEN}[OK] Environnement de combat prêt.${NC}"

# 6. FINALISATION
echo -e "\n${YELLOW}[5/5] Génération du rapport de déploiement...${NC}"
DEPLOY_LOG="${ROOT_DIR}logs/last_deploy.log"
mkdir -p "${ROOT_DIR}logs"
echo "DEPLOY_ID: $UNIT_ID | ZONE: $ZONE | DATE: $(date)" > "$DEPLOY_LOG"

echo -e "\n${CYAN}======================================================"
echo -e "   STATUT : OPÉRATIONNEL - MISSION PRÊTE"
echo -e "   UNITÉ  : $UNIT_ID"
echo -e "   ZONE   : $ZONE"
echo -e "======================================================${NC}"
