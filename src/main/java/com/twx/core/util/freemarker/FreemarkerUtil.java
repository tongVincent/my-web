package com.twx.core.util.freemarker;

import com.twx.core.util.CharacterEncodings;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vincent.tong on 2017/4/22
 */
public abstract class FreemarkerUtil {

    /**
     *
     * @param templatePath 模板所在的真实路径
     * @param templateName 模板名称，相对于templatePath
     * @param fileName 保存的文件路径
     * @param root 解析模板需要的参数
     */
    public static void parseDirectoryTemplate2File(String templatePath, String templateName, String fileName, Map<String, Object> root) {
        try (Writer out = new OutputStreamWriter(new FileOutputStream(fileName), CharacterEncodings.UTF_8)) {
            Configuration config = new Configuration();
            // 设置要解析的模板所在的目录，并加载模板文件
            config.setDirectoryForTemplateLoading(new File(templatePath));
            // 设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapper());

            // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
            // 否则会出现乱码
            Template template = config.getTemplate(templateName, CharacterEncodings.UTF_8);
            // 合并数据模型与模板
            template.process(registerCustomMethod(root), out);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从classes下读取模板
     * @param templateName 模板名称，相对classes下路径
     * @param fileName 保存的文件路径
     * @param root 解析模板需要的参数
     */
    public static void parseClassTemplate2File(String templateName, String fileName, Map<String, Object> root) {
        try (Writer out = new OutputStreamWriter(new FileOutputStream(fileName), CharacterEncodings.UTF_8)) {
            Configuration config = new Configuration();
            // 设置要解析的模板所在的目录，并加载模板文件
            config.setClassForTemplateLoading(FreemarkerUtil.class, "/");
            // 设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapper());

            // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
            // 否则会出现乱码
            Template template = config.getTemplate(templateName, CharacterEncodings.UTF_8);
            // 合并数据模型与模板
            template.process(registerCustomMethod(root), out);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> registerCustomMethod(Map<String, Object> root) {
        Map<String, Object> model = new HashMap<>();
        if (!CollectionUtils.isEmpty(root)) {
            model.putAll(root);
        }

        model.put("listGet", new ListGetMethod());
        return model;
    }
}
