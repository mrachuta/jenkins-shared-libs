#!/usr/bin/env groovy

def checkQualityGateResult() {
    timeout(time: 30, unit: 'MINUTES') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            unstable("Marking build as unstable due to quality gate failure: ${qg.status})")
            sonarQgStatus = 'NOK'
        } else {
            sonarQgStatus = qg.status
        }
        return sonarQgStatus
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
}
