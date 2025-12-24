package tests

import bft.gps.GPSManager
import bft.models.BFTPosition
import services.dsp.ai_inference.ThreatMessage
import ui.map.FusionOverlay
import ui.map.render.TacticalRenderer
import core.audit.MissionLogger

/**
 * SRC - TestFusion
 * Vérifie que FusionOverlay affiche correctement GPS, BFT et SIGINT.
 */
object TestFusion {

    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST FUSION OVERLAY] ===")

        // 1. Simuler une position locale via GPSManager
        GPSManager.onLocationChanged(
            lat = 3.21,
            lon = 29.87,
            alt = 120.0,
            accuracy = 10.0f
        )
        val myPos = GPSManager.getLastPosition()!!

        // 2. Simuler des positions alliées (BFT)
        val allies = listOf(
            BFTPosition("UNIT-ALPHA", "INFANTRY", 3.22, 29.88, 100.0, System.currentTimeMillis()),
            BFTPosition("UNIT-BRAVO", "ARMOR", 3.23, 29.89, 150.0, System.currentTimeMillis())
        )

        // 3. Simuler des menaces SIGINT
        val threats = listOf(
            ThreatMessage("THR-001", "DMR", 145.0, 3.24, 29.90),
            ThreatMessage("THR-002", "JAMMER", 433.0, 3.25, 29.91)
        )

        // 4. Créer un renderer factice
        val renderer = TacticalRenderer()

        // 5. Appeler FusionOverlay
        FusionOverlay.render(renderer, myPos, allies, threats)

        // 6. Vérifier les logs
        MissionLogger.info("=== [TEST FUSION COMPLETED] ===")
    }
}