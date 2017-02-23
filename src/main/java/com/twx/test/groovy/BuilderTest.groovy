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