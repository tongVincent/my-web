package com.twx.test.ws.client;

import com.twx.test.ws.server.HelloWS;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * Created by vincent.tong on 2017/3/14.
 */
public class CxfClient {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(HelloWS.class);
        factory.setAddress("http://localhost:8999/services/hello");
        HelloWS helloWS = factory.create(HelloWS.class);
        String welcome = helloWS.welcome("accountwcx@qq.com");
        System.out.println(welcome);
    }
}
