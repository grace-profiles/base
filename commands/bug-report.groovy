import java.text.*

deprecated('Deprecated because this command is not used anymore!')

description( "Creates a zip file that can be attached to issue reports for the current project" ) {
    usage "grace bug-report"
}

String fileName = baseDir.name
String date = new SimpleDateFormat("ddMMyyyy").format(new Date())
String zipName = "${buildDir}/${fileName}-bug-report-${date}.zip"

ant.zip(destfile: zipName, filesonly: true) {
    fileset(dir: baseDir.canonicalPath) {
        include name: 'app/**'
        include name: 'buildSrc/**'
        include name: 'db/**'
        include name: 'gradle/**'
        include name: 'src/**'
        include name: 'build.gradle'
        include name: 'gradlew'
        include name: 'gradlew.bat'
        include name: 'gradle.properties'
        include name: 'settings.gradle'
        include name: '.gitignore'
        include name: '.gitattributes'
        exclude name: '**/.git/**'
        exclude name: '**/.gradle/**'
        exclude name: '**/build/**'
    }
}

console.addStatus "Created bug report ZIP: ${projectPath(zipName)}"
