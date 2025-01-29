package com.jobrunr.config;

import com.jobrunr.task.filter.OrderFulfilmentTaskFilter;
import org.jobrunr.server.BackgroundJobServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BackgroundJobServerBeanPostProcessor implements BeanPostProcessor {

    private final OrderFulfilmentTaskFilter orderFulfilmentTaskFilter;

    public BackgroundJobServerBeanPostProcessor(OrderFulfilmentTaskFilter orderFulfilmentTaskFilter) {
        this.orderFulfilmentTaskFilter = orderFulfilmentTaskFilter;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof BackgroundJobServer backgroundJobServer) {
            backgroundJobServer.setJobFilters(Collections.singletonList(orderFulfilmentTaskFilter));
        }
        return bean;
    }

}
