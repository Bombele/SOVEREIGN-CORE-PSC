package sigint.comint

import kotlin.math.log10
import kotlin.math.sqrt

/**
 * SRC - ComintUtils
 * Fonctions utilitaires pour le traitement numérique du signal (DSP).
 */
object ComintUtils {

    /**
     * Calcule l'amplitude (Magnitude) à partir d'une paire d'échantillons IQ.
     * Mag = sqrt(I² + Q²)
     */
    fun calculateMagnitude(i: Byte, q: Byte): Double {
        return sqrt((i.toDouble() * i + q.toDouble() * q))
    }

    /**
     * Convertit une valeur d'amplitude en décibels (dB).
     * Utile pour visualiser la puissance relative.
     */
    fun toDecibels(amplitude: Double): Double {
        if (amplitude <= 0) return -100.0
        return 20 * log10(amplitude)
    }

    /**
     * Calcule le SNR (Signal-to-Noise Ratio) estimé d'un buffer.
     * @param buffer Données IQ brutes
     * @return SNR en dB
     */
    fun estimateSnr(buffer: ByteArray): Double {
        if (buffer.isEmpty()) return 0.0

        // Calcul de la puissance moyenne
        var sumPower = 0.0
        buffer.forEach { sumPower += (it.toDouble() * it) }
        val avgPower = sumPower / buffer.size

        // Simulation simple : on compare la puissance totale au "bruit de plancher"
        // Dans un vrai système, on utiliserait une FFT pour isoler le bruit
        val noiseFloor = 10.0 // Valeur arbitraire pour le mock
        val snr = 10 * log10(avgPower / noiseFloor)

        return if (snr.isNaN() || snr < 0) 0.0 else snr
    }

    /**
     * Estime la puissance en dBm (Décibel-milliwatts).
     * Note: Nécessite un étalonnage avec le hardware SDR réel.
     */
    fun estimatePowerDbm(buffer: ByteArray): Double {
        val snr = estimateSnr(buffer)
        return snr - 105.0 // -105 dBm est souvent le plancher de bruit d'une clé RTL-SDR
    }
}

