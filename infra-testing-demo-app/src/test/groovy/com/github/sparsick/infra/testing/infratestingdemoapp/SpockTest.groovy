import spock.lang.Specification

class SpockTest extends Specification{

    def "test" (){
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

}