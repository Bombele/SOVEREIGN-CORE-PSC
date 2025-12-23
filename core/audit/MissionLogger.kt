package core.audit

import java.io.File
import java.security.MessageDigest
import java.util.*

object MissionLogger {
    private val logFile = File("core/audit/logs/mission.log.enc")
    private var lastHash: String = ""

    init {
        if (!logFile.parentFile.exists()) logFile.parentFile.mkdirs()
        // Au démarrage, on recalcule le dernier hash pour continuer la chaîne
        lastHash = recoverLastHash()
    }

    /**
     * Calcule le hash SHA-256 d'une chaîne
     */
    private fun hashString(input: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }

    /**
     * Récupère le hash de la dernière ligne valide du fichier
     */
    private fun recoverLastHash(): String {
        if (!logFile.exists() || logFile.length() == 0L) return "GENESIS_BLOCK"
        return try {
            val lastLine = logFile.readLines().last()
            lastLine.split("|")[0] // Le hash est stocké au début de la ligne
        } catch (e: Exception) {
            "CORRUPTED_CHAIN"
        }
    }

    /**
     * Enregistre un événement avec hachage enchaîné
     */
    fun log(level: String, event: String) {
        val timestamp = System.currentTimeMillis()
        val payload = "$level:$event:$timestamp:$lastHash"
        val currentHash = hashString(payload)
        
        // Format : HASH_ACTUEL | LEVEL | EVENT | TIMESTAMP | HASH_PRECEDENT
        val logEntry = "$currentHash | $level | $event | $timestamp | $lastHash\n"
        
        try {
            logFile.appendText(logEntry)
            lastHash = currentHash
        } catch (e: Exception) {
            // Si l'écriture échoue, on ne peut plus garantir l'intégrité
            System.err.println("CRITICAL: Failed to write to mission log!")
        }
    }

    fun critical(event: String) = log("CRITICAL", event)
    fun info(event: String) = log("INFO", event)

    /**
     * Vérifie l'intégrité complète du journal
     * @return true si la chaîne est intacte, false si une falsification est détectée
     */
    fun verifyIntegrity(): Boolean {
        if (!logFile.exists()) return true
        var expectedPreviousHash = "GENESIS_BLOCK"
        
        logFile.forEachLine { line ->
            val parts = line.split(" | ")
            if (parts.size != 5) return false
            
            val currentHash = parts[0]
            val level = parts[1]
            val event = parts[2]
            val ts = parts[3]
            val prevHash = parts[4]
            
            if (prevHash != expectedPreviousHash) return false
            
            val recalculatedHash = hashString("$level:$event:$ts:$prevHash")
            if (currentHash != recalculatedHash) return false
            
            expectedPreviousHash = currentHash
        }
        return true
    }
}
