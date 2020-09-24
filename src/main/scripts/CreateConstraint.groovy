/* ******************************************************************************
 Copyright 2009-2020 Ellucian Company L.P. and its affiliates.
 ****************************************************************************** */

/**
 * Gant script that creates a Grails Validation
 *
 * @author Geoff Lane
 * @since 0.1
 */

includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCreateArtifacts")

target ('default': "Creates a new constraint") {
    depends(checkVersion, parseArguments)

    def type = "Constraint"
    promptForName(type: type)

    def name = argsMap["params"][0]
    createArtifact(name: name, suffix: type, type: type, path: "grails-app/utils")
    createUnitTest(name: name, suffix: type, superClass: "GroovyTestCase")
}

