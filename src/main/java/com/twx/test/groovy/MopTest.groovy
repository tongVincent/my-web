import groovy.io.EncodingAwareBufferedWriter
import org.springframework.web.context.support.XmlWebApplicationContext
/**
 * Created by vincent.tong on 2016/11/11.
 */
// 通过下面的测试，可以知道：
//1、如果只在实例对象的metaClass上添加方法，那么此方法将只对该实例有效，对其他实例没有任何影响。
//2、如果在类的metaClass上添加方法：
// 对于POJO来说，此方法对所有实例对象都有效，不管实例是在添加方法之前还是之后
// 对于POGO来说，此方法只对添加方法之后新创建的实例有效，在添加方法之前创建的实例没有任何影响。
//3、如果查看metaClass的类型，将会发现:
//对于类的和实例的metaClass是一个HandleMetaClass实例，其delegate是MetaClassImpl实例。
//如果只在实例对象的metaClass上添加方法，那么只改变该实例的metaClass，它还是一个HandleMetaClass实例，但其delegate是ExpandoMetaClass实例。
//
//如果在类的metaClass上添加方法：
// 对于POJO来说，实例的metaClass都是一个HandleMetaClass实例，其delegate是ExpandoMetaClass实例。而类的metaClass是一个ExpandoMetaClass实例。
// 对于POGO来说，添加方法之前的实例的metaClass不变。添加方法之后的新创建的实例metaClass是一个HandleMetaClass实例，其delegate是ExpandoMetaClass实例。而类的metaClass是一个ExpandoMetaClass实例。
class BGroovyObject {
    def existingMethod() { 'existingMethod' }
    def existingMethod2() { 'existingMethod2' }
    def closureProp = { 'closure called' }
}

def printMetaClassInfo(instance) {
    print "MetaClass of ${instance} is ${instance.metaClass.class.simpleName}"
    println " with delegate ${instance.metaClass.delegate.class.simpleName}"
}

println "------------测试改变实例的MetaClass方法 begin ---------"
println "测试基本类"
val1 = new Integer(3)
println val1.toString()
printMetaClassInfo(val1)
println "MetaClass of Integer is ${Integer.metaClass.class.simpleName} with delegate ${Integer.metaClass.delegate.class.simpleName}"
println "--"
val1.metaClass.toString = {-> 'intercepted'}
println val1.toString()
val2 = 6
println val2.toString()
printMetaClassInfo(val1)
printMetaClassInfo(val2)
println "MetaClass of Integer is ${Integer.metaClass.class.simpleName} with delegate ${Integer.metaClass.delegate.class.simpleName}"


println "测试基本类的double"
d1 = 3.5
println d1.toString()
printMetaClassInfo(d1)
println "MetaClass of BigDecimal is ${BigDecimal.metaClass.class.simpleName} with delegate ${BigDecimal.metaClass.delegate.class.simpleName}"
println "--"
d1.metaClass.toString = {-> 'intercepted'}
println d1.toString()
d2 = 6.0
println d2.toString()
printMetaClassInfo(d1)
printMetaClassInfo(d2)
println "MetaClass of BigDecimal is ${BigDecimal.metaClass.class.simpleName} with delegate ${BigDecimal.metaClass.delegate.class.simpleName}"


println "测试String类"
str1 = "test"
println str1.toUpperCase()
printMetaClassInfo(str1)
println "MetaClass of String is ${String.metaClass.class.simpleName} with delegate ${String.metaClass.delegate.class.simpleName}"
println "--"
str1.metaClass.toUpperCase = {-> 'intercepted'}
println str1.toUpperCase()
str2 = "after"
println str2.toUpperCase()
printMetaClassInfo(str1)
printMetaClassInfo(str2)
println "MetaClass of String is ${String.metaClass.class.simpleName} with delegate ${String.metaClass.delegate.class.simpleName}"


println "测试ArrayList类"
list1 = ["3", "5"]
println list1.toString()
printMetaClassInfo(list1)
println "MetaClass of ArrayList is ${ArrayList.metaClass.class.simpleName} with delegate ${ArrayList.metaClass.delegate.class.simpleName}"
println "--"
list1.metaClass.toString = {-> "intercepted"}
println list1.toString()
list2 = ["3", "5", "sdf"]
println list2.toString()
printMetaClassInfo(list1)
printMetaClassInfo(list2)
println "MetaClass of ArrayList is ${ArrayList.metaClass.class.simpleName} with delegate ${ArrayList.metaClass.delegate.class.simpleName}"


