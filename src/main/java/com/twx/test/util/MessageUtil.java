package com.twx.test.util;

import java.time.LocalDateTime;

/**
 * Created by vincent.tong on 2016/6/30.
 */
public abstract class MessageUtil {
    public static void onTime(Object message) {
        System.out.println(LocalDateTime.now() + " has message: " + message);
    }
}
