package com.twx.test.groovy;
/**
 * Created by vincent.tong on 2016/11/7.
 */
//class TestMethodInvocation extends GroovyTestCase {
class TestMethodInvocation {

    void testInterceptedMethodCallonPOJO() {
        def val = new Integer(3)
        Integer.metaClass.toString = {-> 'intercepted'}

        asserEquals "intercepted", val.toString()
    }

    void testInterceptedCalled() {
        def obj = new AnInterceptable()
        asserEquals 'intercepted', obj.existingMethod()
        asserEquals 'intercepted', obj.nonExistingMethod()
    }

    void testInterceptedExistingMethodCalled() {
        AGroovyObject.metaClass.existingMethod2 = {->'intercepted'}
        def obj = new AGroovyObject()
        asserEquals 'intercepted', obj.existingMethod2()
    }

    void testUnInterceptedExistingMethodCalled() {
        def obj = new AGroovyObject()
        asserEquals 'existingMethod', obj.existingMethod()
    }

    void testPropertyThatIsClosureCalled() {
        def obj = new AGroovyObject()
        asserEquals 'closure called', obj.closureProp()
    }

    void testMethodMissingCalledOnlyForNonExistent() {
        def obj = new ClassWithInvokeAndMissingMethod()
        asserEquals 'existingMethod', obj.existingMethod()
        asserEquals 'missing called', obj.nonExistingMethod()
    }

    void testInvokeMethodCalledForOnlyNonExistent() {
        def obj = new ClassWithInvokeOnly()
        asserEquals 'existingMethod', obj.existingMethod()
        asserEquals 'invoke called', obj.nonExistingMethod()
    }

    void testMethodFailsOnNonExistent() {
        def obj = new TestMethodInvocation()
        shouldFail (MissingMethodException) { obj.nonExistingMethod() }
    }
}

class AnInterceptable implements GroovyInterceptable {
    def existingMethod() {}
    def invokeMethod(String name, args) { 'intercepted'}
}

class AGroovyObject {
    def existingMethod() { 'existingMethod' }
    def existingMethod2() { 'existingMethod2' }
    def closureProp = { 'closure called' }
}

class ClassWithInvokeAndMissingMethod {
    def existingMethod() { 'existingMethod' }
    def invokeMethod(String name, args) { 'invoke called' }
    def methodMissing(String name, args) { 'missing called' }
}

class ClassWithInvokeOnly {
    def existingMethod() { 'existingMethod' }
    def invokeMethod(String name, args) { 'invoke  called' }
}