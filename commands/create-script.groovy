import grails.util.*

description( "Creates a Groovy Script" ) {
    usage "grace create-script [Script Name]"
    argument name:'Script Name', description:"The name of the Script to create", required:true
    flag name:'force', description:"Whether to overwrite existing files"
}

def scriptName = GrailsNameUtils.getClassNameForLowerCaseHyphenSeparatedName(args[0])
def model = model(scriptName)
boolean overwrite = flag('force')

render template: template('artifacts/Script.groovy'),
       destination: file("src/main/scripts/${model.lowerCaseName}.groovy"),
       model: model,
       overwrite: overwrite
