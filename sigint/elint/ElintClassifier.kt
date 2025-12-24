package sigint.elint

import sigint.audit.LogManager
import sigint.core.EventBus
import kotlin.math.abs

data class RadarProfile(val name: String, val prfHz: Double, val pwUs: Double, val band: String, val tolerance: Double, val threatLevel: Int)

class ElintClassifier(private val profiles: List<RadarProfile>) {

    fun identify(measuredPrf: Double, measuredPw: Double) {
        val match = profiles.find { 
            abs(it.prfHz - measuredPrf) <= it.tolerance && abs(it.pwUs - measuredPw) <= (it.tolerance / 10)
        }

        if (match != null) {
            LogManager.warn("ELINT_MATCH: ${match.name} détecté (PRF: $measuredPrf Hz, Band: ${match.band})")
            EventBus.publish("ELINT_DETECTION", mapOf(
                "target" to match.name,
                "threat_level" to match.threatLevel,
                "confidence" to 0.95
            ))
        } else {
            LogManager.info("ELINT_SCAN: Pulse détecté mais non répertorié (PRF: $measuredPrf Hz)")
        }
    }
}
