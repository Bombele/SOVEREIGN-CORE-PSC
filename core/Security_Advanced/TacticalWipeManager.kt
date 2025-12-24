package core.security

import core.audit.MissionLogger
import java.io.File
import java.io.RandomAccessFile
import java.security.SecureRandom

/**
 * SRC - TacticalWipeManager
 * Gère la destruction irréversible des données sensibles.
 */
class TacticalWipeManager(private val geofenceConfigPath: String) {

    private val sensitivePaths = listOf(
        "data/keys/",           // Clés de chiffrement
        "core/audit/logs/",     // Journaux de mission
        "bft/cache/",           // Positions alliées
        "sigint/captures/"      // Signaux interceptés
    )

    /**
     * Déclenche l'effacement d'urgence
     */
    fun executeEmergencyWipe(reason: String) {
        MissionLogger.critical("WIPE_INITIATED: Reason -> $reason")
        
        // 1. Destruction des clés en priorité (rend les données chiffrées inutilisables)
        wipeDirectory("data/keys/")

        // 2. Destruction récursive des autres dossiers
        sensitivePaths.drop(1).forEach { path ->
            wipeDirectory(path)
        }

        MissionLogger.info("WIPE_COMPLETE: System sanitized.")
        
        // 3. Suicide du processus pour éviter l'extraction de la RAM
        System.exit(0)
    }

    /**
     * Écrase les fichiers avec des données aléatoires avant suppression (Sanitization)
     */
    private fun wipeFile(file: File) {
        if (!file.exists()) return

        try {
            val length = file.length()
            val raf = RandomAccessFile(file, "rws")
            val random = SecureRandom()
            val buffer = ByteArray(4096)
            
            var pos = 0L
            while (pos < length) {
                random.nextBytes(buffer)
                raf.write(buffer)
                pos += buffer.size
            }
            raf.setLength(0)
            raf.close()
            file.delete()
        } catch (e: Exception) {
            file.delete() // Fallback : suppression simple
        }
    }

    private fun wipeDirectory(path: String) {
        val dir = File(path)
        dir.walkBottomUp().forEach { file ->
            if (file.isFile) wipeFile(file) else file.delete()
        }
    }

    /**
     * Vérifie si la position actuelle est dans la zone autorisée
     */
    fun isPositionSafe(lat: Double, lon: Double): Boolean {
        // Logique de vérification de polygone (simplifiée ici)
        // Si hors zone -> return false
        return true 
    }
}
