# Description du module Core

Le module `core/` constitue le **cœur opérationnel résilient et sécurisé** du système SIGINT.  
Il regroupe les composants essentiels pour assurer sécurité avancée, résilience, traçabilité, interopérabilité et certification.

---

## 📂 Structure

### Security_Advanced/
- **GeofenceManager.kt** → Applique les politiques de géofencing (zones autorisées, wipe logic, désactivation des modules sensibles).  
- **SecurityController.kt** → Anti‑tamper, obscurcissement du binaire, gestion des clés sensibles.  
- **KeyVault.kt** → Stockage sécurisé et effacement des clés cryptographiques.  

### Resilience/
- **SyncEngine.kt** → Synchronisation P2P (LoRa, WiFi ad‑hoc, CRDT fusion).  
- **DeviceManager.kt** → Gestion des drivers SDR (RTL‑SDR, HackRF) sur Android.  
- **BurstTransmitter.kt** → Transmission compressée (CBOR/Zstd) via SatCom/Iridium.  

### Traceability/
- **Auditor.kt** → Journalisation signée et traçable des événements critiques.  
- **core_audit_index.md** → Documentation des mécanismes de traçabilité.  

### Interoperability/
- **PolicyProvider.kt** → Chargement et application des politiques OPA/rego.  
- **ModuleController.kt** → Activation/désactivation des modules sensibles.  
- **GeometryUtils.kt** → Fonctions géométriques (point‑in‑polygon, ellipses TDOA).  

### Certification/
- **core_index.md** → Documentation globale du module.  
- **compliance_hooks.md** → Points de contrôle pour certification.  
- **certification_tests.md** → Tests institutionnels pour validation.  

---

## 🎯 Valeur institutionnelle

- **Sécurité avancée** : geofencing, wipe logic, anti‑tamper.  
- **Résilience** : synchronisation P2P, drivers SDR, transmission compressée.  
- **Traçabilité** : audit et journalisation signée.  
- **Interopérabilité** : politiques OPA/rego, contrôleurs de modules, calculs géométriques.  
- **Certification** : documentation et tests institutionnels pour validation.  

---

✅ Avec ce module `core/`, le système SIGINT dispose d’un **cœur robuste et institutionnel**, garantissant que chaque action est **documentée, auditable et adaptée au terrain**.