# Utilisation d'une image de base sécurisée (Alpine Linux)
FROM alpine:3.18

# Installation des dépendances pour le SIGINT et le Hacking
RUN apk add --no-cache python3 py3-pip scapy tcpdump scapy-doc

# Création de l'utilisateur non-root pour l'exécution
RUN adduser -D operator
USER operator

WORKDIR /app
COPY . /app

# Lancement du module d'audit par défaut
CMD ["python3", "core/gatekeeper.py"]
