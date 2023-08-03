package com.project.app.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

public class TagValuePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> classType = bean.getClass();
        List<Field> classFields = List.of(classType.getDeclaredFields());
        for(var classField : classFields){
            if(classField.isAnnotationPresent(TagValue.class)){
                TagValue tagValue = classField.getAnnotation(TagValue.class);
                classField.setAccessible(true);
                try {
                    classField.set(bean, reshape((String)classField.get(bean)));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bean;
    }

    public String reshape(String value){
        return value.toLowerCase();
    }
}
