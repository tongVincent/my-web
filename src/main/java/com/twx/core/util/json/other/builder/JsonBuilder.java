package com.twx.core.util.json.other.builder;

/**
 * Created by vincent.tong on 2017/2/23.
 */
public interface JsonBuilder {

    JsonBuilder with(String propertyName);

    JsonBuilder put(String propertyName);
}
