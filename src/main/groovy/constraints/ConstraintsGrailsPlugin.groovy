package constraints

import grails.plugins.*
import grails.gorm.validation.ConstrainedProperty
import net.zorched.grails.plugins.validation.ConstraintArtefactHandler
import net.zorched.grails.plugins.validation.CustomConstraintFactory
import net.zorched.grails.plugins.validation.GrailsConstraintClass
import org.grails.datastore.mapping.validation.ValidatorRegistry
import org.springframework.beans.factory.config.MethodInvokingFactoryBean

class ConstraintsGrailsPlugin extends Plugin {
    // the plugin version
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.3.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Custom domain constraints plugin" // Headline display name of the plugin
    def author = "Ellucian"
    def authorEmail = ""
    def description =  '''
    This plugin allows you to create custom domain validations that are applied the same
    way as built-in domain constraints.
    '''
    def profiles = ['web']
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/constraints"
    ValidatorRegistry gormValidatorRegistry
    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() { {->
            // TODO Implement runtime spring config (optional)
        /*grailsApplication.constraintClasses.each {constraintClass ->
            configureConstraintBeans.delegate = delegate
            configureConstraintBeans(constraintClass)
            }*/
        }
    }

    void doWithDynamicMethods() {
        /*grailsApplication.constraintClasses.each {constraintClass ->
            setupConstraintProperties(constraintClass)

            registerConstraint.delegate = delegate
            registerConstraint(constraintClass, false)
        }*/
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
         // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.

        // XXX: Not sure if this works?
		if (grailsApplication.isArtefactOfType(ConstraintArtefactHandler.TYPE, event.source)) {
            setupConstraintProperties(event.source)
            grailsApplication.addArtefact(ConstraintArtefactHandler.TYPE, event.source)
        }
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }

    /**
     * Register the beans with Spring so that we can inject them later.
     * Not sure if this is really needed.
     */
    def configureConstraintBeans = { GrailsConstraintClass constraintClass ->
        // XXX: Not convinced this does anything
        def fullName = constraintClass.fullName
        "${fullName}Class"(MethodInvokingFactoryBean) {
            targetObject = ref("grailsApplication", true)
            targetMethod = "getArtefact"
            arguments = [ConstraintArtefactHandler.TYPE, constraintClass.fullName]
        }

        "${fullName}"(ref("${fullName}Class")) {bean ->
            bean.factoryMethod = "newInstance"
            bean.autowire = true
        }
    }
	
	/**
     * Setup properties on the custom Constraints to make extra information available to them
     */
    def setupConstraintProperties = { constraintClass ->
        Object params = null
        Object hibernateTemplate = null
        Object constraintOwningClass = null
        String constraintPropertyName = null
        constraintClass.clazz.metaClass {
            setParams = {val -> params = val}
            getParams = {-> return params}
            setHibernateTemplate = {val -> hibernateTemplate = val}
            getHibernateTemplate = {-> return hibernateTemplate}
            setConstraintOwningClass = {val -> constraintOwningClass = val}
            getConstraintOwningClass = {-> return constraintOwningClass}
            setConstraintPropertyName = {val -> constraintPropertyName = val}
            getConstraintPropertyName = {-> return constraintPropertyName}
        }
    }

    /**
     * Register the Custom constraint with ConstrainedProperty. Manages creating them with a CustomConstraintFactory
     */
    def registerConstraint = { constraintClass, usingHibernate ->
        def constraintName = constraintClass.name
        log.debug "Loading constraint: ${constraintClass.name}"
        gormValidatorRegistry.addConstraint(constraintClass) //Changed from ContraintFactory
    }
}
