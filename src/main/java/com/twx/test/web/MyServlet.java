package com.twx.test.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vincent.tong on 2016/7/5.
 */
@WebServlet(urlPatterns = "/test/myServlet")
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = -8016328059808092454L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("进入MyServlet的时间：" + LocalDateTime.now() + ".");
        out.flush();
        out.println("结束MyServlet的时间：" + LocalDateTime.now() + ".");
        out.flush();
    }
}
