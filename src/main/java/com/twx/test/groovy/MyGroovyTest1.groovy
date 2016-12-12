/**
 * Created by vincent.tong on 2016/11/21.
 */
// 1、测试ExpandoMetaClass注入方法
println "1、测试ExpandoMetaClass注入方法"
Number.metaClass.someMethod = { ->
    println "someMethod called"
}

Number.metaClass.someMethod = { String s ->
    println "someMethod called " + s
}

Number.metaClass.toString = { ->
    println "someMethod called new"
}

Number.metaClass.someMethod << { ->
    println "someMethod called new"
}

2.someMethod()
2L.someMethod("str")


Number.metaClass.getSomeProperty = { ->
    println "someProperty called"
}

Number.metaClass.getSomeProperty = { String s ->
    println "someProperty called " + s
}

Number.metaClass.getSomeProperty << { ->
    println "someProperty called new"
}

Number.metaClass.getSomeProperty << { ->
    println "someProperty called new"
}

2.someProperty
2L.getSomeProperty("str")

Integer.metaClass.constructor = { String str ->
    println str
    new Integer(0)
}
println new Integer("sfsfl")

Integer.metaClass.daysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, delegate)
    today.time
}
println 5.daysFromNow()

Integer.metaClass.getDaysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, delegate)
    today.time
}
println 5.daysFromNow

daysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, (int)delegate)
    today.time
}
Integer.metaClass.daysFromNow = daysFromNow
Long.metaClass.daysFromNow = daysFromNow
println 5.daysFromNow()
println 5L.daysFromNow()


Integer.metaClass.'static'.isEven = { val -> val % 2 == 0 }

println "Is 2 even?" + Integer.isEven(2)
println "Is 3 even?" + Integer.isEven(3)

Integer.metaClass.constructor << { Calendar calendar ->
    new Integer(calendar.get(Calendar.DAY_OF_YEAR))
}
println new Integer(Calendar.instance)

Integer.metaClass.constructor = { int val ->
    println "Intercepting constructor call"
    constructor = Integer.class.getConstructor(Integer.TYPE)
    constructor.newInstance(val)
}
println new Integer(4)
println new Integer(Calendar.instance)

Integer i = 6;
println i.metaClass
println i.metaClass.delegate
i.metaClass.here = { ->
    "here"
}
println i.metaClass
println i.metaClass.delegate
println i.daysFromNow
println i.here()
i.metaClass = null
println i.metaClass
println i.metaClass.delegate
try {
    println i.daysFromNow
    println i.here()
} catch (ex) {
    println ex
}

// 2、测试Mixin注入方法
println "2、测试Mixin注入方法"
class Friend {
    def listen() {
        "$name is listening as a friend"
    }
}

@Mixin(Friend)
class Person1 {
    String firstName
    String lastName
    String getName() { "$firstName $lastName"}
}

john = new Person1(firstName: "John", lastName: "Smith")
println john.listen()

Person1.metaClass.test = { ->
    for (mixin in mixedIn.mixinClasses) {
        println mixin.mixinClass.theClass
    }
}

john = new Person1()
john.test()

class Dog {
    String name
}

Dog.mixin Friend

buddy = new Dog(name: "Buddy")
println buddy.listen()

class Cat {
    String name
}

try {
    rude = new Cat(name: "Rude")
    rude.listen()
} catch(ex) {
    println ex.message
}

socks = new Cat(name: "Socks")
socks.metaClass.mixin Friend
println socks.listen()




// 3、测试trait关键字代替Mixin注解，如果Person2中从新实现了listen方法，那么以Person2的实现为准
println "3、测试trait关键字代替Mixin注解，如果Person2中从新实现了listen方法，那么以Person2的实现为准"
trait Friend1 {
    def listen() {
        "$name is listening as a friend 1"
    }
}

trait Friend2 {
    def listen() {
        "$name is listening as a friend 2"
    }
}

class Person2 implements Friend1,Friend2 {
    String firstName
    String lastName
    String getName() { "$firstName $lastName"}
}

john = new Person2(firstName: "John", lastName: "Smith")
println john.listen()


// 4、测试在类中使用多个Mixin
println "4、测试在类中使用多个Mixin"
abstract class Writer {
    abstract void write(String message)
}

class StringWriter extends Writer {
    def target = new StringBuilder()

    void write(String message) {
        target.append(message)
    }

    String toString() { target.toString() }
}

def writeStuff(writer) {
    writer.write("This is stupid")
    println writer
}

def create(theWriter, Object[] filters = []) {
    def instance = theWriter.newInstance()
    filters.each { filter -> instance.metaClass.mixin filter }
    instance
}
writeStuff(create(StringWriter))

class UpperCaseFilter {
    void write(String message) {
        def allUpper = message.toUpperCase()
        println this
        invokeOnPreviousMixin(metaClass, "write", allUpper)
    }
}

Object.metaClass.invokeOnPreviousMixin = {
    MetaClass currentMixinMetaClass, String method, Object[] args ->
        def previousMixin = delegate.getClass()
        mixedIn.isCase()
        for (mixin in mixedIn.mixinClasses) {
            if (mixin.mixinClass.theClass == currentMixinMetaClass.delegate.theClass) {
                break
            }
            previousMixin = mixin.mixinClass.theClass
        }
        mixedIn[previousMixin]."$method"(*args)
}

writeStuff(create(StringWriter, UpperCaseFilter))

class ProfanityFilter {
    void write(String message) {
        def filtered = message.replaceAll('stupid', 's*****')
        println this
        invokeOnPreviousMixin(metaClass, "write", filtered)
    }
}

writeStuff(create(StringWriter, ProfanityFilter))

writeStuff(create(StringWriter, UpperCaseFilter, ProfanityFilter))
writeStuff(create(StringWriter, ProfanityFilter, UpperCaseFilter))


// 5、
println "5、"



// 6、
println "6、"



// 7、
println "7、"



// 8、
println "8、"



// 9、
println "9、"



// 0、
println "0、"



// 1、
println "1、"



// 2、
println "2、"



// 3、
println "3、"



// 4、
println "4、"



// 5、
println "5、"



// 6、
println "6、"



// 7、
println "7、"



// 8、
println "8、"



// 9、
println "9、"







