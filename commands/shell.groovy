description("Runs the Grace interactive shell") {
    usage "grace shell"
    synonyms 'sh'
}

System.setProperty('org.gradle.console', 'plain')

def arguments = []

if( !(flag('verbose') || console.verbose)) {
    arguments << '-q' << '--console=plain'
}

gradle."shell"(*arguments)

return true
