package com.psc.sovereign.core

class OffensiveBridge {
    fun startFinancialInterception() {
        println("🚀 Activation du module de Saisie Conservatoire Numérique...")
        val process = ProcessBuilder("python3", "vectors/financial/auto_seizure.py")
            .inheritIO()
            .start()
    }
}
