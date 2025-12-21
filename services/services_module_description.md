# Description du module Services

Le module `services/` regroupe les briques techniques transversales du système SIGINT.  
Il assure la capture, le traitement, le décodage, la classification, la fusion géographique, l’audit et la synchronisation offline.  
C’est le cœur opérationnel qui rend le logiciel plug‑and‑play, multi‑SDR et institutionnellement auditable.

---

## 📂 Structure

### gateway/
- **GatewayServer.kt** → Agrégation des flux SDR.
- **GatewayRouter.kt** → Routage vers modules COMINT/ELINT/FISINT.
- **GatewayConfig.yaml** → Configurations passerelle.

### capture/
- **CaptureManager.kt** → Gestion des drivers SDR (USB, réseau).
- **CaptureAdapter.kt** → Interface commune IQ/flux.
- **CaptureProfiles.json** → Profils de capture (bandes, protocoles).

### dsp/
- **DSPPipeline.kt** → Chaîne DSP (FFT, filtrage, spectrogrammes).
- **DSPUtils.kt** → Fonctions DSP partagées.
- **DSPConfig.yaml** → Paramètres DSP.

### decode/
- **DecoderManager.kt** → Gestion des protocoles (AM/FM, GSM, VoIP, télémesure).
- **DecoderProfiles.json** → Protocoles supportés.
- **DecoderUtils.kt** → Fonctions utilitaires.

### classify/
- **ClassifierEngine.kt** → Moteur de classification radar/protocoles.
- **SignatureDB.json** → Base signatures connues.
- **ClassifierUtils.kt** → Fonctions utilitaires.

### fusion_geo/
- **GeoFusionEngine.kt** → Triangulation des émetteurs.
- **GeoMapper.kt** → Cartographie des signaux.
- **GeoProfiles.json** → Profils géographiques.

### audit/
- **AuditManager.kt** → Gestion journaux.
- **AuditExport.kt** → Exports signés (PDF/JSON).
- **ComplianceMatrix.json** → Matrice conformité.

### sync/
- **SyncManager.kt** → Moteur de synchronisation.
- **SyncWorker.kt** → Reprise sur erreur.
- **SyncConfig.yaml** → Paramètres sync.

---

## 🎯 Description des sous‑modules

- **gateway/** → passerelle multi‑SDR pour agrégation et routage des flux.  
- **capture/** → abstraction matérielle des SDR (drivers, profils de capture).  
- **dsp/** → pipeline de traitement signal (FFT, filtrage, spectrogrammes).  
- **decode/** → décodage des protocoles voix, données et télémesures.  
- **classify/** → classification signatures radar et protocoles.  
- **fusion_geo/** → triangulation et cartographie des émetteurs.  
- **audit/** → journaux immuables, conformité et exports audités.  
- **sync/** → moteur de synchronisation offline, reprise sur erreur.  

---

## 🏛️ Valeur institutionnelle

- **Plug‑and‑Play** → capture immédiate via abstraction SDR.  
- **Multi‑SDR** → passerelle pour déploiements distribués.  
- **Offline‑first** → synchronisation différée et auditable.  
- **Auditabilité** → journaux immuables et conformité institutionnelle.  
- **Transmission trilingue** → adoption locale et continentale.  

---

✅ Avec ce module `services/`, ton logiciel SIGINT devient **opérationnel et institutionnel**, capable de gérer toutes les étapes : capture, traitement, décodage, classification, fusion géographique, audit et synchronisation.