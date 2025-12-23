// core/security/GeofenceManager.kt
object GeofenceManager {
    private var activePolygon: List<Pair<Double, Double>> = emptyList()

    fun loadGeofence(polyFile: String) {
        val file = File(polyFile)
        if (file.exists()) {
            activePolygon = file.readLines().mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size == 2) parts[0].toDouble() to parts[1].toDouble() else null
            }
        }
    }

    // Algorithme Ray Casting pour vérifier si la position est dans la zone
    fun isInAuthorizedZone(lat: Double, lon: Double): Boolean {
        if (activePolygon.isEmpty()) return true // Protection si pas de zone définie
        
        var isInside = false
        var j = activePolygon.size - 1
        for (i in activePolygon.indices) {
            val pi = activePolygon[i]
            val pj = activePolygon[j]
            if (((pi.second > lon) != (pj.second > lon)) &&
                (lat < (pj.first - pi.first) * (lon - pi.second) / (pj.second - pi.second) + pi.first)) {
                isInside = !isInside
            }
            j = i
        }
        return isInside
    }
}

