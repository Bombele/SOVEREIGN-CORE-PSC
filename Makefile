# Configuration FARDC - SOVEREIGN-CORE-PSC
KOTLINC = ./kotlinc/bin/kotlinc
JAR_FILE = build/libs/sigint-core-all.jar
MAIN_CLASS = com.fardc.sigint.core.MainKt

all: setup compile run

setup:
	@echo "🛡️ Configuration de l'environnement..."
	@mkdir -p build/libs
	@if [ ! -f "$(KOTLINC)" ]; then \
		curl -L https://github.com/JetBrains/kotlin/releases/download/v1.9.0/kotlin-compiler-1.9.0.zip -o kotlinc.zip && \
		unzip -qo kotlinc.zip && rm kotlinc.zip; \
	fi

compile:
	@echo "⚙️ Compilation du noyau (Bypass 25.0.1)..."
	@export JAVA_HOME=$(JAVA_HOME_17_X64) && \
	$(KOTLINC) src/main/kotlin/com/fardc/sigint/core/*.kt \
		-jdk-home "$$JAVA_HOME" \
		-include-runtime -d $(JAR_FILE)

run:
	@echo "🚀 Lancement du système SIGINT..."
	@java -jar $(JAR_FILE)

clean:
	rm -rf build/
	rm -rf kotlinc/