println "测试XmlWebApplicationContext类"
context1 = new XmlWebApplicationContext()
println context1.toString()
printMetaClassInfo(context1)
println "MetaClass of XmlWebApplicationContext is ${XmlWebApplicationContext.metaClass.class.simpleName} with delegate ${XmlWebApplicationContext.metaClass.delegate.class.simpleName}"
println "--"
context1.metaClass.toString = {-> "intercepted"}
println context1.toString()
context2 = new XmlWebApplicationContext()
println context2.toString()
printMetaClassInfo(context1)
printMetaClassInfo(context2)
println "MetaClass of XmlWebApplicationContext is ${XmlWebApplicationContext.metaClass.class.simpleName} with delegate ${XmlWebApplicationContext.metaClass.delegate.class.simpleName}"


println "测试EncodingAwareBufferedWriter类"
w1 = new EncodingAwareBufferedWriter(new OutputStreamWriter(System.out))
println w1.toString()
printMetaClassInfo(w1)
println "MetaClass of EncodingAwareBufferedWriter is ${EncodingAwareBufferedWriter.metaClass.class.simpleName} with delegate ${EncodingAwareBufferedWriter.metaClass.delegate.class.simpleName}"
println "--"
w1.metaClass.toString = {-> "intercepted"}
println w1.toString()
w2 = new EncodingAwareBufferedWriter(new OutputStreamWriter(System.out))
println w2.toString()
printMetaClassInfo(w1)
printMetaClassInfo(w2)
println "MetaClass of EncodingAwareBufferedWriter is ${EncodingAwareBufferedWriter.metaClass.class.simpleName} with delegate ${EncodingAwareBufferedWriter.metaClass.delegate.class.simpleName}"


println "测试Binding类"
bind1 = new Binding()
println bind1.toString()
printMetaClassInfo(bind1)
println "MetaClass of Binding is ${Binding.metaClass.class.simpleName} with delegate ${Binding.metaClass.delegate.class.simpleName}"
println "--"
bind1.metaClass.toString = {-> "intercepted"}
println bind1.toString()
bind2 = new Binding()
println bind2.toString()
printMetaClassInfo(bind1)
printMetaClassInfo(bind2)
println "MetaClass of Binding is ${Binding.metaClass.class.simpleName} with delegate ${Binding.metaClass.delegate.class.simpleName}"


println "测试自定义类"
obj1 = new BGroovyObject()
println obj1.existingMethod2()
printMetaClassInfo(obj1)
println "MetaClass of BGroovyObject is ${BGroovyObject.metaClass.class.simpleName} with delegate ${BGroovyObject.metaClass.delegate.class.simpleName}"
println "--"
obj1.metaClass.existingMethod2 = {->'intercepted'}
println obj1.existingMethod2()
obj2 = new BGroovyObject()
println obj2.existingMethod2()
printMetaClassInfo(obj1)
printMetaClassInfo(obj2)
println "MetaClass of BGroovyObject is ${BGroovyObject.metaClass.class.simpleName} with delegate ${BGroovyObject.metaClass.delegate.class.simpleName}"
println "------------测试改变实例的MetaClass方法 end ---------"

println()
println()

println "------------测试改变类的MetaClass方法 begin ---------"
println "测试基本类"
val3 = new Integer(3)
println val3.toString()
printMetaClassInfo(val3)
println "MetaClass of Integer is ${Integer.metaClass.class.simpleName} with delegate ${Integer.metaClass.delegate.class.simpleName}"
println "--"
Integer.metaClass.toString = {-> 'intercepted'}
println val3.toString()
val4 = 6
println val4.toString()
printMetaClassInfo(val3)
printMetaClassInfo(val4)
println "MetaClass of Integer is ${Integer.metaClass.class.simpleName} with delegate ${Integer.metaClass.delegate.class.simpleName}"


println "测试基本类的double"
d3 = 4.0
println d3.toString()
printMetaClassInfo(d3)
println "MetaClass of BigDecimal is ${BigDecimal.metaClass.class.simpleName} with delegate ${BigDecimal.metaClass.delegate.class.simpleName}"
println "--"
BigDecimal.metaClass.toString = {-> 'intercepted'}
println d3.toString()
d4 = 6.0
println d4.toString()
printMetaClassInfo(d3)
printMetaClassInfo(d4)
println "MetaClass of BigDecimal is ${BigDecimal.metaClass.class.simpleName} with delegate ${BigDecimal.metaClass.delegate.class.simpleName}"


