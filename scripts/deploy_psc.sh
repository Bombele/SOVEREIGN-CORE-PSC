#!/bin/bash

# =============================================================================
# SOVEREIGN-CORE-PSC - SCRIPT DE DÉPLOIEMENT AUTOMATIQUE
# Cible : Ubuntu 22.04+ / Debian 11+
# =============================================================================

set -e

echo -e "\033[0;34m[PSC] Démarrage du déploiement du Système Souverain...\033[0m"

# 1. Mise à jour système et installation des paquets de base
echo "[1/6] Installation des dépendances système..."
sudo apt-get update -y
sudo apt-get install -y openjdk-17-jdk python3-pip redis-server netcat-openbsd libnetfilter-queue-dev iptables wireguard

# 2. Installation des bibliothèques Python pour les vecteurs offensifs
echo "[2/6] Installation des bibliothèques Python..."
pip3 install netfilterqueue scapy pymodbus redis zmq

# 3. Configuration de Redis (Persistance des données SIGINT)
echo "[3/6] Configuration de Redis..."
sudo systemctl enable redis-server
sudo systemctl start redis-server

# 4. Préparation de l'arborescence et des fichiers de configuration
echo "[4/6] Structuration de l'environnement PSC..."
mkdir -p build/libs core/sigint data/audit data/keys scripts vectors/financial src/main/resources
chmod +x scripts/*.sh

# Téléchargement du packager ISO8583 si manquant
if [ ! -f "src/main/resources/iso87ascii.xml" ]; then
    echo "[!] Attention : iso87ascii.xml manquant. Pensez à l'ajouter pour jPOS."
fi

# 5. Configuration du Pare-feu pour l'interception réelle
echo "[5/6] Configuration des règles Netfilter (NFQUEUE)..."
sudo sysctl -w net.ipv4.ip_forward=1
sudo sysctl -w net.ipv4.conf.all.send_redirects=0
# Nettoyage des anciennes règles et ajout de la file d'attente pour le port bancaire
sudo iptables -F FORWARD
sudo iptables -A FORWARD -p tcp --dport 8583 -j NFQUEUE --queue-num 1

# 6. Compilation finale du projet
echo "[6/6] Compilation du Cœur PSC..."
chmod +x gradlew
./gradlew clean shadowJar

echo -e "\n\033[0;32m[SUCCÈS] DÉPLOIEMENT TERMINÉ.\033[0m"
echo -e "Pour lancer le système : \033[0;33mjava -jar build/libs/sovereign-core-psc.jar\033[0m"
