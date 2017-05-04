package com.twx.test.groovy

import groovy.json.JsonSlurper

/**
 * Created by vincent.tong on 2017/2/17.
 */
bldr = new groovy.xml.MarkupBuilder()
bldr.languages {
    language(name: 'C++') { author('Stroustrup') }
    language(name: 'Java') { author('Gosling') }
    language(name: 'Lisp') { author('McCarthy') }
}

println()


langs = ['C++' : 'Stroustrup', 'Java' : 'Gosling', 'Lisp' : 'McCarthy']

writer = new StringWriter()
bldr = new groovy.xml.MarkupBuilder(writer)
bldr.languages {
    langs.each { key, value ->
        language(name: key) {
            author(value)
        }
    }
}

println writer

langs = ['C++' : 'Stroustrup', 'Java' : 'Gosling', 'Lisp' : 'McCarthy']

xmlDocument = new groovy.xml.StreamingMarkupBuilder().bind {
    mkp.xmlDeclaration()
    mkp.declareNamespace(computer: "Computer")
    languages {
        comment << "Created using StreamingMarkupBuilder"
        langs.each { key, value ->
            computer.language(name: key) {
                author(value)
            }
        }
    }
}

println xmlDocument

class Person {
    String first
    String last
    def sigs
    def tools
}

john = new Person(first: "John", last: "Smith", sigs: ['Java', 'Groovy'], tools: ['script': 'Groovy', 'test': 'Spock'])
bldr = new groovy.json.JsonBuilder(john)
writer = new StringWriter()
bldr.writeTo(writer)

println writer


bldr = new groovy.json.JsonBuilder()
bldr {
    firstName john.first
    lastName john.last
    "special interest groups" john.sigs
    "preferred tools" {
        numberOfTools john.tools.size()
        tools john.tools
    }
}
writer = new StringWriter()
bldr.writeTo(writer)

println writer

def slurper = new JsonSlurper()
def person = slurper.parse(new FileReader('person.json'))
println "$person.first $person.last is interested in ${person.sigs.join(', ')}"



bldr = new groovy.swing.SwingBuilder()

frame = bldr.frame(
        title: 'Swing',
        size: [50, 100],
        layout: new java.awt.FlowLayout(),
        defaultCloseOperation: javax.swing.WindowConstants.EXIT_ON_CLOSE
) {
    lbl = label(text: 'test')
    btn = button(text: 'Click me', actionPerformed: {
        btn.text = 'Clicked'
        lbl.text = 'Groovy!'
    })
}

frame.visible = true;


bldr = new TodoBuilder()

bldr.build {
    Prepare_Vacation(start: '02/15', end: ' 02 / 22 ') {
        Reserve_Flight(on: '01/01', status: 'done')
        Reserve_Hotel(on: '01/02')
        Reserve_Car(on: '01/02')
    }
    Buy_New_Mac {
        Install_QuickSilver
        Install_TextMate
        Install_Groovy {
            Run_all_tests
        }
    }
}


class TodoBuilder {
    def level = 0
    def result = new StringWriter()

    def build(closure) {
        result << "To-Do:\n"
        closure.delegate = this
        closure()
        println result
    }

    def methodMissing(String name, args) {
        handle(name, args)
    }

    def propertyMissing(String name) {
        Object[] emptyArray = []
        handle(name, emptyArray)
    }

    def handle(String name, args) {
        level++
        level.times { result << " " }
        result << placeXifStatusDOne(args)
        result << name.replaceAll("_", " ")
        result << printParameters(args)
        result << "\n"
        if (args.length > 0 && args[-1] instanceof Closure) {
            def theClosure = args[-1]
            theClosure.delegate = this
            theClosure()
        }
        level--
    }

    def placeXifStatusDOne(args) {
        args.length > 0 && args[0] instanceof Map &&
                args[0]['status'] == 'done' ? "x" : "-"
    }

    def printParameters(args) {
        def values = ""
        if (args.length > 0 && args[0] instanceof Map) {
            values += " ["
            def count = 0
            args[0].each { key, value ->
                if (key == 'status') return
                count++
                values += (count > 1 ? " " : "")
                values += "${key}: ${value}"
            }
            values += "]"
        }
        values
    }
}