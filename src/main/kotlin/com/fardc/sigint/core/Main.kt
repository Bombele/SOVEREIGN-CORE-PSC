package com.fardc.sigint.core

import java.util.Scanner
import kotlin.system.exitProcess

/**
 * SRC - Système de Renseignement de Combat
 * Point d'entrée principal du Cœur (Sentinel-Alpha)
 */
fun main(args: Array<String>) {
    println("""
        ==================================================
        🛡️ SRC - SENTINEL-ALPHA v1.0.0
        Forces Armées de la RDC - État-Major
        ==================================================
    """.trimIndent())

    // 1. Initialisation du Gatekeeper (Sécurité PKI)
    val gatekeeper = Gatekeeper()
    println("[CORE] Initialisation du Gatekeeper... OK")

    // 2. Lancement du Pont avec les modules Python (OffensiveBridge)
    val bridge = OffensiveBridge(gatekeeper)
    Thread {
        try {
            bridge.startListening()
        } catch (e: Exception) {
            println("[ERREUR] Échec du démarrage du Pont : ${e.message}")
        }
    }.start()

    println("[CORE] Système opérationnel. En attente de commandes d'État...")
    println("[INFO] Appuyez sur 'Q' pour arrêter le système proprement.")

    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLine()) {
        val input = scanner.nextLine()
        if (input.equals("Q", ignoreCase = true)) {
            println("[CORE] Arrêt du système sécurisé...")
            exitProcess(0)
        }
    }
}

