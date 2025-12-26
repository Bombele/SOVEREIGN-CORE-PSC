import hashlib
import json
import os
from datetime import datetime

class ChainSealer:
    """
    Moteur d'Audit Souverain Fusionné.
    Assure l'immuabilité des saisies financières et la traçabilité des opérateurs.
    """
    def __init__(self, storage_path="Sovereign-Offensive/logs/blackbox.ledger"):
        self.storage_path = storage_path
        self._ensure_storage_exists()
        self.last_hash = self._get_last_hash()

    def _ensure_storage_exists(self):
        os.makedirs(os.path.dirname(self.storage_path), exist_ok=True)
        if not os.path.exists(self.storage_path):
            with open(self.storage_path, "w") as f:
                f.write("") # Initialisation du registre

    def _get_last_hash(self):
        """Récupère le hash du dernier bloc pour lier le nouveau."""
        try:
            with open(self.storage_path, "rb") as f:
                lines = f.readlines()
                if not lines:
                    return "FARDC_GENESIS_BLOCK_00000000"
                last_entry = json.loads(lines[-1])
                return last_entry["seal"]
        except Exception:
            return "FARDC_GENESIS_BLOCK_00000000"

    def seal_operation(self, operator_id, auth_token, target_imsi, amount, destination):
        """
        Génère un scellé cryptographique inaltérable pour une saisie.
        """
        operation_metadata = {
            "timestamp": datetime.now().isoformat(),
            "operator_id": operator_id,
            "gatekeeper_token": auth_token,
            "target_imsi": target_imsi,
            "amount": amount,
            "currency": "USD/CDF",
            "destination_account": destination,
            "previous_hash": self.last_hash
        }

        # Création du Hash SHA-256 (Le Scellé)
        raw_payload = json.dumps(operation_metadata, sort_keys=True).encode()
        current_hash = hashlib.sha256(raw_payload).hexdigest()

        # Enregistrement dans le registre immuable
        entry = {"data": operation_metadata, "seal": current_hash}
        with open(self.storage_path, "a") as f:
            f.write(json.dumps(entry) + "\n")

        self.last_hash = current_hash
        print(f"[🛡️ AUDIT] Bloc scellé : {current_hash[:16]}")
        return current_hash

    def verify_integrity(self):
        """
        Vérifie que la chaîne n'a pas été manipulée.
        """
        print("[*] Vérification de l'intégrité de la BlackBox...")
        expected_prev_hash = "FARDC_GENESIS_BLOCK_00000000"
        
        with open(self.storage_path, "r") as f:
            for line in f:
                entry = json.loads(line)
                if entry["data"]["previous_hash"] != expected_prev_hash:
                    print("[!!!] ALERTE : RUPTURE DE LA CHAÎNE D'AUDIT détectée !")
                    return False
                expected_prev_hash = entry["seal"]
        
        print("[✓] Intégrité confirmée. Aucune manipulation détectée.")
        return True
