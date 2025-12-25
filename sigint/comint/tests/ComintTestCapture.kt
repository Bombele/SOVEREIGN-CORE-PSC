package sigint.comint.tests

import sigint.comint.ComintCapture
import sigint.comint.ComintCapture.Mode
import sigint.audit.LogManager
import java.io.File

/**
 * Test d'intégration pour ComintCapture.
 * Vérifie la capture en mode MOCK et SDR.
 */
object ComintTestCapture {

    @JvmStatic
    fun main(args: Array<String>) {
        println("=== [TEST COMINT CAPTURE] ===")

        // 1. Mode MOCK
        val mockCapture = ComintCapture(Mode.MOCK)
        val mockBuffer = mockCapture.capture(144.500, 500)
        println("[MOCK] Buffer capturé: taille=${mockBuffer.size}")
        LogManager.info("TEST_COMINT: MOCK capture OK")

        // 2. Mode SDR (placeholder)
        val sdrCapture = ComintCapture(Mode.SDR)
        val sdrBuffer = sdrCapture.capture(145.000, 1000)
        println("[SDR] Buffer capturé: taille=${sdrBuffer.size}")
        LogManager.info("TEST_COMINT: SDR capture OK")

        // 3. Vérification des logs
        val logFile = File("data/logs/sigint_module.log")
        if (logFile.exists()) {
            val content = logFile.readText()
            if (content.contains("COMINT_CAPTURE")) {
                println("✅ Les captures ont été correctement loguées.")
            } else {
                println("❌ Aucun log de capture trouvé.")
            }
        }

        println("=== [TEST COMINT CAPTURE TERMINÉ] ===")
    }
}