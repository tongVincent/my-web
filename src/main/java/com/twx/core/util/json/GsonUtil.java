package com.twx.core.util.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by vincent.tong on 2016/11/16.
 */
public abstract class GsonUtil {
    private static Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return GsonUtil.fromJson(json, typeToken.getType());
    }
}
