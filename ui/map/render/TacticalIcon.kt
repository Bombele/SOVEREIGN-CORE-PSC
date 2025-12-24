package ui.map.render

/**
 * Définit un objet graphique sur la carte (Amis ou Ennemis)
 */
data class TacticalIcon(
    val type: String,
    val lat: Double,
    val lon: Double,
    val label: String,
    val color: Int
) {
    fun draw(canvas: TacticalCanvas) {
        // Logique de conversion coordonnées GPS -> Pixels écran
        val screenPos = canvas.gpsToScreen(lat, lon)
        
        if (type == "FRIENDLY") {
            canvas.drawSquare(screenPos, color, label) // Symbole OTAN simplifié
        } else {
            canvas.drawDiamond(screenPos, color, label) // Symbole Hostile
        }
    }
}
