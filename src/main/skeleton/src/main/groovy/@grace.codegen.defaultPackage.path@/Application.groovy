package @grace.codegen.defaultPackage@

import groovy.transform.CompileStatic
import grails.boot.Grails

@CompileStatic
class Application {

    static void main(String[] args) {
        Grails.run(Application, args)
    }

}
