package com.demand.config;

import com.demand.svc.DemandBean;
import com.demand.svc.DemandBeanImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
@ConditionalOnClass(DemandBean.class)
public class ConfigAoto {
    @Bean
    @ConditionalOnMissingBean
    public DemandBean demandBean(){
        return new DemandBeanImpl();
    }
}
