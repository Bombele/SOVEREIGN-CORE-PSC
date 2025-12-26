package infra.switch

import sigint.audit.LogManager
import sigint.core.Gatekeeper
import java.security.Signature

/**
 * SRC - NationalSwitchController
 * Contrôleur de la Chambre de Compensation Nationale.
 * Gère le filtrage massif des transactions ISO 8583.
 */
class NationalSwitchController(private val gatekeeper: Gatekeeper) {

    // Liste noire des comptes (RIB/Mobile Money) synchronisée avec l'État-Major
    private val blacklistedAccounts = mutableSetOf<String>()

    /**
     * Point d'entrée pour chaque message financier passant par le switch.
     */
    fun onTransactionRequest(isoMessage: ISOMessage, authSignature: ByteArray): ISOMessage {
        
        // 1. Vérification de l'autorisation d'accès au Switch (Signature EM)
        if (!gatekeeper.verifyCommand(authSignature)) {
            LogManager.warn("SWITCH_AUTH_FAIL: Tentative de contrôle sans signature valide.")
            return isoMessage // On laisse passer par défaut pour ne pas bloquer l'économie
        }

        // 2. Extraction du compte destinataire (Champ 102 dans la norme ISO 8583)
        val targetAccount = isoMessage.getField(102)

        // 3. Filtrage offensif
        if (blacklistedAccounts.contains(targetAccount)) {
            LogManager.info("SWITCH_BLOCK: Transaction vers $targetAccount interceptée.")
            
            // On modifie le code de réponse (Champ 39) : "57 - Transaction non permise"
            return isoMessage.apply {
                setField(39, "57")
                setField(44, "STATE_SEIZURE_ORDER") // Information de saisie
            }
        }

        return isoMessage // Transaction autorisée
    }

    fun updateBlacklist(newTargets: List<String>) {
        this.blacklistedAccounts.addAll(newTargets)
        LogManager.info("SWITCH_SYNC: Liste noire mise à jour (${newTargets.size} cibles).")
    }
}

/**
 * Structure réelle d'un message ISO 8583 (Simplifiée pour l'implémentation)
 */
data class ISOMessage(
    val fields: MutableMap<Int, String> = mutableMapOf()
) {
    fun getField(id: Int): String = fields[id] ?: ""
    fun setField(id: Int, value: String) { fields[id] = value }
}
