package com.twx.java.net;

import com.twx.BaseTest;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author wenxin.tong
 * @since 2018/1/31
 */
public class UrlTest extends BaseTest {

    @Test
    public void test001() throws Exception {
        URL url = new URL("http://v3.lingke100.com/boss/ag;jsessionid=5AC6268DD8D4D5D1FDF5D41E9F2FD960?aaa=555&saf=aaa#sdf");
        url = new URL(url, "asdf/asf");
        MessageUtil.onTime(url.getProtocol());
        MessageUtil.onTime(url.getHost());
        MessageUtil.onTime(url.getFile());
        MessageUtil.onTime(url.getAuthority());
        MessageUtil.onTime(url.getPath());
        MessageUtil.onTime(url.getQuery());
        MessageUtil.onTime(url.getPort());
        MessageUtil.onTime(url.getRef());
        MessageUtil.onTime(url.getUserInfo());
        MessageUtil.onTime(url.toExternalForm());
    }

    @Test
    public void test002() throws Exception {
        URL url = new URL("ftp://george@x.com:90/public/notes?text=shakespeare#hamlet");
        MessageUtil.onTime(url.getProtocol());
        MessageUtil.onTime(url.getHost());
        MessageUtil.onTime(url.getFile());
        MessageUtil.onTime(url.getAuthority());
        MessageUtil.onTime(url.getPath());
        MessageUtil.onTime(url.getQuery());
        MessageUtil.onTime(url.getPort());
        MessageUtil.onTime(url.getRef());
        MessageUtil.onTime(url.getUserInfo());
        MessageUtil.onTime(url.toExternalForm());
    }

    @Test
    public void test003() throws Exception {
        thrown.expect(URISyntaxException.class);
        new URI("//foo:bar").parseServerAuthority();
    }


}
