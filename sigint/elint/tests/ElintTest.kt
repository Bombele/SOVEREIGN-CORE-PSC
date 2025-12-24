package sigint.elint.tests

import sigint.elint.ElintClassifier
import sigint.elint.RadarProfile
import sigint.sync.SyncWorker
import core.sync.MeshSyncEngine
import core.audit.MissionLogger

object ElintTest {
    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST ELINT MODULE] ===")

        MeshSyncEngine.startEngine()
        SyncWorker.startListening()

        // 1. Initialisation avec profils de test
        val profiles = listOf(
            RadarProfile("DRONE_SEARCH_RADAR", 1200.0, 15.0, "S-BAND", 50.0, 4)
        )
        val classifier = ElintClassifier(profiles)

        // 2. Simulation d'un radar de drone détecté (paramètres dans la tolérance)
        println("[TEST] Simulation pulses: PRF 1210 Hz, PW 15.2 us...")
        classifier.identify(1210.0, 15.2)

        Thread.sleep(1500)
        MeshSyncEngine.stopEngine()
        MissionLogger.info("=== [TEST ELINT COMPLETED] ===")
    }
}
