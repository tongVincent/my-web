package com.twx.core.util.freemarker;

import com.twx.test.domain.People;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author vincent.tong on 2017/7/2
 */
public class ObjectMethod implements TemplateMethodModelEx {
    private People people = new People("vincent", 23);

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return people;
    }
}
