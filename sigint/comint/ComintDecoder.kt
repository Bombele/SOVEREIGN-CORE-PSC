package sigint.comint

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class ComintProfile(val name: String, val band: String, val modulation: String, val bandwidthKhz: Double, val snrMinDb: Int)

class ComintDecoder(private val profilesPath: String = "sigint/comint/ComintProfiles.json") {
    private val mapper = jacksonObjectMapper()
    private val profiles: List<ComintProfile> = runCatching {
        mapper.readValue<File>(File(profilesPath))
            .let { mapper.readValue<Map<String, List<ComintProfile>>>(it) }["profiles"] ?: emptyList()
    }.getOrElse { emptyList() }

    fun selectProfile(freqMHz: Double, snrDb: Double): ComintProfile? {
        val band = if (freqMHz in 30.0..300.0) "VHF" else "UHF"
        return profiles.firstOrNull { it.band == band && snrDb >= it.snrMinDb }
    }
}
