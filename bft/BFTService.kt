package bft

import bft.gps.BFTPosition
import core.audit.MissionLogger
import core.sync.UnifiedMessage
import core.sync.DataType
import core.sync.MeshSyncEngine

/**
 * Module BFT Intégré
 * Gère la localisation des forces amies.
 */
object BFTService {
    // Stockage en RAM des positions des alliés (Clé: unitID)
    private val peerPositions = mutableMapOf<String, BFTPosition>()

    /**
     * Met à jour ma position et la diffuse au Mesh
     */
    fun updateAndBroadcastMyPosition(unitName: String, lat: Double, lon: Double) {
        val myPos = BFTPosition(
            unitName = unitName,
            latitude = lat,
            longitude = lon
        )

        // 1. Log local pour l'audit (Signé via MissionLogger)
        MissionLogger.info("BFT_SELF_UPDATE: $unitName at [$lat, $lon]")

        // 2. Encapsulation pour le Mesh
        val syncMsg = UnifiedMessage(
            type = DataType.BFT_POS,
            senderId = unitName,
            priority = 3, // Routine
            payload = myPos,
            signature = "HMAC_SIG_PLACEHOLDER"
        )

        // 3. Diffusion aux autres unités
        MeshSyncEngine.enqueueMessage(syncMsg)
    }

    /**
     * Traite une position reçue d'un allié via le Mesh
     */
    fun processPeerPosition(pos: BFTPosition) {
        peerPositions[pos.unitName] = pos
        MissionLogger.info("BFT_PEER_UPDATE: ${pos.unitName} updated.")
    }

    fun getAllFriendlyPositions(): List<BFTPosition> = peerPositions.values.toList()
}

