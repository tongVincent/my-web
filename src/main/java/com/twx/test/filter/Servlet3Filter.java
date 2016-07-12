package com.twx.test.filter;

import com.twx.test.servlet.MyRequest;
import com.twx.test.util.MessageUtil;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by vincent.tong on 2016/6/28.
 */
// 1）对于异步请求，filter的dispatcherTypes 必须要包含DispatcherType.ASYNC，
//    这样通过AsyncContext.dispatch方法分派的请求，就可以通过该filter了。
// 2）如果在service方法中需要启动异步周期的，那么该请求经过的filters和最后的servlet，都必须设置asyncSupported = true。
@WebFilter(filterName = "Servlet3Filter", urlPatterns = "/*", asyncSupported = true, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ASYNC})
public class Servlet3Filter implements Filter {

    @Override
    public void destroy() {
        MessageUtil.onTime("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        MessageUtil.onTime("执行过滤操作" + ((HttpServletRequest)request).getRequestURI());
        chain.doFilter(new MyRequest((HttpServletRequest) request), response);
//        AsyncContext context = request.getAsyncContext();
//        context.setTimeout(30000);
        MessageUtil.onTime("执行过滤操作后" + ((HttpServletRequest)request).getRequestURI());
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        MessageUtil.onTime("过滤器初始化");
    }
}