println "测试String类"
str3 = "test23"
println str3.toUpperCase()
printMetaClassInfo(str3)
println "MetaClass of String is ${String.metaClass.class.simpleName} with delegate ${String.metaClass.delegate.class.simpleName}"
println "--"
String.metaClass.toUpperCase = {-> 'intercepted'}
println str3.toUpperCase()
str4 = "after"
println str4.toUpperCase()
printMetaClassInfo(str3)
printMetaClassInfo(str4)
println "MetaClass of String is ${String.metaClass.class.simpleName} with delegate ${String.metaClass.delegate.class.simpleName}"


println "测试ArrayList类"
list3 = ["3", "5"]
println list3.toString()
printMetaClassInfo(list3)
println "MetaClass of ArrayList is ${ArrayList.metaClass.class.simpleName} with delegate ${ArrayList.metaClass.delegate.class.simpleName}"
println "--"
ArrayList.metaClass.toString = {-> "intercepted"}
println list3.toString()
list4 = ["3", "5", "sdf"]
println list4.toString()
printMetaClassInfo(list3)
printMetaClassInfo(list4)
println "MetaClass of ArrayList is ${ArrayList.metaClass.class.simpleName} with delegate ${ArrayList.metaClass.delegate.class.simpleName}"


println "测试XmlWebApplicationContext类"
context3 = new XmlWebApplicationContext()
println context3.toString()
printMetaClassInfo(context3)
println "MetaClass of XmlWebApplicationContext is ${XmlWebApplicationContext.metaClass.class.simpleName} with delegate ${XmlWebApplicationContext.metaClass.delegate.class.simpleName}"
println "--"
XmlWebApplicationContext.metaClass.toString = {-> "intercepted"}
println context3.toString()
context4 = new XmlWebApplicationContext()
println context4.toString()
printMetaClassInfo(context3)
printMetaClassInfo(context4)
println "MetaClass of XmlWebApplicationContext is ${XmlWebApplicationContext.metaClass.class.simpleName} with delegate ${XmlWebApplicationContext.metaClass.delegate.class.simpleName}"


println "测试EncodingAwareBufferedWriter类"
w3 = new EncodingAwareBufferedWriter(new OutputStreamWriter(System.out))
println w3.toString()
printMetaClassInfo(w3)
println "MetaClass of EncodingAwareBufferedWriter is ${EncodingAwareBufferedWriter.metaClass.class.simpleName} with delegate ${EncodingAwareBufferedWriter.metaClass.delegate.class.simpleName}"
println "--"
EncodingAwareBufferedWriter.metaClass.toString = {-> "intercepted"}
println w3.toString()
w4 = new EncodingAwareBufferedWriter(new OutputStreamWriter(System.out))
println w4.toString()
printMetaClassInfo(w3)
printMetaClassInfo(w4)
println "MetaClass of EncodingAwareBufferedWriter is ${EncodingAwareBufferedWriter.metaClass.class.simpleName} with delegate ${EncodingAwareBufferedWriter.metaClass.delegate.class.simpleName}"


println "测试Binding类"
bind3 = new Binding()
println bind3.toString()
printMetaClassInfo(bind3)
println "MetaClass of Binding is ${Binding.metaClass.class.simpleName} with delegate ${Binding.metaClass.delegate.class.simpleName}"
println "--"
Binding.metaClass.toString = {-> "intercepted"}
println bind3.toString()
bind4 = new Binding()
println bind4.toString()
printMetaClassInfo(bind3)
printMetaClassInfo(bind4)
println "MetaClass of Binding is ${Binding.metaClass.class.simpleName} with delegate ${Binding.metaClass.delegate.class.simpleName}"


println "测试自定义类"
obj3 = new BGroovyObject()
println obj3.existingMethod2()
printMetaClassInfo(obj3)
println "MetaClass of BGroovyObject is ${BGroovyObject.metaClass.class.simpleName} with delegate ${BGroovyObject.metaClass.delegate.class.simpleName}"
println "--"
BGroovyObject.metaClass.existingMethod2 = {->'intercepted'}
println obj3.existingMethod2()
obj4 = new BGroovyObject()
println obj4.existingMethod2()
printMetaClassInfo(obj3)
printMetaClassInfo(obj4)
println "MetaClass of BGroovyObject is ${BGroovyObject.metaClass.class.simpleName} with delegate ${BGroovyObject.metaClass.delegate.class.simpleName}"
println "------------测试改变类的MetaClass方法 end ---------"


