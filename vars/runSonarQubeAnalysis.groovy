#!/usr/bin/env groovy

def _checkQualityGateResult() {
    timeout(time: 30, unit: 'MINUTES') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            unstable("Marking build as unstable due to quality gate failure: ${qg.status})")
            sonarQGStatus = 'NOK'
        } else {
            sonarQGStatus = qg.status
        }
    }
}

def scanner(Map args) {
    if (!args.sonarScannerToolId || !args.nodeJsToolId || !args.sonarQubeServerId) {
        error('Parameters sonarScannerToolId, nodeJsToolId and sonarQubeServerId are required!')
    }
    def scannerHome = tool("${args.sonarScannerToolId}")
    def nodeHome = tool("${args.nodeJsToolId}")
    withEnv(["PATH+EXTRA=${scannerHome}/bin:${nodeHome}/bin"]) {
        withSonarQubeEnv("${args.sonarQubeServerId}") {
            sh('sonar-scanner')
        }
    }
    _checkQualityGateResult()
}

def maven(Map args) {
    if (!args.sonarQubeServerId || !args.mavenConfigFileId || !args.mavenSonarGoal) {
        error('Parameters sonarScannerToolId, nodeJsToolId and sonarQubeServerId are required!')
    }
    withSonarQubeEnv("${args.sonarQubeServerId}") {
        runMaven(
            mavenConfigFileId: "${args.mavenConfigFileId}",
            cmd: "${args.mavenSonarGoal}"
        )
    }
    _checkQualityGateResult()
}
