description('Runs the applications tests') {
    usage 'grace test-app [TEST NAME]'
    completer org.grails.cli.command.completers.TestsCompleter
    synonyms 'test'
    argument name: 'Test Name', description: 'The name of the test to run (optional)', required: false
    flag name: 'debug-jvm', description: 'Starts the JVM in debug mode allowing attachment of a remote debugger'
    flag name: 'unit', description: 'Run unit tests (test target)'
    flag name: 'integration', description: 'Run integration tests (integrationTest target)'
    flag name: 'clean', description: 'Re-run all tests (cleanTest cleanIntegrationTest target)'
}

// configure environment to test is not specified
if (!commandLine.isEnvironmentSet()) {
    System.setProperty('grails.env', 'test')
}
else {
    System.setProperty('grails.env', commandLine.environment)
}

// add test.single argument if specified
def testsFilter = commandLine.remainingArgs.collect { "--tests $it".toString() }.join(' ')?.trim()
boolean debugJvm = flag('debug-jvm')

def handleTestPhase = { targetName ->
    def args = []
    if (flag('clean')) {
        args << "clean${targetName.capitalize()}"
    }
    args << targetName
    if (testsFilter) {
        args << testsFilter
    }
    // add debug flag if present
    if (debugJvm) {
        args << '--debug-jvm'
    }
    args
}

def gradleArgs = []

boolean executeUnitTests = flag('unit') || !flag('integration')
if (executeUnitTests) {
    gradleArgs.addAll handleTestPhase('test')
}

boolean hasIntegrationTests = file('src/integration-test').isDirectory()
boolean executeIntegrationTests = hasIntegrationTests && (flag('integration') || !flag('unit'))
if (executeIntegrationTests) {
    gradleArgs.addAll handleTestPhase('integrationTest')
}

runTests = { List args ->
    def additionalArguments = []
    commandLine.systemProperties.each { key, value ->
        additionalArguments << "-D${key}=$value".toString()
    }

    additionalArguments << '-Dgrails.run.active=true'

    try {
        gradle."${args.join(' ')}"(*additionalArguments)
        addStatus 'Tests PASSED'
        return true
    }
    catch (ignore) {
        console.error 'Tests FAILED', 'Test execution failed'
        return false
    }
}

runTests(gradleArgs)
