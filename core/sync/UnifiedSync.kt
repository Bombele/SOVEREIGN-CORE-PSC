package core.sync

import bft.gps.BFTPosition
import core.audit.MissionLogger
import core.security.TacticalWipeManager

enum class DataType { BFT_POS, SIGINT_THREAT, CMD_ORDER }

data class UnifiedMessage(
    val type: DataType,
    val senderId: String,
    val priority: Int, // 1 = Critique, 3 = Routine
    val payload: Any,  // BFTPosition ou ThreatMessage
    val signature: String
)

object GlobalSyncManager {
    fun processIncoming(msg: UnifiedMessage) {
        // Le MissionLogger signe tout, peu importe la source
        MissionLogger.info("Sync Received: ${msg.type} from ${msg.senderId}")

        when(msg.type) {
            DataType.BFT_POS -> BFTManager.updatePeerLocation(msg.payload as BFTPosition)
            DataType.SIGINT_THREAT -> SignalClassifier.handleRemoteThreat(msg.payload as ThreatMessage)
            DataType.CMD_ORDER -> handleCommand(msg.payload)
        }
    }
}
