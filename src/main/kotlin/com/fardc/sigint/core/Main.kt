package com.psc.sovereign.core

import kotlin.system.exitProcess
import java.util.Scanner

fun main(args: Array<String>) {
    println("""
        ==================================================
        🛡️ SOVEREIGN-CORE-PSC v1.0.0
        Système de Capacité Offensive Souveraine
        ==================================================
    """.trimIndent())

    val gatekeeper = Gatekeeper()
    val bridge = OffensiveBridge(gatekeeper)

    // Lancement du pont de communication avec les vecteurs Python
    Thread {
        try {
            bridge.startListening()
        } catch (e: Exception) {
            println("[ERREUR] Échec du pont PSC : ${e.message}")
        }
    }.start()

    println("[CORE] Cœur PSC opérationnel et rattaché au Switch National.")
    
    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLine()) {
        if (scanner.nextLine().uppercase() == "Q") {
            println("[CORE] Arrêt sécurisé du système PSC...")
            exitProcess(0)
        }
    }
}
