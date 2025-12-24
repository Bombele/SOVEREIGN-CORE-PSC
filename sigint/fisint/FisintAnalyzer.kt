package sigint.fisint

data class FisintEvent(val frequencyMHz: Double, val cadenceMs: Long, val rssiDbm: Double, val timestamp: Long)

class FisintAnalyzer {
    fun detect(events: List<Pair<Double, Double>>): List<FisintEvent> {
        // Exemple: détection de transmissions périodiques
        return events.filter { it.second > -80.0 }.map {
            FisintEvent(it.first, cadenceMs = 1000L, rssiDbm = it.second, timestamp = System.currentTimeMillis())
        }
    }
}
