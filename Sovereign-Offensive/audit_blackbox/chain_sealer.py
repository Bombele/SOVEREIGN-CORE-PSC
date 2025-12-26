import json
import hashlib
import time

class ChainSealer:
    def __init__(self, log_path="data/audit/blackbox.log"):
        self.log_path = log_path

    def _get_last_hash(self):
        try:
            with open(self.log_path, "r") as f:
                lines = f.readlines()
                if not lines: return "0000000000000000"
                last_line = json.loads(lines[-1])
                return last_line.get("current_hash", "0000000000000000")
        except FileNotFoundError:
            return "0000000000000000"

    def log_action(self, action_type, details):
        prev_hash = self._get_last_hash()
        entry = {
            "type": action_type,
            "details": details,
            "prev_hash": prev_hash,
            "timestamp": time.time()
        }
        
        # Création du hash lié (Blockchain-lite)
        entry_data = json.dumps(entry, sort_keys=True).encode()
        entry["current_hash"] = hashlib.sha256(entry_data).hexdigest()

        with open(self.log_path, "a") as f:
            f.write(json.dumps(entry) + "\n")
        print(f"[AUDIT] Action {action_type} scellée avec succès.")
