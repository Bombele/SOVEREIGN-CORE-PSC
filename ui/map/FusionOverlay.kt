package ui.map

import bft.models.BFTPosition
import core.sync.UnifiedMessage
import core.sync.MessageType
import core.sync.PacketCodec
import services.dsp.ai_inference.ThreatMessage
import ui.map.render.TacticalIcon

/**
 * SRC - FusionOverlay
 * Gère l'affichage combiné BFT (Bleu) et SIGINT (Rouge) sur la carte.
 */
object FusionOverlay {

    // Listes pour le rendu graphique
    private val friendlyIcons = mutableMapOf<String, TacticalIcon>()
    private val hostileZones = mutableMapOf<String, TacticalIcon>()

    /**
     * Traite un message unifié entrant pour mise à jour graphique
     */
    fun processIncomingMessage(msg: UnifiedMessage) {
        when (msg.type) {
            MessageType.BFT_POSITION -> {
                val pos = PacketCodec.decodeCBOR(msg.payload) as? BFTPosition
                pos?.let { updateFriendlyUnit(it) }
            }
            MessageType.SIGINT_THREAT -> {
                val threat = PacketCodec.decodeCBOR(msg.payload) as? ThreatMessage
                threat?.let { updateHostileThreat(it) }
            }
            else -> { /* Autres types : ordres, status */ }
        }
    }

    private fun updateFriendlyUnit(pos: BFTPosition) {
        // Met à jour ou crée un icone bleu (Standard OTAN APP-6)
        friendlyIcons[pos.unitId] = TacticalIcon(
            type = "FRIENDLY",
            lat = pos.latitude,
            lon = pos.longitude,
            label = pos.unitId,
            color = 0x0000FF // Bleu
        )
    }

    private fun updateHostileThreat(threat: ThreatMessage) {
        // Crée une zone de menace rouge (Cercle ou Hexagone)
        hostileZones[threat.id] = TacticalIcon(
            type = "HOSTILE_SIGINT",
            lat = threat.latitude,
            lon = threat.longitude,
            label = "${threat.type} @ ${threat.frequency}MHz",
            color = 0xFF0000 // Rouge
        )
    }

    fun renderAll(canvas: TacticalCanvas) {
        // Affiche la couche SIGINT en premier (dessous)
        hostileZones.values.forEach { it.draw(canvas) }
        // Affiche la couche BFT en second (dessus pour visibilité)
        friendlyIcons.values.forEach { it.draw(canvas) }
    }
}
