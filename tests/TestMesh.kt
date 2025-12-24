package tests

import core.sync.MeshSyncEngine
import core.sync.UnifiedMessage
import core.sync.MessageType
import core.sync.PacketCodec
import core.audit.MissionLogger
import bft.models.BFTPosition
import services.dsp.ai_inference.ThreatMessage

/**
 * SRC - TestMesh
 * Vérifie la priorisation des messages dans MeshSyncEngine.
 */
object TestMesh {

    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST MESH INTEGRATION] ===")

        // 1. Démarrer le moteur Mesh
        MeshSyncEngine.startEngine()

        // 2. Créer un message BFT (routine)
        val bftPos = BFTPosition(
            unitId = "UNIT-BETA",
            unitType = "INFANTRY",
            latitude = 3.25,
            longitude = 29.90,
            altitude = 100.0,
            timestamp = System.currentTimeMillis()
        )
        val bftPayload = PacketCodec.encodeBFTPosition(bftPos)
        val bftMsg = UnifiedMessage(
            senderId = "UNIT-BETA",
            type = MessageType.BFT_POSITION,
            payload = bftPayload
        )

        // 3. Créer un message SIGINT (critique)
        val threat = ThreatMessage(
            id = "THR-001",
            type = "DMR",
            frequency = 145.0,
            latitude = 3.26,
            longitude = 29.91
        )
        val sigintPayload = PacketCodec.encodeThreat(threat)
        val sigintMsg = UnifiedMessage(
            senderId = "UNIT-ALPHA",
            type = MessageType.SIGINT_THREAT,
            payload = sigintPayload
        )

        // 4. Injecter les deux messages dans la file
        MeshSyncEngine.enqueueMessage(bftMsg)
        MeshSyncEngine.enqueueMessage(sigintMsg)

        // 5. Attendre quelques secondes pour observer la transmission
        Thread.sleep(3000)

        // 6. Arrêter le moteur Mesh
        MeshSyncEngine.stopEngine()

        MissionLogger.info("=== [TEST MESH COMPLETED] ===")
    }
}