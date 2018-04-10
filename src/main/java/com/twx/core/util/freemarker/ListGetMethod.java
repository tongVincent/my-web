package com.twx.core.util.freemarker;

import com.twx.core.util.Convert;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * TemplateMethodModel的方法参数只能是字符串、数字等简单类型。所以参数不能传List
 * <p>
 * TemplateMethodModelEx的方法可以是任意的TemplateModel类型。
 *
 * 此类，用来实现安全地去list里的东西
 *
 * @author vincent.tong on 2017/4/22
 */
public class ListGetMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (CollectionUtils.isEmpty(arguments)) {
            return null;
        }

        TemplateSequenceModel list = toList(arguments.get(0));
        if (list.size() == 0) {
            return null;
        }

        int index = 0;
        if (arguments.size() > 1) {
            index = Convert.toInt(Objects.toString(arguments.get(1)), -1);
        }

        if (index < 0 || index >= list.size()) {
            return null;
        }

        return list.get(index);
    }

    private TemplateSequenceModel toList(Object obj) {
        if (obj instanceof TemplateSequenceModel) {
            return (TemplateSequenceModel) obj;
        }

        return new TemplateSequenceModel() {
            private DefaultObjectWrapper objectWrapper = new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

            @Override
            public TemplateModel get(int index) throws TemplateModelException {
                return obj instanceof TemplateModel ? (TemplateModel) obj : objectWrapper.wrap(obj);
            }

            @Override
            public int size() throws TemplateModelException {
                return obj == null ? 0 : 1;
            }
        };
    }


}
