import json
import hashlib

class ChainSealer:
    def __init__(self):
        self.log_path = "data/audit/blackbox.log"

    def get_last_hash(self):
        try:
            with open(self.log_path, "r") as f:
                last_line = f.readlines()[-1]
                return json.loads(last_line)['current_hash']
        except (IndexError, FileNotFoundError):
            return "0000000000000000"

    def log_action(self, action_type, details):
        last_hash = self.get_last_hash()
        
        entry = {
            "type": action_type,
            "details": details,
            "prev_hash": last_hash,
            "timestamp": time.time()
        }
        
        # Calcul du hash unique de l'entrée liée au passé
        entry_string = json.dumps(entry, sort_keys=True).encode()
        entry['current_hash'] = hashlib.sha256(entry_string).hexdigest()

        with open(self.log_path, "a") as f:
            f.write(json.dumps(entry) + "\n")

