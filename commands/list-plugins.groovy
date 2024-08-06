description("Lists available plugins from the Plugin Repository") {
    usage "grace list-plugins"
}

try {
    console.addStatus "Available Plugins"
    def text = new URL('https://repo1.maven.org/maven2/org/graceframework/plugins/').text
    text.eachMatch(/<a href="([a-z-]+)\/" title="([a-z-]+)\/">.+/) {
        console.log "* ${it[1]}"
    }
}
catch(Throwable e) {
    console.error "Failed to list plugins", e
    return false
}
