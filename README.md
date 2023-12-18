## Project name

jenkins-shared-libs - library to be used with Jenkins pipelines.

## Table of contents
- [Project name](#project-name)
- [Table of contents](#table-of-contents)
- [General info](#general-info)
- [exposeVariablesFromConfigFile](#exposevariablesfromconfigfile)
- [runMaven](#runmaven)
- [runSonarQubeAnalysis](#runsonarqubeanalysis)

## General info

Repo contains custom Jenkins functions written in groovy that could reduce code repetition and make pipelines more user-friendly.

## exposeVariablesFromConfigFile
- run over config file from ConfigFileProvider and expose each key and value as environment variable

## runMaven
- run maven with custom config file from ConfigFileProvider

## runSonarQubeAnalysis
- run SonarQube staic code analysis via sonar-scanner or maven
