package sigint.elint.tests

import sigint.elint.*
import sigint.core.EventBus
import sigint.sync.SyncWorker
import core.sync.MeshSyncEngine
import core.audit.MissionLogger

object ElintTest {
    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST ELINT MODULE] ===")

        MeshSyncEngine.startEngine()
        SyncWorker.startListening()

        // Simulation de signatures ELINT chargées
        val signatures = listOf(
            ElintSignature("SURV_RADAR_X", 1200.0, 5000.0, 50.0)
        )
        val classifier = ElintClassifier(signatures)

        // Simuler la détection d'un radar de surveillance (1210 Hz, 4990 kHz)
        classifier.classify(detectedPrf = 1210.0, detectedBw = 4990.0)

        Thread.sleep(1000)
        MeshSyncEngine.stopEngine()
        MissionLogger.info("=== [TEST ELINT COMPLETED] ===")
    }
}
