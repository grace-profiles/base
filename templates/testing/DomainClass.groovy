@artifact.package@
import spock.lang.Specification

import grails.testing.gorm.DomainUnitTest

class @artifact.name@Spec extends Specification implements DomainUnitTest<@artifact.name@> {

    def setup() {
    }

    def cleanup() {
    }

    void 'test something'() {
        expect: 'fix me'
        true == false
    }

}
