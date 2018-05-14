package com.twx.test.web;

import com.twx.core.util.MessageUtil;

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
 * Created by vincent.tong on 2016/6/28.
 */
@WebServlet(urlPatterns = "/test/demo", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    private static final long serialVersionUID = -8016328059808092454L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("进入AsyncServlet的时间：" + LocalDateTime.now() + ".");
        out.flush();

        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        AsyncContext ctx = req.startAsync(req, resp);
        ctx.addListener(new MyAsyncListener("AsyncServlet"));
        //ctx = req.startAsync(req, resp); // 此处不能再调用startAsync了，因为在同一个dispatch中
        ctx.setTimeout(20000);
        ctx.start(new Work(ctx)); // 如果没有调用这个方法，那么异步操作，会等到超时的时候结束异步周期。
        out.println("结束AsyncServlet的时间：" + LocalDateTime.now() + ".");
        out.flush();
        //ctx.complete(); //如果在这个方法调用之前，已经有线程开启执行异步操作了，
        // 那么在这里又调用complete方法，将结束异步周期，而在异步线程中如果有调用AsyncContext相关的方法将抛出异常。
    }
}

class Work extends Thread {
    private final AsyncContext context;

    public Work(AsyncContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            MessageUtil.onTime("start run");
            Thread.sleep(2000); // 让线程休眠2s钟模拟超时操作
            PrintWriter wirter = context.getResponse().getWriter();
            //context.setTimeout(100000); // 此时设置timeout只要还没有超时，就没有问题，如果已经超时，就抛出异常
            wirter.write("延迟输出");
            wirter.flush();
//            if (1 == 1) {
//                throw new RuntimeException("here test exception.");
//            } //如果此时抛出异常，而代码没有在catch里complete，那么只有等到timeout了才能完成异步周期，所以要在catch中调用complete方法。
            context.dispatch("/demo2");
            //context.setTimeout(100000); // 此时设置timeout会抛出异常
            MessageUtil.onTime("延迟输出:");
        } catch (InterruptedException e) {

        } catch (IOException e) {

        }
    }
}

class MyAsyncListener implements AsyncListener {
    private final String version;

    public MyAsyncListener(String version) {
        this.version = version;
    }

    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        MessageUtil.onTime("here " + version + " on complete:");
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        MessageUtil.onTime("here " + version + " on timeout:");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        MessageUtil.onTime("here " + version + " on error:");
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        MessageUtil.onTime("here " + version + " on startAsync:");
    }
}

