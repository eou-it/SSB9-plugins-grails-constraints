/*********************************************************************************
 Copyright 2012 Ellucian Company L.P. and its affiliates.
 **********************************************************************************/
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        if (System.properties['PROXY_SERVER_NAME']) {
            mavenRepo "${System.properties['PROXY_SERVER_NAME']}"
        }

        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()

        mavenRepo "http://repo.grails.org/grails/repo" // supporting old plugin releases

        mavenRepo "http://repository.jboss.org/maven2/"
        mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"

    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
    }

    plugins {
        compile ":hibernate:3.6.10.10"
        compile ":tomcat:7.0.52.1"
        test ':code-coverage:2.0.3-2',
                {
                    excludes 'xercesImpl'
                }
    }
}
