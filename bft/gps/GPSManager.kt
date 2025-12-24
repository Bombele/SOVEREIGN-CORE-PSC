
package bft.gps

import core.audit.MissionLogger
import core.security.TacticalWipeManager
import bft.models.BFTPosition

/**
 * SRC - GPSManager
 * Gère l'acquisition des coordonnées et alimente le Geofencing.
 */
object GPSManager {

    private var lastValidPosition: BFTPosition? = null
    private var isGpsLocked: Boolean = false

    /**
     * Simule ou interface l'acquisition GPS réelle
     */
    fun onLocationChanged(lat: Double, lon: Double, alt: Double, accuracy: Float) {
        // 1. Filtrage par précision (Ignorer si l'erreur est > 50m en zone de combat)
        if (accuracy > 50.0) {
            MissionLogger.warning("GPS_LOW_ACCURACY: ${accuracy}m. Position ignored.")
            return
        }

        val currentPos = BFTPosition(
            unitId = "LOCAL_NODE", // ID de l'appareil
            unitType = "OPERATOR",
            latitude = lat,
            longitude = lon,
            altitude = alt,
            timestamp = System.currentTimeMillis()
        )

        lastValidPosition = currentPos
        isGpsLocked = true

        // 2. Feed immédiat au Geofencing (Sécurité)
        checkSecurityZone(lat, lon)

        // 3. Notification au BFT pour diffusion Mesh
        MissionLogger.info("GPS_FIX_ACQUIRED: [$lat, $lon] (Acc: ${accuracy}m)")
    }

    private fun checkSecurityZone(lat: Double, lon: Double) {
        // Appel au module de sécurité que nous avons créé
        // Si la position est hors zone, le wipe est déclenché ici
        val wipeManager = TacticalWipeManager("core/security/active_geofence.poly")
        if (!wipeManager.isPositionSafe(lat, lon)) {
            MissionLogger.critical("GEOFENCE_BREACH_DETECTED: Position [$lat, $lon] is unauthorized.")
            wipeManager.executeEmergencyWipe("EXIT_AUTHORIZED_ZONE")
        }
    }

    fun getLastPosition(): BFTPosition? = lastValidPosition
    fun hasFix(): Boolean = isGpsLocked
}
