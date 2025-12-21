# Description du module Makefile

Le module `makefile/` centralise l’automatisation et l’orchestration du projet SIGINT.  
Il ne constitue pas un module fonctionnel comme `infra/`, `services/`, `data/` ou `tests/`, mais un **artefact institutionnel** qui garantit la reproductibilité, la traçabilité et la conformité des opérations.

---

## 📂 Structure

### makefile/
- **Makefile** → fichier racine des commandes institutionnelles.

---

## 🎯 Description du sous‑module

- **Makefile** → définit les cibles pour :
  - **Build** → compilation et génération des images conteneurs.  
  - **Deploy** → déploiement via Kubernetes ou Docker Compose.  
  - **Test** → exécution des tests unitaires, d’intégration et de conformité.  
  - **Audit** → génération des journaux, SBOM et vérification des signatures Cosign.  
  - **Compliance** → validation des règles institutionnelles et export des rapports.  

---

## 🏛️ Valeur institutionnelle

- **Crédibilité** → un seul point d’entrée pour orchestrer tout le projet.  
- **Traçabilité** → chaque cible est documentée et reliée aux modules (`infra/`, `services/`, `data/`, `tests/`).  
- **Auditabilité** → les commandes du Makefile garantissent que les étapes critiques sont reproductibles et vérifiables.  
- **Plug‑and‑Play institutionnel** → simplifie le déploiement et les tests sur terrain.  
- **Homogénéité** → en le plaçant dans `makefile/`, la structure reste claire et modulaire.  

---

✅ Avec ce module `makefile/`, ton projet SIGINT dispose d’une **colonne vertébrale opérationnelle** qui relie tous les autres modules et assure cohérence, reproductibilité et conformité.