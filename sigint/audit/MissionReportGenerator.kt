package sigint.audit

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

/**
 * SRC - MissionReportGenerator
 * Génère le bilan final de l'opération avec signature d'intégrité.
 */
object MissionReportGenerator {

    private val mapper = jacksonObjectMapper()
    private val sdf = SimpleDateFormat("yyyyMMdd_HHmm")

    fun generateFinalReport(missionId: String) {
        println("[REPORT] Compilation des données de mission : $missionId...")

        val reportData = mutableMapOf<String, Any>(
            "mission_id" to missionId,
            "timestamp" to System.currentTimeMillis(),
            "summary" to mapOf(
                "comint_hits" to countOccurrences("COMINT_MATCH"),
                "elint_hits" to countOccurrences("ELINT_MATCH"),
                "fisint_hits" to countOccurrences("FISINT_MATCH")
            ),
            "critical_alerts" to extractCriticalAlerts()
        )

        val reportName = "REPORT_${missionId}_${sdf.format(Date())}.json"
        val reportFile = File("data/audit/$reportName")

        // Utilisation de ton AuditExport pour l'écriture et la signature HMAC
        // On récupère une clé de signature (simulée ici)
        val secretKey = "SECRET_MISSION_KEY".toByteArray()
        
        val success = AuditExport.writeJson(reportFile.absolutePath, reportData, secretKey)

        if (success) {
            println("✅ Rapport généré et signé : ${reportFile.name}")
        } else {
            println("❌ Échec de la génération du rapport.")
        }
    }

    private fun countOccurrences(pattern: String): Int {
        val logFile = File("data/logs/sigint_module.log")
        if (!logFile.exists()) return 0
        return logFile.readLines().count { it.contains(pattern) }
    }

    private fun extractCriticalAlerts(): List<String> {
        val logFile = File("data/logs/sigint_module.log")
        if (!logFile.exists()) return emptyList()
        return logFile.readLines().filter { it.contains("[WARN]") || it.contains("[ERROR]") }.takeLast(10)
    }
}
