package com.twx.test.web;

import com.twx.core.util.json.JSONBinder;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/3/23.
 */
@WebServlet(urlPatterns = "/cookies")
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            out.println("没有cookies");
        } else {
            JSONBinder<Cookie> binder = JSONBinder.binder(Cookie.class);
            for (Cookie cookie : cookies) {
                out.println(binder.toJson(cookie));
            }
        }

        out.close();
    }
}
