package com.gene.base.api.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextUtil implements ApplicationContextAware {

    /**
     * This class contains generic implementation for inject beans in unidentified components
     * of springboot application
     */

    private ApplicationContext applicationContext;

    public ContextUtil() {
    }

    public void setApplicationContext(ApplicationContext app) throws BeansException {
        applicationContext = app;
    }

    public <T> T getBean(Class<T> t) {
        return applicationContext.getBean(t);
    }

    public Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public <T> T getBean(String name, Class<T> t) {
        return applicationContext.getBean(name, t);
    }

}