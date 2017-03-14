package com.twx.test.ws.server.impl;

import com.twx.test.ws.server.HelloWS;

import javax.jws.WebService;

/**
 * Created by vincent.tong on 2017/3/14.
 */
@WebService(
    endpointInterface = "com.twx.test.ws.server.HelloWS",
    portName = "HelloWSPort",
    serviceName = "HelloWSService",
    targetNamespace = "http://www.tmp.com/services/hello"
)
public class HelloWSImpl implements HelloWS {
    @Override
    public String welcome(String name) {
        return "Welcome " + name;
    }
}
