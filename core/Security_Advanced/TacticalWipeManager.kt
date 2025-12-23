package core.security

import core.audit.MissionLogger
import java.io.File
import java.security.SecureRandom
import kotlin.system.exitProcess

/**
 * SRC - TacticalWipeManager
 * Gère la survie des données sensibles en zone hostile.
 */
class TacticalWipeManager(private val polyFilePath: String) {

    private var activePolygon: List<Pair<Double, Double>> = emptyList()

    init {
        loadGeofence()
    }

    private fun loadGeofence() {
        val file = File(polyFilePath)
        if (file.exists()) {
            activePolygon = file.readLines().mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size == 2) parts[0].toDouble() to parts[1].toDouble() else null
            }
        }
    }

    /**
     * Algorithme de Ray Casting pour vérifier si la position GPS est dans la zone autorisée.
     */
    fun isPositionSafe(lat: Double, lon: Double): Boolean {
        if (activePolygon.isEmpty()) return true // Si pas de polygone, on considère la zone comme libre
        var isInside = false
        var j = activePolygon.size - 1
        for (i in activePolygon.indices) {
            if (((activePolygon[i].second > lon) != (activePolygon[j].second > lon)) &&
                (lat < (activePolygon[j].first - activePolygon[i].first) * (lon - activePolygon[i].second) / 
                (activePolygon[j].second - activePolygon[i].second) + activePolygon[i].first)) {
                isInside = !isInside
            }
            j = i
        }
        return isInside
    }

    /**
     * Déclenche l'effacement définitif des données (Panic Wipe).
     */
    fun executeEmergencyWipe(reason: String) {
        MissionLogger.critical("PANIC_WIPE_INITIATED: Reason -> $reason")
        
        val targets = listOf("data/signatures/", "data/keys/", "core/audit/logs/", "data/reports/")
        val secureRandom = SecureRandom()

        targets.forEach { path ->
            val dir = File(path)
            if (dir.exists()) {
                dir.walkBottomUp().forEach { file ->
                    if (file.isFile) {
                        // Overwrite with random data before deletion (Anti-Forensics)
                        val length = file.length()
                        val randomBytes = ByteArray(length.toInt().coerceAtMost(1024 * 1024))
                        secureRandom.nextBytes(randomBytes)
                        file.writeBytes(randomBytes)
                    }
                    file.delete()
                }
            }
        }
        
        MissionLogger.critical("PANIC_WIPE_COMPLETED. Self-destructing process.")
        exitProcess(0)
    }
}
