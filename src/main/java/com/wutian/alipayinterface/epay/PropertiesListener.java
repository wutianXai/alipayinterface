package com.wutian.alipayinterface.epay;

/**
 * PropertiesListener
 * 配置文件监听器，用来加载自定义配置文件
 *
 * @author ：wutian
 * @date ：Created in 2020/1/8 17:20
 */

import com.wutian.alipayinterface.epay.model.AlipayProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class PropertiesListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        AlipayProperties.loadProperties();
    }
}