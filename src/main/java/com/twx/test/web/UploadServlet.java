package com.twx.test.web;

import com.twx.test.util.MessageUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Created by vincent.tong on 2016/7/6.
 */
@WebServlet(urlPatterns="/upload", asyncSupported=true)
@MultipartConfig
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Part> parts = req.getParts();
        for (Part p : parts) {
            MessageUtil.onTime(p.getName());
            MessageUtil.onTime(p.getSize());
            if (p.getContentType() == null) {
                ByteArrayOutputStream out = getOutputStream(p.getInputStream());
                String text = new String(out.toByteArray(), Charset.forName("utf-8"));
                MessageUtil.onTime(text);
            }
        }
    }

    private ByteArrayOutputStream getOutputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] cache = new byte[1024];
        try {
            int count = inputStream.read(cache);
            while (count != -1) {
                baos.write(cache, 0, count);
                baos.flush();
                count = inputStream.read(cache);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (null != inputStream) inputStream.close();
            baos.close();
        }
        return baos;
    }
}
