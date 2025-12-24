package sigint.elint

import sigint.audit.LogManager
import kotlin.math.abs

class ElintClassifier(private val profiles: List<ElintSignature>) {

    fun classify(detectedPrf: Double, detectedBw: Double): ElintSignature? {
        // On cherche la signature la plus proche dans la bibliothèque
        val match = profiles.find { profile ->
            abs(profile.prfHz - detectedPrf) <= profile.tolerance &&
            abs(profile.bandwidthKhz - detectedBw) <= profile.tolerance
        }

        if (match != null) {
            LogManager.info("ELINT_MATCH: Radar détecté -> ${match.name}")
        } else {
            LogManager.warn("ELINT_UNKNOWN: Signal pulsé non répertorié (PRF: $detectedPrf Hz)")
        }
        return match
    }
}
