
# 🛡️ SRC - Système de Renseignement de Combat (SIGINT & BFT)  

SRC est une plateforme souveraine de guerre électronique mobile conçue pour offrir une supériorité informationnelle sur le champ de bataille. Il fusionne le suivi des forces amies (BFT) et l'interception de signaux hostiles (SIGINT) dans une architecture ultra-sécurisée et résiliente.

## 🚀 Capacités Clés
 * Fusion Tactique (COP) : Visualisation unifiée des unités alliées (Blue Forces) et des menaces électromagnétiques (Red Threats) sur cartes hors-ligne.
 * Intelligence Artificielle SIGINT : Classification automatique des modulations (DMR, VHF, UHF) via TensorFlow Lite pour identifier instantanément les réseaux adverses.
 * Réseau Mesh Résilient : Synchronisation décentralisée des données via Wi-Fi Direct et LoRa, permettant de fonctionner sans infrastructure (Cloud-independent).
 * Sécurité de Grade Militaire :
   * Tactical Wipe : Auto-destruction des données sensibles en cas de capture ou de sortie de zone (Geofencing).
   * Audit Immuable : Journalisation de mission signée par HMAC pour garantir l'intégrité des preuves.
   * Hardening : Obscurcissement binaire et protection contre l'ingénierie inverse.

## 🏗️ Architecture du Projet
Le système est construit de manière modulaire pour une maintenance et une évolution facilitées :
 * core/ : Moteur de sécurité, synchronisation Mesh et audit.
 * bft/ : Gestion de la géolocalisation et coordination des unités.
 * sigint/ : Traitement du signal (DSP) et classification par IA.
 * ui/ : Interface de fusion cartographique (Common Operating Picture).
 * scripts/ : Automatisation du durcissement, déploiement et gestion des clés.

## 🛠️ Installation Rapide
### 1. Cloner et préparer
git clone https://github.com/Bombele/Combat-Ready-System-SIGINT.git
cd Combat-Ready-System-SIGINT

### 2. Configurer la sécurité
make rotate-keys
nano core/security/active_geofence.poly

### 3. Compiler et Lancer la démo
make build
./run_demo.sh

##📋 État du Développement
 * [x] Framework de sécurité (Wipe/Geofence)
 * [x] Moteur de synchronisation Mesh (Protobuf/CBOR)
 * [x] Module de classification IA
 * [x] Interface de fusion cartographique
 * [ ] Support Multi-SDR (HackRF/BladeRF) - En cours

Développé pour la résilience et la souveraineté technologique.