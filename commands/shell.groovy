description("Runs the Grace interactive shell") {
    usage "grace shell"
    synonyms 'sh'
}

console.error "The Grace shell must be run from Gradle using 'gradle shell -q'"
return false
