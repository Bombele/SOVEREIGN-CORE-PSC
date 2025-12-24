package sigint.fisint.tests

import sigint.fisint.FisintAnalyzer
import sigint.fisint.FisintProfile
import sigint.sync.SyncWorker
import core.sync.MeshSyncEngine
import core.audit.MissionLogger

object FisintTest {
    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST FISINT MODULE] ===")

        MeshSyncEngine.startEngine()
        SyncWorker.startListening()

        val profiles = listOf(
            FisintProfile("DRONE_TELEMETRY_MAVLINK", 100, "FHSS", "ISM_2.4GHZ", 3)
        )
        val analyzer = FisintAnalyzer(profiles)

        // Simulation d'un signal cadencé à 100ms sur la bande ISM
        println("[TEST] Simulation télémétrie : Cadence 100ms détectée...")
        analyzer.analyzeTraffic(100, "ISM_2.4GHZ")

        Thread.sleep(1000)
        MeshSyncEngine.stopEngine()
        MissionLogger.info("=== [TEST FISINT COMPLETED] ===")
    }
}
