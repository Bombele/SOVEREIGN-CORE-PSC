# Description du module Infra

Le module `infra/` fournit les capacités de déploiement, de sécurité et de traçabilité pour le système SIGINT.  
Il garantit que le logiciel peut être empaqueté, orchestré, signé et audité dans des environnements institutionnels.

---

## 📂 Structure

### docker/
- **Dockerfile** → Définition de l’image de base (runtime + dépendances).
- **docker-compose.yaml** → Orchestration locale multi-services.
- **entrypoint.sh** → Script d’initialisation du conteneur.

### k8s/
- **deployment.yaml** → Déploiement Kubernetes des pods (services COMINT/ELINT/FISINT).
- **service.yaml** → Exposition des services (gateway, sync, audit).
- **ingress.yaml** → Routage externe pour les interfaces analyste et auditeur.
- **configmap.yaml** → Paramètres de mission et profils.

### cosign/
- **cosign.pub** → Clé publique pour la vérification des signatures.
- **cosign.key** → Clé privée pour la signature (sécurisée).
- **sign.sh** → Script de signature des images conteneurs.

### sbom/
- **sbom.json** → Software Bill of Materials (liste des composants).
- **sbom.spdx** → Format SPDX pour conformité internationale.
- **generate_sbom.sh** → Script de génération des fichiers SBOM.

### ci_cd/
- **pipeline.yaml** → Pipeline CI/CD (tests, build, déploiement).
- **security_scan.yaml** → Scan de sécurité pour images et dépendances.
- **compliance_check.yaml** → Vérification de conformité légale et institutionnelle.

---

## 🎯 Description des sous‑modules

- **docker/** → conteneurisation pour déploiement rapide et portable.  
- **k8s/** → orchestration Kubernetes pour déploiement distribué et scalable.  
- **cosign/** → signature cryptographique des images pour garantir intégrité et authenticité.  
- **sbom/** → traçabilité logicielle (liste des composants, dépendances, versions).  
- **ci_cd/** → pipelines automatisés pour tests, sécurité et conformité.  

---

## 🏛️ Valeur institutionnelle

- **Crédibilité** : signatures Cosign + SBOM = conformité internationale.  
- **Scalabilité** : Kubernetes permet de gérer plusieurs SDR et modules en parallèle.  
- **Auditabilité** : CI/CD + compliance check garantissent que chaque build est validé.  
- **Plug‑and‑Play institutionnel** : Docker Compose pour déploiement rapide sur terrain.  

---

✅ Avec ce module `infra/`, ton logiciel SIGINT est prêt pour déploiement institutionnel : portable, sécurisé, traçable et conforme.