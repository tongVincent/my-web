package com.twx.test.web;

import com.twx.test.util.MessageUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vincent.tong on 2016/7/1.
 */
@WebServlet(urlPatterns="/demo2", asyncSupported=true)
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
        ctx.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                MessageUtil.onTime("here on complete2:");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                MessageUtil.onTime("here on timeout2:");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                MessageUtil.onTime("here on error2:");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                MessageUtil.onTime("here on startAsync2:");
            }
        });
        ctx.start(() -> {
            try {
                MessageUtil.onTime("start run2");
                Thread.sleep(2000); // 让线程休眠2s钟模拟超时操作
                PrintWriter wirter = ctx.getResponse().getWriter();
                wirter.write("延迟输出2");
                wirter.flush();
                ctx.complete();
                MessageUtil.onTime("延迟输出2:");
            } catch (InterruptedException e) {

            } catch (IOException e) {

            }
        });
        out.println("结束AsyncServlet2的时间：" + LocalDateTime.now() + ".");
        out.flush();
    }
}
