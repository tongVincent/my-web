package com.twx.test.web;

import com.twx.core.util.MessageUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vincent.tong on 2016/7/1.
 */
@WebServlet(urlPatterns = "/test/demo2", asyncSupported = true)
public class AsyncServlet2 extends HttpServlet {
    private static final long serialVersionUID = -8016328059808092454L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("进入AsyncServlet2的时间：" + LocalDateTime.now() + ".");
        out.flush();

        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        AsyncContext ctx = req.startAsync(req, resp);
        ctx.addListener(new MyAsyncListener("AsyncServlet2"));
        ctx.start(() -> {
            try {
                MessageUtil.onTime("start run2");
                Thread.sleep(2000); // 让线程休眠2s钟模拟超时操作
                PrintWriter writer = ctx.getResponse().getWriter();
                writer.write("延迟输出2");
                writer.flush();
                ctx.complete();
                MessageUtil.onTime("延迟输出2:");
            } catch (InterruptedException e) {

            } catch (IOException e) {

            }
        });
        out.println("结束AsyncServlet2的时间：" + LocalDateTime.now() + ".");
        out.flush(); // 如果这里调用了out.close()则在异步周期中，就不能用writer输出内容到页面了，但依然可以执行其他异步操作
    }
}
