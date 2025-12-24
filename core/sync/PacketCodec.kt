package core.sync

import com.fasterxml.jackson.dataformat.cbor.CBORFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.protobuf.InvalidProtocolBufferException
import core.audit.MissionLogger

/**
 * SRC - PacketCodec
 * Sérialisation compacte des UnifiedMessage pour transmission Mesh.
 * Supporte CBOR (furtif) et Protobuf (rapide).
 */
object PacketCodec {

    // CBOR mapper (furtivité, taille réduite)
    private val cborMapper = ObjectMapper(CBORFactory())

    /**
     * Encode un UnifiedMessage en CBOR
     */
    fun encodeCBOR(msg: UnifiedMessage): ByteArray {
        return try {
            cborMapper.writeValueAsBytes(msg)
        } catch (e: Exception) {
            MissionLogger.warning("CBOR encode failed: ${e.message}")
            ByteArray(0)
        }
    }

    /**
     * Decode un UnifiedMessage depuis CBOR
     */
    fun decodeCBOR(data: ByteArray): UnifiedMessage? {
        return try {
            cborMapper.readValue(data, UnifiedMessage::class.java)
        } catch (e: Exception) {
            MissionLogger.warning("CBOR decode failed: ${e.message}")
            null
        }
    }

    /**
     * Encode un UnifiedMessage en Protobuf
     * (nécessite un schéma .proto compilé en classes Java/Kotlin)
     */
    fun encodeProtobuf(protoMsg: UnifiedMessageProto.UnifiedMessage): ByteArray {
        return protoMsg.toByteArray()
    }

    /**
     * Decode un UnifiedMessage depuis Protobuf
     */
    fun decodeProtobuf(data: ByteArray): UnifiedMessageProto.UnifiedMessage? {
        return try {
            UnifiedMessageProto.UnifiedMessage.parseFrom(data)
        } catch (e: InvalidProtocolBufferException) {
            MissionLogger.warning("Protobuf decode failed: ${e.message}")
            null
        }
    }
}