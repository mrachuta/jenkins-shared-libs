#!/usr/bin/env groovy

def call(Map args) {
    if (!args.configFileProviderFileId) {
        error('Parameter configFileProviderFileId is required!')
    }
    echo  "Exposing variables from file ${args.configFileProviderFileId}..."
    configFileProvider([
        configFile(
            fileId: args.configFileProviderFileId,
            variable: 'CONFIG_FILE'
        )
    ]) {
        // Split by each line, then split by '=' and remove quotes
        def configFile = readFile("${env.CONFIG_FILE}").split('\n')
        configFile.each { configLine ->
            configLineProcessed = configLine.trim().split('=')
            def envKey = configLineProcessed.first().replaceAll("\"|\'|\'\$|\"\$", "")
            def envValue = configLineProcessed.last().replaceAll("\"|\'|\'\$|\"\$", "")
            env."${envKey}" = envValue
        }
    }
}
