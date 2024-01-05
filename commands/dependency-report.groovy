description("Prints out the Grace application's dependencies") {
    usage "grace dependency-report [configuration]"
    argument name: "Configuration", description: "Which source set to report on (compile, test, etc)", required: false
}

def arguments = []
commandLine.systemProperties.each { key, value ->
    arguments << "-D${key}=$value".toString()
}

def command = ["dependencies"]
if (commandLine.remainingArgs.size() > 0) {
    command << "--configuration ${commandLine.remainingArgs[0]}"
}

gradle."${command.join(' ')}"(*arguments)