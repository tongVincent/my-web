import com.twx.core.util.json.JSONBinder
import com.twx.core.util.json.JsonUtil

/**
 * Created by vincent.tong on 2016/11/23.
 */

// 1、
println "1、"
data = new File('car.dat').readLines()

props = data[0].split(", ")
data -= data[0]

def averageMilesDrivenPerYear = { miles.toLong() / (2008 - year.toLong()) }

cars = data.collect {
    car = new Expando()
    it.split(", ").eachWithIndex { value, index ->
        car[props[index]] = value
    }

    car.ampy = averageMilesDrivenPerYear

    car
}

props.each { name -> print "$name " }
println " Avg. MPY"

ampyMethod = 'ampy'
cars.each { car ->
    for (String property : props) { print "${car[property]} " }
    println car."$ampyMethod"()
}

// 你也可能想通过名字访问属性或方法
car = cars[0]
println "$car.miles $car.year $car.make ${car.ampy()}"



// 2、
println "2、"
class Worker {
    def simpleWork1(spec) { println "worker does work1 with spec $spec" }
    def simpleWork2() { println "worker does work2" }
}

class Expert {
    def advancedWork1(spec) { println "Expert does work1 with spec $spec" }
    def advancedWork2(scope, spec) { println "Expert does work2 with scope $scope spec $spec" }
}

class Manager {
    { delegateCallsTo Worker, Expert, GregorianCalendar }

    def schedule() { println "Scheduling ..." }
}

Object.metaClass.delegateCallsTo = { Class... klassOfDelegates ->
    def objectOfDelegates = klassOfDelegates.collect { it.newInstance() }
    delegate.metaClass.methodMissing = { String name, args ->
        println "intercepting call to $name..."
        def delegateTo = objectOfDelegates.find {
            it.metaClass.respondsTo(it, name, args)
        }
        if (delegateTo) {
            delegate.metaClass."${name}" = { Object[] varArgs ->
                delegateTo.invokeMethod(name, varArgs)
            }
            delegateTo.invokeMethod(name, args)
        } else {
            throw new MissingMethodException(name, delegate.getClass(), args)
        }
    }
}

peter = new Manager()
peter.schedule()
peter.simpleWork1('fast')
peter.simpleWork1('quality')
peter.simpleWork2()
peter.simpleWork2()
peter.advancedWork1('fast')
peter.advancedWork1('quality')
peter.advancedWork2('prototype', 'fast')
peter.advancedWork2('product', 'quality')
println "Is 2008 a leap year? " + peter.isLeapYear(2008)
try {
    peter.simpleWork3()
} catch (ex) {
    println ex
}


// 3、测试if的条件判断，跟js类似
println "3、"
def d = null
if(d) {
    println "is not Null"
} else {
    println "is Null"
}
d = ""
if(d) {
    println "is not Null"
} else {
    println "is Null"
}
d = []
if(d) {
    println "is not Null"
} else {
    println "is Null"
}
d.add(1)
if(d) {
    println "is not Null"
} else {
    println "is Null"
}

// 4、
println "4、"
println JSONBinder.binder(JsonUtil.class).toJson(null)


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