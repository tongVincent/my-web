package com.twx.test.web;

import com.twx.test.util.MessageUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

/**
 * Created by vincent.tong on 2016/7/13.
 */
// 1、验证默认servlet下的各种路径信息，此时请把urlPatterns设为"/"
// 2、验证取得实际路径：urlPatterns依次设为：""、""
@WebServlet(urlPatterns = "/test/path/*")
public class DefaultServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //验证默认servlet下的各种路径信息
        MessageUtil.onTime("here is default servlet.");

        MessageUtil.onTime("contextPath: " + req.getContextPath());
        MessageUtil.onTime("servletPath: " + req.getServletPath());
        MessageUtil.onTime("pathInfo: " + req.getPathInfo());
        MessageUtil.onTime("uri: " + req.getRequestURI());
        MessageUtil.onTime("url: " + req.getRequestURL().toString());
        MessageUtil.onTime("url2: " + HttpUtils.getRequestURL(req));

        //验证取得实际路径
        MessageUtil.onTime(req.getPathTranslated());
        MessageUtil.onTime(req.getRealPath(req.getPathInfo()));
        MessageUtil.onTime("/: " + req.getRealPath("/")); //与下面的相同，即"/"和""都表示跟路径，但最好是加上"/"
        MessageUtil.onTime("空 : " + req.getRealPath(""));
        MessageUtil.onTime("null : " + req.getRealPath(null)); //返回null
        // 对于下面2句，结果都是相同，即有没有"/"开头都一样。但最好是加上"/"。
        // 因为根据源码可以看出，当环境为严格要求的时候，对于没有以"/"开头的将会返回null。
        MessageUtil.onTime("/my/name: " + req.getRealPath("/my/name"));
        MessageUtil.onTime("my/name: " + req.getRealPath("my/name"));


    }
}
