package com.twx.test.ws;

import com.twx.test.ws.server.HelloWS;
import com.twx.test.ws.server.impl.HelloWSImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * Created by vincent.tong on 2017/3/14.
 */
public class CxfServer {

    public static void main(String[] args) {
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloWS.class);
        //服务发布的地址
        factory.setAddress("http://localhost:8999/services/hello");
        factory.setServiceBean(new HelloWSImpl());
        factory.create();
    }

}
