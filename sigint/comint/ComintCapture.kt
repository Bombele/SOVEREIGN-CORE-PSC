package sigint.comint

import sigint.audit.LogManager
import kotlin.random.Random

/**
 * SRC - ComintCapture
 * Interface de capture radio pour COMINT.
 * Deux modes : MOCK (simulation) et SDR (réel).
 */
class ComintCapture(private val mode: Mode = Mode.MOCK) {

    enum class Mode { MOCK, SDR }

    /**
     * Capture un signal brut sur une fréquence donnée.
     * @param frequencyMHz Fréquence centrale
     * @param durationMs Durée de la capture
     * @return Buffer IQ simulé ou réel
     */
    fun capture(frequencyMHz: Double, durationMs: Long): ByteArray {
        return when (mode) {
            Mode.MOCK -> mockCapture(frequencyMHz, durationMs)
            Mode.SDR -> sdrCapture(frequencyMHz, durationMs)
        }
    }

    /**
     * Mode simulation : génère un buffer avec "signaux plausibles".
     */
    private fun mockCapture(frequencyMHz: Double, durationMs: Long): ByteArray {
        LogManager.info("COMINT_CAPTURE[MOCK]: freq=${frequencyMHz} MHz, dur=${durationMs} ms")
        val size = (durationMs / 10).toInt().coerceAtLeast(256)
        return ByteArray(size) { idx ->
            // Simule bruit + signal fort si fréquence connue
            val noise = Random.nextInt(-100, -80)
            if (frequencyMHz in 144.0..146.0 && idx % 50 == 0) (noise + 30).toByte() else noise.toByte()
        }
    }

    /**
     * Mode SDR : placeholder pour brancher un vrai périphérique.
     * Ici, on simule l'appel à une lib externe (rtl-sdr, hackrf).
     */
    private fun sdrCapture(frequencyMHz: Double, durationMs: Long): ByteArray {
        LogManager.info("COMINT_CAPTURE[SDR]: freq=${frequencyMHz} MHz, dur=${durationMs} ms")
        // TODO: brancher une lib SDR réelle (rtl-sdr, hackrf)
        // Exemple: exécuter une commande shell "rtl_sdr -f <freq> -n <samples>"
        return ByteArray(1024) { 0 } // buffer vide en attendant l’intégration réelle
    }
}
