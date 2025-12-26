package com.psc.sovereign.core

import org.jpos.iso.ISOMsg
import org.jpos.iso.packager.GenericPackager

class NationalSwitchController(private val gatekeeper: Gatekeeper) {
    
    // Utilisation du packager jPOS standard pour ISO 8583
    private val packager = GenericPackager("src/main/resources/iso87ascii.xml")
    private val blacklist = mutableSetOf<String>()

    fun processSwitchTraffic(rawData: ByteArray, authSig: ByteArray): ByteArray {
        // Vérification de l'autorisation d'action
        if (!gatekeeper.verifyCommand(authSig)) return rawData

        val m = ISOMsg()
        m.packager = packager
        m.unpack(rawData)

        // DE 102 : Compte de destination (norme ISO)
        val destination = m.getString(102)

        if (blacklist.contains(destination)) {
            println("[SWITCH-PSC] INTERCEPTION : Redirection du flux $destination")
            
            // Modification du message : Code de réponse 57 (Action de l'État)
            m.set(39, "57") 
            // Optionnel : Redirection forcée en changeant le champ 102
            // m.set(102, "COMPTE_SEQUESTRE_PSC_001")
            
            return m.pack()
        }

        return rawData
    }

    fun updateTarget(rib: String) {
        blacklist.add(rib)
    }
}
