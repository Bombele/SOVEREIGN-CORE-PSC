plugins {
    id 'org.jetbrains.kotlin.jvm' version '1.9.0'
    id 'com.github.johnrengelman.shadow' version '8.1.1' 
    id 'application'
}

// Nouvelle identité du projet PSC
group = 'com.psc.sovereign'
version = '1.0.0'

repositories {
    mavenCentral()
    maven { url 'https://jpos.org/maven' }
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib"
    
    // Moteur financier ISO 8583
    implementation 'org.jpos:jpos:2.1.8'
    
    // Communication et Sécurité
    implementation 'io.netty:netty-all:4.1.94.Final'
    implementation 'com.google.code.gson:gson:2.10.1'
    
    testImplementation 'org.jetbrains.kotlin:kotlin-test'
}

application {
    // Assurez-vous que le package dans vos fichiers .kt correspond
    mainClass = 'com.psc.sovereign.core.MainKt'
}

shadowJar {
    // Nom du binaire final adapté au nouveau projet
    archiveBaseName.set('sovereign-core-psc')
    archiveClassifier.set('')
    archiveVersion.set('')
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
