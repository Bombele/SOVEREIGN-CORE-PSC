plugins {
    kotlin("jvm") version "2.3.0" // Version compatible Gradle 9
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.bombele"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // Ajoute tes dépendances spécifiques ici
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17" // Obligatoire pour Gradle 9
}

tasks.shadowJar {
    archiveBaseName.set("sovereign-core")
    archiveClassifier.set("all")
    
    // Correction pour Gradle 9 : Forcer la reproductibilité
    isZip64 = true
}
