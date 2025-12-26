# 🛡️ Project Sovereign Core (PSC)
Système Intégré de Renseignement Technique & d'Action Offensive
PSC est une plateforme de défense de nouvelle génération conçue pour la République Démocratique du Congo. Elle fusionne le renseignement électronique (SIGINT), le suivi tactique (BFT) et la supériorité numérique offensive (Cyber/Finance).
## 🚀 Capacités Stratégiques Majeures
### 1. 📡 Renseignement & Tactique (Plan Bleu)
 * SIGINT Multimodal : Interception et analyse du spectre radio (COMINT/ELINT).
 * Identity Resolver : Corrélation en temps réel entre les identités radios (IMSI/IMEI) et les activités numériques (IP).
 * BFT (Blue Force Tracking) : Visualisation cartographique des forces amies et détection des menaces sur le théâtre d'opérations.
### 2. 💸 Souveraineté Financière (Plan Gris)
 * Saisie Conservatoire Numérique : Interception et redirection automatique des flux financiers suspects (Mobile Money, Banques) vers les comptes de récupération de l'État.
 * Crypto-Linker : Levée de l'anonymat sur les transactions Blockchain par corrélation avec les métadonnées SIGINT.
 * Asphyxie Économique : Gel instantané des portefeuilles numériques de groupes armés identifiés.
### 3. ⚡ Supériorité Offensive (Plan Rouge)
 * Action Réseau : Man-in-the-Middle (MitM) tactique, injection BGP et neutralisation d'infrastructures critiques.
 * Sabotage Numérique : "Panne Fantôme", désinformation (Spoofing) et chiffrement tactique des bases de données adverses.
 * Intervention SCADA : Capacité de coupure sélective de l'énergie et des télécoms en zone de conflit.
## 🏛️ Gouvernance & Audit : "La Boîte Noire"
Le système PSC est régi par une architecture de confiance inviolable :
 * Audit Immuable (WORM) : Chaque action offensive est scellée par hachage cryptographique lié. Toute modification du passé corrompt la chaîne, alertant immédiatement le haut commandement.
 * Double Verrouillage PKI : L'activation des modules offensifs nécessite la signature conjointe de l'Opérateur Technique et d'un Magistrat Militaire.
 * Transparence Juridique : Génération automatique de rapports certifiés pour servir de preuve devant les instances internationales.
### 🛠️ Structure du Projet
├── 📂 build/libs/          # Noyau compilé (sigint-core-all.jar)
├── 📂 core/                # Renseignement technique (Identity Resolver)
├── 📂 data/                # Coffre-fort (Clés PKI, Journaux d'Audit)
├── 📂 scripts/             # Scripts d'activation tactique (IPTables, Tunneling)
├── 📂 src/main/kotlin/     # Cœur de contrôle PKI & Bridge
└── 📂 vectors/             # Arsenal offensif (Saisie financière, Infra, SCADA)

### ⚙️ Installation Rapide (Combat Ready)
 * Générer les certificats de souveraineté :
   openssl req -x509 -newkey rsa:4096 -keyout data/keys/state_private.key -out data/keys/state_auth.crt -nodes -days 3650

 * Compiler le noyau :
   ./gradlew shadowJar

 * Lancer la mission :
   sudo ./run_all.sh

Développé pour la défense de la souveraineté nationale. 🇨🇩
Ce projet est strictement réservé à un usage institutionnel sous mandat de l'État-Major.
