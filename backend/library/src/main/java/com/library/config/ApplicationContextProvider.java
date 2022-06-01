package com.library.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
        ApplicationContextProvider.ctx = ctx;
    }

    public static <T> T getBean(Class<T> returnType) {
        return getApplicationContext().getBean(returnType);
    }

    public static void autowire(Object classToAutowire, Object... beansToAutowireInClass) {
        for (Object bean : beansToAutowireInClass) {
            if (bean == null) {
                ctx.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
            }
        }
    }

}
