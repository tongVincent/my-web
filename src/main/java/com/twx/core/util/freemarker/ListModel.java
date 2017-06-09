package com.twx.core.util.freemarker;

import com.twx.core.util.Convert;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author vincent.tong on 2017/4/22
 */
public class ListModel implements TemplateHashModel {

    private final List<Object> model = new ArrayList<>();

    public ListModel(Collection<Object> c) {
        if (!CollectionUtils.isEmpty(c)) {
            model.addAll(c);
        }
    }

    public ListModel(Object[] array) {
        if (array != null && array.length != 0) {
            model.addAll(Arrays.asList(array));
        }
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        Object bean = null;

        int index = Convert.toInt(Objects.toString(key), -1);
        if (index > -1 && index < model.size()) {
            bean = model.get(index);
        }

        return new BeanModel(bean, new BeansWrapper());
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {
        return model.isEmpty();
    }
}
