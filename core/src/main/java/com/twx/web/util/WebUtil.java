package com.twx.web.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wenxin.tong
 * @since 2017/4/25
 */
public abstract class WebUtil {
    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static Object getApplicationAttribute(String name) {
        return getServletContext().getAttribute(name);
    }

    public static void setApplicationAttribute(String name, Object value) {
        getServletContext().setAttribute(name, value);
    }

    public static String getRealPath(String path) {
        return getServletContext().getRealPath(path);
    }
}
