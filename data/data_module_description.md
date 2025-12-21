# Description du module Data

Le module `data/` constitue le référentiel technique et institutionnel du système SIGINT.  
Il regroupe les échantillons de signaux, les bases de signatures, les jeux de données pour classification, les données géographiques et les éléments nécessaires à l’audit.  
Ce module garantit la traçabilité, l’auditabilité et la transmission des informations.

---

## 📂 Structure

### samples_iq/
- **hf_voice_sample.iq** → Exemple COMINT voix HF.
- **radar_bandS_sample.iq** → Exemple ELINT radar bande S.
- **telemetry_bandL_sample.iq** → Exemple FISINT télémesure bande L.
- **test_metadata.json** → Métadonnées associées aux échantillons.

### signatures/
- **radar_signatures.json** → Signatures radar connues.
- **protocol_signatures.json** → Protocoles COMINT/FISINT.
- **emitter_profiles.yaml** → Profils émetteurs (fréquences, puissances, zones).
- **signature_index.md** → Documentation indexée.

### datasets/
- **training_set.csv** → Données pour classification.
- **validation_set.csv** → Données pour tests.
- **labels.json** → Labels associés aux signaux.

### geo/
- **maps/** → Cartes et profils géographiques.
  - **base_map.geojson** → Carte de référence globale.
  - **terrain_profiles.json** → Profils terrains (altitude, zones).
  - **coverage_zones.yaml** → Zones de couverture SDR.
  - **restricted_areas.json** → Zones interdites / sensibles.
  - **map_index.md** → Documentation indexée.
- **geo_profiles.json** → Profils géographiques.
- **triangulation_samples.csv** → Données pour fusion géographique.

### audit_data/
- **compliance_logs.json** → Journaux de conformité.
- **audit_samples.json** → Exemples de flux audités.
- **export_templates/** → Modèles d’exports auditeurs.
  - **audit_report_template.md** → Modèle de rapport auditeur.
  - **compliance_export.json** → Modèle export conformité.
  - **signal_log_template.csv** → Modèle journal des signaux.
  - **geo_export_template.yaml** → Modèle export données géographiques.
  - **export_index.md** → Documentation indexée.

---

## 🎯 Description des sous‑modules

- **samples_iq/** → fournit des échantillons IQ pour tester le pipeline DSP (FFT, filtrage, spectrogrammes).  
- **signatures/** → base de signatures radar et protocoles pour classification et reconnaissance.  
- **datasets/** → jeux de données enrichis pour entraînement et validation des moteurs de classification.  
- **geo/maps/** → cartes et profils géographiques pour triangulation et audit.  
- **audit_data/export_templates/** → modèles standardisés pour exports auditeurs et conformité institutionnelle.  

---

## 🏛️ Valeur institutionnelle

- **Traçabilité** : chaque échantillon et signature est documenté et indexé.  
- **Auditabilité** : données de conformité et modèles d’exports prêts pour validation externe.  
- **Transmission** : documentation claire pour adoption continentale.  
- **Plug‑and‑Play institutionnel** : les échantillons IQ et modèles d’exports permettent de tester et transmettre immédiatement.  

---

✅ Avec ce module `data/`, ton logiciel SIGINT dispose d’un **référentiel technique et institutionnel complet**, garantissant traçabilité, auditabilité et transmission.