package com.twx.test.groovy
import groovy.transform.Canonical
import groovy.transform.Immutable
import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory

import java.util.regex.Matcher
/**
 * Created by vincent.tong on 2016/9/5.
 */



// 1、测试循环
println '1、测试循环'
for (i in 0..2) {
    println "la"
}
println "my first"

0.upto(2) { print "$it " }
println()

3.times { print "$it " }
println()

0.step(10, 2) { print "$it " }
println()



// 2、测试Process
println '2、测试Process'
println "java".execute().err.text

println "javac".execute().err.getText("utf-8")

println "java -version".execute().err.text

println "cmd /c groovy -v".execute().text
// 因为没有在Windows上安装groovy程序，但有设置path路径，则groovy相当与一个shell命令。所以应该先调用cmd程序，然后再执行groovy命令



// 3、测试?.运算符
println '3、测试?.运算符'
String str = null;
println str?.reverse(); // ?.操作符，当引用不为null的时候才调用对应的方法和属性。

println URLDecoder.decode("http://192.168.254.188:8095/image-service/um3/tq7/163012671%EF%BC%81200x200.jpg", "utf-8")



// 4、测试定义与调用，是如何区分的
println '4、测试定义与调用，是如何区分的'
x = 1; // 定义与赋值同时，编译器可以很好地区分开来，所以可以不需要任何修饰符
println x

x = "abc"
println x

def a // 此处，不能省略def，因为如果省略了，编译器不能区分开定义还是调用变量。当然可以用其他的类型代替def
println a;

def add(m, n) {
    m + n
}

println add(1, 2)
println add("a", "c")

// 类中定义成员的时候，一定要有一个修饰符来修饰成员，这样编译器才能区分开定义与调用
class TestDef {
    final year // 如果省略final，则编译不通过，因为编译器不能区分是定义还是调用。同理下面的def也不能省略
    def month
    def day

    TestDef() {
        year = 2000
    }

    TestDef(year) {
        this.year = year
    }

    def display() { // 此处也不能省略def
        println year?.toString() + "年" + month + "月" + day + "日"
    }
}

a = new TestDef(1900)
println a.year
println a['year']
println a.'year'
println a.month
println a['month']

a = new TestDef(month: 12, day: 31) // 这种写法的时候，当有只有一个参数的构造函数的时候，那么就优先调用构造函数。
// 而不会先调用无参构造，然后再对应调用setter。
// 所以这里是调用了一个参数的构造函数，里面的定义当做map赋值给了year
println a.display() // groovy也把java里+运算符改了，因为在groovy里可以重载运算符，所以对于map对象 + 字符串，将会报错，因为map没有重载+运算符。
//println a.year + "a"这句在groovy中会报错



// 5、测试具名参数
println '5、测试具名参数'
class Test5 {
    def x, y, z

    Test5() {
    }

    Test5(x, y) {
        this.x = x
        this.y = y
    }

    def display(a, b, c) {
        println "$a,$b,$c"
    }

