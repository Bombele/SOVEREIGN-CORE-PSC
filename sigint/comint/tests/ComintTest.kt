package sigint.comint.tests

import sigint.comint.ComintAnalyzer
import sigint.comint.ComintDecoder
import sigint.sync.SyncWorker
import core.sync.MeshSyncEngine
import core.audit.MissionLogger

/**
 * SRC - ComintTest
 * Vérifie que la détection COMINT est publiée et relayée dans le Mesh.
 */
object ComintTest {

    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST COMINT MODULE] ===")

        // 1. Démarrer le Mesh et SyncWorker
        MeshSyncEngine.startEngine()
        SyncWorker.startListening()

        // 2. Charger le décodeur COMINT
        val decoder = ComintDecoder("sigint/comint/ComintProfiles.json")
        val analyzer = ComintAnalyzer(decoder)

        // 3. Simuler un signal VHF voix avec SNR suffisant
        analyzer.processSignal(frequencyMHz = 145.000, snrDb = 15.0, rawIqSize = 1024)

        // 4. Simuler un signal inconnu fort
        analyzer.processSignal(frequencyMHz = 299.000, snrDb = 25.0, rawIqSize = 2048)

        // 5. Attendre quelques secondes pour observer la transmission
        Thread.sleep(2000)

        // 6. Arrêter le Mesh
        MeshSyncEngine.stopEngine()

        MissionLogger.info("=== [TEST COMINT COMPLETED] ===")
    }
}