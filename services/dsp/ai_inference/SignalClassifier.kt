package services.dsp.ai_inference

import core.sync.ThreatMessage
import core.audit.MissionLogger
import java.io.File

/**
 * SRC - SignalClassifier
 * Analyse le spectre radio et génère des ThreatMessages.
 */
class SignalClassifier(private val tfliteModelPath: String) {

    private var modelLoaded: Boolean = false

    /**
     * Charge le modèle TensorFlow Lite depuis le disque.
     */
    fun loadModel(): Boolean {
        val file = File(tfliteModelPath)
        modelLoaded = file.exists()
        if (modelLoaded) {
            MissionLogger.info("TFLite model loaded: $tfliteModelPath")
        } else {
            MissionLogger.warning("TFLite model not found: $tfliteModelPath")
        }
        return modelLoaded
    }

    /**
     * Classifie un spectre radio brut et retourne un ThreatMessage.
     * @param spectrum : tableau de floats représentant l’énergie par fréquence.
     * @param latitude : position GPS de l’opérateur.
     * @param longitude : position GPS de l’opérateur.
     */
    fun classifySpectrum(spectrum: FloatArray, latitude: Double, longitude: Double): ThreatMessage? {
        if (!modelLoaded) {
            MissionLogger.warning("Model not loaded. Cannot classify spectrum.")
            return null
        }

        // TODO: Intégrer l’inférence réelle TFLite
        // Simulation : détection d’une modulation FM sur 144.500 MHz
        val detectedType = "VHF_FM"
        val detectedFreq = 144.500
        val priority = 2 // Medium

        val threat = ThreatMessage(
            type = detectedType,
            frequency = detectedFreq,
            latitude = latitude,
            longitude = longitude,
            priority = priority
        )

        MissionLogger.info("Spectrum classified: ${threat.type} at ${threat.frequency}MHz")
        return threat
    }
}