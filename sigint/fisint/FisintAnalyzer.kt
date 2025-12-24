package sigint.fisint

import sigint.audit.LogManager
import sigint.core.EventBus

data class FisintProfile(val name: String, val cadenceMs: Long, val modulation: String, val frequencyBand: String, val threatLevel: Int)

class FisintAnalyzer(private val profiles: List<FisintProfile>) {

    fun analyze(observedCadenceMs: Long, detectedBand: String) {
        // Recherche d'un profil correspondant avec une tolérance de 5ms
        val match = profiles.find { 
            observedCadenceMs in (it.cadenceMs - 5)..(it.cadenceMs + 5) && it.frequencyBand == detectedBand 
        }

        if (match != null) {
            LogManager.warn("FISINT_MATCH: Flux technique identifié -> ${match.name} (Période: $observedCadenceMs ms)")
            EventBus.publish("FISINT_DETECTION", mapOf(
                "sensor" to match.name,
                "modulation" to match.modulation,
                "threat" to match.threatLevel
            ))
        } else {
            LogManager.info("FISINT_SCAN: Signal périodique inconnu détecté ($observedCadenceMs ms)")
        }
    }
}
