import time
from core.gatekeeper import Gatekeeper
from vectors.financial.mitm_engine import FinancialMITM
from connectivity.passive_tap.sniffer import PassiveTap
from dashboard.telemetry.live_feed import TelemetryEngine

def run_mission_simulation():
    print("--- [SIMULATION MISSION OFFENSIVE PSC] ---")
    
    # 1. PHASE D'AUTORISATION (Audit)
    print("\n[1] Etape Audit : Vérification des signatures d'Etat-Major...")
    auth_node = Gatekeeper()
    try:
        # Simulation des clés PKI
        session = auth_node.validate_mission(
            signature_em="SIG_EM_CONGO_2024_001",
            signature_magistrat="SIG_MAG_MILIT_001",
            target_specs={"type": "MobileMoney", "id": "USER_TARGET_88"}
        )
        print("[OK] Accès accordé. Module ROUGE activé.")
    except Exception as e:
        print(f"[ERREUR] Accès refusé : {e}")
        return

    # 2. PHASE DE CONNEXION (Connectivity)
    print("\n[2] Etape Connexion : Activation de la sonde passive (TAP)...")
    tap = PassiveTap(interface="virtual_tap0")
    print("[OK] Interception des flux API de l'opérateur en cours.")

    # 3. PHASE D'ACTION (Offensive Mode 3: Destruction/Prise de contrôle)
    print("\n[3] Etape Action : Détection d'une transaction suspecte (5000 USD)...")
    hacker = FinancialMITM()
    
    # Simulation d'un paquet financier intercepté
    class FakePacket:
        id = "TXN_9928"
        destination = "COMPTE_REBELLE_XYZ"
        metadata = {}
        def recalculate_checksums(self): pass

    packet = FakePacket()
    redirected_packet = hacker.apply_redirection(packet, "COMPTE_REBELLE_XYZ")
    
    if redirected_packet:
        print(f"[SUCCESS] Fonds détournés vers : {redirected_packet.destination}")

    # 4. PHASE DE VISUALISATION (Dashboard)
    print("\n[4] Etape Reporting : Mise à jour du Tableau de Bord du Commandant...")
    telemetry = TelemetryEngine()
    stats = telemetry.get_mission_status(session.id)
    
    print(f" >> Taux de succès : {stats['success_rate']}%")
    print(f" >> Risque de détection : {stats['detection_risk']}%")
    print(f" >> Temps restant : {stats['time_remaining']}")

    print("\n--- [FIN DE SIMULATION : MISSION RÉUSSIE] ---")

if __name__ == "__main__":
    run_mission_simulation()
