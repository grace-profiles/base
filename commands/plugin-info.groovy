import groovy.xml.*

description("Prints information about the given plugin") {
    usage "grace plugin-info [PLUGIN NAME]"
    argument name:"Plugin Name", description:"The name of the plugin"
    flag name:'snapshots', description:"Whether to list snapshot versions"
}

def pluginRepoURL = "https://repo1.maven.org/maven2/org/graceframework/plugins"
def pluginName = args[0]
def includeSnapshots = flag('snapshots')
try {
    console.addStatus "Plugin Info: ${pluginName}"
    def mavenMetadata = new XmlSlurper().parseText(new URL("${pluginRepoURL}/${pluginName}/maven-metadata.xml").text)
    def latestVersion = mavenMetadata.versioning.release.text()
    if(!latestVersion) {
        latestVersion = mavenMetadata.versioning.latest.text()
    }
    console.addStatus "Latest Version: ${latestVersion}"
    allVersions = mavenMetadata.versioning.versions.version*.text()
    if(!includeSnapshots) {
        allVersions = allVersions.findAll {
            !it?.endsWith('-SNAPSHOT')
        }
    }
    console.addStatus "All Versions: ${allVersions.join(',')}"

    def pluginInfo
    if(latestVersion.endsWith('-SNAPSHOT')) {
        def versionMetadata = new XmlSlurper().parseText(new URL("${pluginRepoURL}/${pluginName}/${latestVersion}/maven-metadata.xml").text)
        def snapshotVersion = versionMetadata.version.text()
        pluginInfo = new XmlSlurper().parseText(new URL("${pluginRepoURL}/${pluginName}/${latestVersion}/${pluginName}-${snapshotVersion}-plugin.xml").text)
    }
    else {
        pluginInfo = new XmlSlurper().parseText(new URL("${pluginRepoURL}/${pluginName}/${latestVersion}/${pluginName}-${latestVersion}-plugin.xml").text)
    }

    if(pluginInfo) {
        console.addStatus "Title: ${pluginInfo.title.text()}"
        def desc = pluginInfo.description.text()
        if(desc) {
            console.log('')
            console.log(desc)
            console.log('')
        }

        console.log "* License: ${pluginInfo.license.text()}"

        if(pluginInfo.documentation) {
            console.log "* Documentation: ${pluginInfo.documentation.text()}"
        }
        if(pluginInfo.issueManagement) {
            console.log "* Issue Tracker: ${pluginInfo.issueManagement.@url.text()}"
        }
        if(pluginInfo.scm) {
            console.log "* Source: ${pluginInfo.scm.@url.text()}"
        }

        console.log """* Definition:

dependencies {
    implementation "org.graceframework.plugins:${pluginName}:${latestVersion}"    
}

"""
    }
}
catch(Throwable e) {
    console.error "Failed to display plugin info: ${e.message}", e
    return false
}
