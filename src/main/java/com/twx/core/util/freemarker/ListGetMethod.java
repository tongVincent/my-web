package com.twx.core.util.freemarker;

import com.twx.core.util.Convert;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 此方法无效
 * 想法不对，freemarker方法参数只能是字符串、数字等简单类型。
 * 所以参数不能传List
 * @author vincent.tong on 2017/4/22
 */
@Deprecated
public class ListGetMethod implements TemplateMethodModel {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (CollectionUtils.isEmpty(arguments)) {
            return null;
        }

        List<Object> list = toList(arguments.get(0));
        if (list.isEmpty()) {
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

    private List<Object> toList(Object obj) {
        if (obj == null) {
            return Collections.emptyList();
        }

        if (obj instanceof Collection) {
            return new ArrayList<>((Collection<Object>) obj);
        }

        if (obj.getClass().isArray()) {
            return Arrays.asList((Object[]) obj);
        }

        return Collections.singletonList(obj);
    }


}
