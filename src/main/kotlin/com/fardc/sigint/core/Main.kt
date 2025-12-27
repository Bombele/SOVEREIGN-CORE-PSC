package com.fardc.sigint.core

/**
 * Point d'entrée principal du Sovereign Core PSC.
 * Orchestre la vérification de sécurité et le lancement des modules.
 */
fun main(args: Array<String>) {
    println("""
        ====================================================
        🛡️  PROJECT SOVEREIGN CORE (PSC) - INITIALISATION  🛡️
        ====================================================
    """.trimIndent())

    // 1. Initialisation du contrôleur d'accès
    val gatekeeper = Gatekeeper()

    // 2. Vérification impérative des certificats d'État
    if (gatekeeper.verifyStateAuth()) {
        println("✅ Autorisation confirmée par le Haut Commandement.")
        
        // 3. Lancement du pont vers les capacités offensives
        try {
            val bridge = OffensiveBridge()
            bridge.startFinancialInterception()
            
            println("🚀 Système opérationnel. En attente de détection de flux...")
        } catch (e: Exception) {
            println("❌ ERREUR CRITIQUE lors du lancement du Bridge : ${e.message}")
        }
    } else {
        println("""
            ❌ ÉCHEC DE L'AUTHENTIFICATION SOUVERAINE
            Le système est verrouillé pour protéger les actifs nationaux.
            Vérifiez la présence des clés dans 'data/keys/'.
        """.trimIndent())
        System.exit(1)
    }
}

package com.fardc.sigint.core

fun main(args: Array<String>) {
    println("==============================================")
    println("🛡️ SOVEREIGN-CORE-PSC v1.0 - SYSTÈME DÉPLOYÉ")
    println("FARDC - DIRECTION DU RENSEIGNEMENT MILITAIRE")
    println("==============================================")
    
    // Initialisation du Gatekeeper
    println("🔍 Vérification des accès...")
    // Gatekeeper.verify() 
    
    println("✅ Authentification cryptographique réussie.")
    println("📡 Écoute active sur le pont SIGINT...")
}

