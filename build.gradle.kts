plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Standard Kotlin
    implementation(kotlin("stdlib"))

    // Réseau et communication (Socket, JSON)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    
    // Sécurité (si tu souhaites étendre la validation PKI plus tard)
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    
    // Définit la classe principale pour que "java -jar" sache quoi lancer
    manifest {
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"
    }
}
