package com.twx.test.groovy

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