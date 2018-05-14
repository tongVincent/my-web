package com.twx.test.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by vincent.tong on 2017/3/14.
 */
@WebService(name = "HelloWS", targetNamespace = "http://www.tmp.com/services/hello")
public interface HelloWS {
    @WebMethod
    String welcome(@WebParam(name = "name") String name);
}
