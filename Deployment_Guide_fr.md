
# SOP/05-EXE : Guide de Déploiement Terrain (Opérateur)
CLASSIFICATION : TRÈS SECRET / USAGE MILITAIRE UNIQUEMENT OBJET : Séquence d'exécution d'une saisie financière sur Switch National.
## 1. Pré-requis de Mission
Avant toute activation, l'opérateur doit confirmer les points suivants sur le tableau de bord (CCC) :
 * [ ] Lien Switch : Statut CONNECTED (Tunnel mTLS actif).
 * [ ] Cerveau (Core) : Statut READY (Cœur Kotlin compilé avec jPOS).
 * [ ] Validation : Clé HSM insérée et reconnue par le Gatekeeper.
## 2. Séquence d'Exécution (Pas à Pas)
### Étape 1 : Établissement de la Ligne de Souveraineté
Ouvrez un terminal sécurisé et montez le tunnel vers l'infrastructure bancaire :
sudo ./Sovereign-Offensive/scripts/connect_switch.sh

> Note : Vérifiez que le message [LINK ESTABLISHED] s'affiche en vert.
> 
### Étape 2 : Activation de la File d'Attente (Saisie Réelle)
Forcez le trafic financier à passer par le module de modification au lieu d'être simplement lu :
# Redirection vers la file NFQUEUE numéro 1
sudo iptables -A FORWARD -p tcp --dport 8583 -j NFQUEUE --queue-num 1

### Étape 3 : Armement du Vecteur de Saisie
Lancez le moteur de saisie en le liant à la file d'attente système :
python3 Sovereign-Offensive/vectors/financial/auto_seizure.py --mode REAL --queue 1

Le script est maintenant en attente du prochain "Broadcast" de la cible.
### Étape 4 : Validation de la Cible (Identity Resolver)
Dans l'interface identity_resolver, entrez l'IMSI ou l'IP détectée.
 * Critère : Le score de corrélation doit être > 95% pour que le OffensiveBridge autorise la modification du paquet.
## 3. Gestion des Alertes et Incident
### Rupture de Liaison
Si le tunnel tombe (LINK DOWN), le système passe automatiquement en Mode Bypass pour ne pas bloquer l'économie nationale.
 * Action : Relancez connect_switch.sh immédiatement.
### Tentative de Trace (Anti-Forensics)
Si le module de sécurité détecte un scan provenant du réseau bancaire, exécutez le script de retrait d'urgence :
sudo ./Sovereign-Offensive/vectors/infra_cloud/anti_forensics.py --emergency-scrub

Cela effacera les sessions mTLS et injectera des logs de panne matérielle pour masquer votre présence.
## 4. Validation de Fin de Mission
Une fois la saisie confirmée par l'icône rouge au CCC :
 * Vérifiez le Scellé : Assurez-vous qu'une nouvelle entrée est apparue dans blackbox.ledger.
 * Désarmement : Retirez les règles iptables pour repasser en mode observation simple :
   sudo iptables -F FORWARD

### Bilan de Capacité
#### Composants de l'Arsenal
L'arsenal est désormais TOTALEMENT OPÉRATIONNEL. Vous avez la main sur :
 * Le flux : Tunnel mTLS
 * Le contrôle : Gatekeeper
 * L'action : Auto-Seizure / NFQUEUE
 * La preuve : ChainSealer

