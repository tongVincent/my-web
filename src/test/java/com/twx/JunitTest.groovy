package com.twx

/**
 * @author vincent.tong on 2017/6/9
 */

class ListTest {
    void testListSize() {
        def lst = [1,2]
        asserEquals "ArrayList size must be 2",  2, lst.size()
    }
}


