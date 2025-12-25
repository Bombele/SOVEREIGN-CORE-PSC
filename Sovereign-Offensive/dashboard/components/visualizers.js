// Composant pour l'indicateur de succès
const SuccessGauge = (rate) => {
    return `[PROGRESS BAR] Success: ${rate}% - Operation Effective`;
}

// Composant pour le risque de détection
const DetectionAlert = (riskLevel) => {
    if (riskLevel > 50) return "WARNING: ENEMY ANALYSIS DETECTED";
    return "STATUS: STEALTH";
}
