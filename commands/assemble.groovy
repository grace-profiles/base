description("Creates a JAR or WAR archive for production deployment") {
    usage "grace [ENV] assemble"
    synonyms 'war', 'package'
    flag name:'clean', description:"Execute 'clean' prior to creating WAR"
}

// configure environment to production if it is not specified
if(!commandLine.isEnvironmentSet()) {
    System.setProperty('grails.env', 'production')
} else {
    System.setProperty('grails.env', commandLine.environment)
}

grails.util.Environment.reset()

def arguments = []
commandLine.systemProperties.each { key, value ->
    arguments << "-D${key}=$value".toString()
}

gradle."assemble"(*arguments)

buildPath = projectPath("${buildDir}/libs")
addStatus "Built application to $buildPath using environment: ${grails.util.Environment.current.name}"
