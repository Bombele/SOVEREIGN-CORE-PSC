package services.dsp.ai_inference

import java.io.File

class SignalClassifier(
    private val tflitePath: String
) {
    // TODO: intégrer le runtime TFLite (plateforme spécifique)
    fun loadModel(): Boolean = File(tflitePath).exists()
    fun classify(spectrum: FloatArray): Pair<String, Double> {
        // Placeholder: renvoie (classe, confiance)
        return "VHF_FM" to 0.72
    }
}