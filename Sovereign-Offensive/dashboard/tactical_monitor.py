import time
from core.gatekeeper import Gatekeeper
from connectivity.switch.iso8583_filter import ISO8583Filter

class TacticalDashboard:
    def __init__(self):
        self.active_missions = {}
        self.detection_risk = 0.0  # Jauge de 0 à 100%
        self.funds_recovered = 0.0 # Total saisi en FC/USD

    def update_telemetry(self, interception_data):
        """
        Reçoit les données en temps réel des vecteurs offensifs.
        """
        # Calcul de l'indicateur de succès
        if interception_data['status'] == 'REDIRECTED':
            self.funds_recovered += interception_data['amount']
            self.active_missions[interception_data['id']] = "SUCCESS"
        
        # Mise à jour du risque de détection (IA simulée)
        # Si trop de TCP Resets sont envoyés, le risque augmente
        if interception_data['method'] == 'NETWORK_INJECTION':
            self.detection_risk += 1.5 
            
        return self.get_operational_status()

    def get_operational_status(self):
        """
        Retourne l'état pour l'affichage écran.
        """
        return {
            "funds": f"{self.funds_recovered} USD",
            "risk_level": "LOW" if self.detection_risk < 30 else "CRITICAL",
            "risk_percent": self.detection_risk,
            "active_vectors": ["API_SOVEREIGN", "ISO8583_SWITCH"]
        }
