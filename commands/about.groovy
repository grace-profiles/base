import groovy.transform.CompileStatic
import org.springframework.boot.SpringBootVersion

import grails.util.BuildSettings
import grails.util.Environment
import grails.util.GrailsVersion
import org.grails.config.CodeGenConfig

description("About your application's environment") {
    usage "grace about"
}

try {
    console.addStatus("About your application's environment")
    println()
    println('Name:               ' + appName)
    println('Version:            ' + appVersion)
    println('Application root:   ' + appRoot)
    println('Environment:        ' + appEnvironment)
    println()
    println('Grails:             ' + grailsVersion)
    println('Groovy:             ' + groovyVersion)
    println('Gradle:             ' + gradleVersion)
    println('Spring Boot:        ' + springBootVersion)
    println('JVM:                ' + javaVersion)
    println('OS:                 ' + osVersion)
    println()
    return true
}
catch (Exception ignored) {
    return false
}

String getAppName() {
    getApplicationConfig().getProperty(('info.app.name'))
}

String getAppVersion() {
    getApplicationConfig().getProperty(('info.app.version'))
}

String getAppRoot() {
    BuildSettings.BASE_DIR.canonicalPath
}

String getAppEnvironment() {
    Environment.current.name
}

String getGrailsVersion() {
    GrailsVersion.current().version
}

String getGroovyVersion() {
    GroovySystem.version
}

String getGradleVersion() {
    String gradleVersion = null
    File gradleWrapperFile = new File(BuildSettings.BASE_DIR, 'gradle/wrapper/gradle-wrapper.properties')
    if (gradleWrapperFile.exists()) {
        Properties fileProps = new Properties()
        gradleWrapperFile.withInputStream { InputStream input ->
            fileProps.load(input)
            String distributionUrl = fileProps.get('distributionUrl')
            gradleVersion = distributionUrl[49..-9]
        }
    }
    gradleVersion
}

String getSpringBootVersion() {
    SpringBootVersion.version
}

String getJavaVersion() {
    String.format('%s (%s %s)', System.getProperty('java.version'),
            System.getProperty('java.vm.vendor'), System.getProperty('java.vm.version'))
}

String getOsVersion() {
    String.format('%s %s %s', System.getProperty('os.name'), System.getProperty('os.version'), System.getProperty('os.arch'))
}

CodeGenConfig getApplicationConfig() {
    CodeGenConfig config = new CodeGenConfig()
    File applicationYml = new File(BuildSettings.RESOURCES_DIR, "application.yml")
    if (applicationYml.exists()) {
        config.loadYml(applicationYml)
    }
    config
}
