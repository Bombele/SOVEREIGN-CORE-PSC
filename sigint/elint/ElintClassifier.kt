package sigint.elint

data class ElintSignature(val name: String, val prfHz: Double, val bandwidthKhz: Double, val tolerance: Double)

class ElintClassifier(private val profiles: List<ElintSignature>) {
    fun classify(prfHz: Double, bwKhz: Double): ElintSignature? {
        return profiles.minByOrNull { 
            kotlin.math.abs(it.prfHz - prfHz) + kotlin.math.abs(it.bandwidthKhz - bwKhz) 
        }?.takeIf { 
            kotlin.math.abs(it.prfHz - prfHz) <= it.tolerance && kotlin.math.abs(it.bandwidthKhz - bwKhz) <= it.tolerance
        }
    }
}