    @Override
    String toString() {
        "Test5{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}

def test5 = new Test5(x: 30, y: 20, z: 10) // 因为该类没有定义一个参数的构造函数，所以先调用无参构造函数，再调用对应的setter。
println(test5)
println(new Test5(100, 200))
// 对于方法，把具名实参都赋给第一个参数，剩下的实参依次赋给其他的参数
test5.display(x: 30, y: 20, 5, 6)
test5.display(5, x: 30, y: 20, 6)
test5.display(5, 6, x: 30, y: 20)
test5.display(5, x: 30, 6, y: 20)
test5.display(5, x: 30, 6)
test5.display 5, x: 30, 6 // 有没有括号都一样



// 6、测试可选形参
println '6、测试可选形参'
def log(x, base = 10) {
    Math.log(x) / Math.log(base)
}
println log(1024)
println log(1024, 10)
println log(1024, 2)



// 7、测试多赋值
println "7、测试多赋值"
(lastName, firstName) = "vincent tong".split(" ")
println "$lastName,$firstName"

def (String tom, String mouse) = ["abc", "add", "ha"]
println "$tom,$mouse"

def (m, n) = ["abc"]
println "$m,$n"

[].asBoolean()



// 8、测试正则表达式
println "8、测试正则表达式"
p = ~$/dollar/slashy $ string/$
println p.toString()

def text = "some text to match"
m = text =~ /match/
assert m instanceof Matcher
if (!m) {
    throw new RuntimeException("Oops, text not found!")
}
if (text =~ /match/) {
    println '=~ is true';
}
if (text ==~ /some text to match/) {
    println '==~ is true';
}
if (text ==~ /match$/) {
    println '==~ is true';
}



// 9、测试*.运算符
println "9、测试*.运算符"
int function(int x, int y, int z) {
    x*y+z
}
args = [4]
println function(*args,5,6) == 26
println args instanceof List
println args.get(0)

println((0..5) == [0, 1, 2, 3, 4, 5])



// 10、测试变长参数
println "10、测试变长参数"
def receiveVarArgs(int a, int... b) {
    println "You passed $a and $b"
}
receiveVarArgs(1, 2, 3, 4, 5)
int[] values = [2, 3]
receiveVarArgs(1, values)
receiveVarArgs(1, [2, 3] as int[])
// 写成这样：receiveVarArgs(1, [2, 3])是会抛出MissingMethodException


// 11、测试Canonical注解
println "11、测试Canonical注解"
@Canonical class Customer {
    String first, last
    int age
    Date since
    Collection favItems = ['Food']
    def object
    private int num = 5
}
def d = new Date()
def anyObject = new Object()
def c1 = new Customer(first:'Tom', last:'Jones', age:21, since:d, favItems:['Books', 'Games'], object: anyObject)
def c2 = new Customer('Tom', 'Jones', 21, d, ['Books', 'Games'], anyObject)
assert c1 == c2

def c3 = new Customer(last: 'Jones', age: 21)
def c4 = new Customer('Tom', 'Jones')
assert null == c3.since
assert 0 == c4.age
assert c3.favItems == ['Food'] && c4.favItems == ['Food']

println c1
println c2
println c3
println c4
println c4.num


// 12、测试==运算符
println "12、测试==运算符"
class A {
    boolean equals(other) {
        println "equals called"
        false
    }

    int compareTo(Object o) {
        println "compareTo called"
        return 0
    }
}

class B implements Comparable{
    boolean equals(other) {
        println "equals called"
        false
    }

    int compareTo(Object o) {
        println "compareTo called"
        return 0
    }
}
new A() == new A()
new B() == new B()




// 13、
@groovy.transform.Immutable class Customer1 {
    LinkedHashMap map
    String first, last
    int age
    private Date since = new Date()
    Collection favItems
}
println new Customer1(age:10).age
println new Customer1(age:10).since


// 14、测试Lazy
class LazyClass {
    @Lazy
    private Date date1;
    @Lazy
    private volatile Date date2;
    @Lazy
    private Date date3 = {new Date().copyWith(year: 2000) }();
    @Lazy(soft = true)
    private Date date4;
    @Lazy
    private static Date date5;
    @Lazy(soft = true)
    private static Date date6;
}
println "14、测试Lazy"
def lazy = new LazyClass();
println lazy.date1;
println lazy.date2;
println lazy.date3;
println lazy.date4;
println lazy.date5;
println lazy.date6;
// 由于下面睡眠，影响到其他的测试速度，所以注释掉
//Thread.sleep(5000);
println lazy.date4;
println lazy.date6;


// 15、测试字面量对象
println "15、测试数字的字面对象"
println 10.0.getClass()
println 10.getClass()
println 'a'.getClass()
println "a".getClass()
println true.getClass()


// 16、测试闭包的参数类型
println "16、测试闭包的参数类型"
def examine(closure) {
    println "$closure.maximumNumberOfParameters parameter(s) given:"
    for (aParameter in closure.parameterTypes) {
        println aParameter.name
    }

    println "--"
}

examine() {}
examine() { it }
examine() { -> }
examine() { val1 -> }
examine() { Date val1 -> }
examine() { Date val1, val2 -> }
examine() { Date val1, String val2 -> }



// 17、测试闭包委托
println "17、测试闭包委托"
def examiningClosure(closure) {
    closure()
}

examiningClosure() {
    println "In First Closure:"
    println "class is " + getClass().name
    println "this.class is " + this.getClass().name
    println "toString is " + toString()
    println "this.toString is " + this.toString()
    println "this is " + this + ", super:" + this.getClass().superclass.name
    println "thisObject is " + thisObject
    println "owner is " + owner + ", supper:" + owner.getClass().superclass.name
    println "delegate is " + delegate + ", supper:" + delegate.getClass().superclass.name

    examiningClosure() {
        println "In Closure within the First Closure:"
        println "class is " + getClass().name
        println "this.class is " + this.getClass().name
        println "toString is " + toString()
        println "this.toString is " + this.toString()
        println "this is " + this + ", super:" + this.getClass().superclass.name
        println "thisObject is " + thisObject
        println "owner is " + owner + ", supper:" + owner.getClass().superclass.name
        println "delegate is " + delegate + ", supper:" + delegate.getClass().superclass.name
    }
}



// 18、测试闭包方法查找顺序
println "18、测试闭包方法查找顺序"
class Handler {
    def f1() { println "f1 of Handler called ..." }
    def f2() { println "f2 of Handler called ..." }
}

class Example {
    def f1() { println "f1 of Example called ..." }
    def f2() { println "f2 of Example called ..." }

    def foo(closure) {
        closure.delegate = new Handler()
        closure()
    }
}

def f1() { println "f1 of Script called ..." }

new Example().foo {
    println "this is " + this + ", super:" + this.getClass().superclass.name
    println "owner is " + owner + ", supper:" + owner.getClass().superclass.name
    println "delegate is " + delegate + ", supper:" + delegate.getClass().superclass.name
    f1()
    f2()

    new Example().foo {
        println "this is " + this + ", super:" + this.getClass().superclass.name
        println "owner is " + owner + ", supper:" + owner.getClass().superclass.name
        println "delegate is " + delegate + ", supper:" + delegate.getClass().superclass.name
        f1()
        f2()
    }
}

new Example().with {

}



// 19、测试尾递归
println "19、测试尾递归"
def factorial // 这样把单独定义变量作为一行，是因为要在闭包里引用该变量，如果把定义变量和赋值当做一行，就会在闭包里出现找不到变量的错误
factorial = { int number, BigInteger theFactorial ->
    number == 1 ? theFactorial : factorial.trampoline(number - 1, number * theFactorial)
}.trampoline()

println "factorial of 5 is ${factorial(5, 1)}"
// 由于下面计算时间长，注释掉
//println "number of bits in the result is ${factorial(5000, 1).bitCount()}"



// 20、测试闭包的记忆化
println "20、测试闭包的记忆化"
def timeIt(length, closure) {
    long start = System.nanoTime()
    println "Max revenue for $length is ${closure(length)}"
    long end =System.nanoTime()
    println "Time taken ${(end - start)/1.0e9} seconds"
}

def rodPrices = [0, 1, 3, 4, 5, 8, 9, 11, 12, 14,
                 15, 15, 16, 18, 19, 15, 20, 21, 22, 24,
                 25, 24, 26, 28, 29, 35, 37, 38, 39, 40]

def desiredLength = 27

@Immutable
class RevenueDetails {
    int revenue
    ArrayList splits
}

def cutRod
cutRod = { prices, length ->
    if (length == 0)
        new RevenueDetails(0, [])
    else {
        def maxRevenueDetails = new RevenueDetails(Integer.MIN_VALUE, [])
        for (rodSize in 1..length) {
            def revenueFromSecondHalf = cutRod(prices, length - rodSize)
            def potentialRevenue = new RevenueDetails(
                    prices[rodSize] + revenueFromSecondHalf.revenue,
                    revenueFromSecondHalf.splits + rodSize)
            if (potentialRevenue.revenue > maxRevenueDetails.revenue)
                maxRevenueDetails = potentialRevenue
        }
        maxRevenueDetails
    }
}.memoize()

timeIt desiredLength, { length -> cutRod(rodPrices, length) }



// 21、测试GString的惰性求值问题
println "21、测试GString的惰性求值问题"
quoto = "Today ${-> company } stock closed at ${-> price }"
stocks = [Apple : 663.01, Microsoft : 30.95]
stocks.each { key, value ->
    company = key
    price = value
    println quoto
}



// 22、测试GString操作符重载
println "22、测试GString操作符重载"
println "dda"++ //把字符串的最后一个字符+1，只能是后缀操作符，这个操作符是没有什么用处的，一般只用作foreach循环中吧
for (s in "ab".."ag") println s //这里调用的就是++操作符，这里的字符串范围，只能是最后一个字符不同，不然会抛异常。
println "daddddadd" - 'a' //删掉第一个出现的
println "daddddadd1" * 3 //重复几遍，数值必须大于等于0



// 23、测试List的角标
println "23、测试List的角标"
lst = [1, 3, 4, 1, 8, 9, 2, 6]
println lst[-1] // 负数表示从尾部开始算，-1表示尾部的第一个元素，即最后一个
println lst[-2]

subLst = lst[2..5]
println subLst.dump()
subLst[0] = 55
println "After subLst[0] = 55 lst = $lst"



// 24、测试with方法
println "24、测试with方法"
lst = [1, 2]
lst.with {
    add(3)
    add(4)
    println size()
    println contains(2)
}

// 25、测试DOMCategory的XML解析
println "25、测试DOMCategory的XML解析"
document = DOMBuilder.parse(getClass().getResource("/languages.xml").newReader()) // 解析xml文件获取document节点

languages = document.documentElement // 通过document节点获取其根元素的节点

use(DOMCategory) {
    println 'Languages and authors'
    // 获取某节点下的所有节点的方法，或节点的属性，
    // 就像访问对象属性一样的有3种方式：languages['language']、languages.'language'、languages.language
    // 只是属性的访问，需要在属性名前加上@
    // 对于节点集合，又可以像数组或List那样用索引访问
    languages['language'].each { language ->
        println "${language.'@name'} authored by ${language.author[0].text()}"
    }

    def languagesByAuthor = { authorName ->
        languages.language.findAll { it.author[0].text() == authorName }.collect { it.'@name' }.join(', ')
    }

    println "languages by Wirth:" + languagesByAuthor('Wirth')
}


// 26、测试XMLParser的XML解析
println "26、测试XMLParser的XML解析"
languages = new XmlParser().parse(getClass().getResource("/languages.xml").newReader())
println 'Languages and authors'
// 节点的访问和属性的访问同使用DOMCategory一样
languages.each {
    println "${it.'@name'} authored by ${it.author[0].text()}"
}
// 这2种写法是一样的
println '另外的写法：Languages and authors'
languages.language.each {
    println "${it.'@name'} authored by ${it.author[0].text()}"
}

def languagesByAuthor = { authorName ->
    languages.findAll { it.author[0].text() == authorName }.collect { it.'@name' }.join(', ')
}

println "languages by Wirth:" + languagesByAuthor('Wirth')



// 27、测试XMLSlurper的XML解析，parse方法解析得到的是根节点
println "27、测试XMLSlurper的XML解析，parse方法解析得到的是根节点"
languages = new XmlSlurper().parse(getClass().getResource("/computerAndNaturalLanguages.xml").newReader())
        .declareNamespace(human:'http://www.twx.test.com.cn/Natural')

print "Languages:"
println languages.language.collect { it.@name}.join(', ')
print "根节点本身:"
println languages.collect { it.@name}.join(', ')

print "Natural languages:"
println languages.'human:language'.collect { it.@name}.join(', ')



// 28、
println "28、"



// 29、
println "29、"



// 30、
println "30、"






























