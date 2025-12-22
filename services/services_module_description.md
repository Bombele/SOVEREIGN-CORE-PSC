# Description du module Services

Le module `services/` constitue le **cœur opérationnel de traitement et de communication** du système SIGINT.  
Il regroupe les composants nécessaires pour capturer, traiter, classifier, géolocaliser et transmettre les signaux, tout en assurant auditabilité et résilience.

---

## 📂 Structure

### gateway/
- **GatewayService.kt** → Point d’entrée des flux SIGINT.

### capture/
- **CaptureService.kt** → Acquisition des signaux bruts via SDR.

### dsp/
- **FeaturesExtractor.kt** → Extraction DSP (FFT, cumulants, cyclostationnaire).  
- **SignalProcessor.kt** → Pipeline DSP classique.  
- **ai_inference/** → Intelligence embarquée (Edge AI).  
  - **TFLiteClassifier.kt** → Classification automatique (AMC).  
  - **ModelRegistry.md** → Liste des modèles légers.  
  - **InferenceService.kt** → API interne pour classification.  

### decode/
- **DecoderService.kt** → Décodage des protocoles radio.  
- **Protocols/** → Support des standards DMR, P25.  
  - **DMRDecoder.kt**  
  - **P25Decoder.kt**  

### classify/
- **ClassifyService.kt** → Classification des signaux et menaces.

### fusion_geo/
- **TdoaSolver.kt** → Algorithmes TDOA (Time Difference of Arrival).  
- **AoaEstimator.kt** → Algorithmes AoA (Angle of Arrival).  
- **FusionService.kt** → Fusion multi-nœuds, ellipse de confiance.  

### mesh/
- **MeshTransport.kt** → Interface transport (LoRa, WiFi ad-hoc).  
- **LoRaTransport.kt**  
- **WifiDirectTransport.kt**  
- **MeshCRDT.kt** → Structures CRDT pour fusion observations.  
- **MeshService.kt** → Orchestration et persistance locale.  

### audit/
- **AuditService.kt** → Journalisation et conformité des flux.

### sync/
- **SyncService.kt** → Synchronisation avec autres modules et transmission compressée.

---

## 🎯 Valeur institutionnelle

- **Opérabilité terrain** : Edge AI, mesh, géolocalisation offline.  
- **Résilience** : fonctionnement sans réseau centralisé.  
- **Traçabilité** : audit et journalisation des flux.  
- **Interopérabilité** : support des protocoles locaux (DMR, P25).  
- **Certification** : documentation et tests intégrés pour validation.  

---

✅ Avec ce module `services/`, le système SIGINT devient le **cœur opérationnel de traitement et de communication**, capable de fonctionner en contexte hostile tout en restant **documenté, auditable et certifiable**.