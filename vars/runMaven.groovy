#!/usr/bin/env groovy

def call(Map args) {
    if (!args.mavenConfigFileId || !args.cmd) {
        error('Parameters mavenConfigFileId and cmd are required!')
    }
    configFileProvider([
        configFile(
            fileId: "${args.mavenConfigFileId}",
            variable: 'MAVEN_SETTINGS_XML'
        )
    ])
    {
        sh("${args.cmd} -s $MAVEN_SETTINGS_XML")
    }
}
