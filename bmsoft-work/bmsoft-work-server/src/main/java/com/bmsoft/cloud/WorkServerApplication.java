package com.bmsoft.cloud;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import com.bmsoft.cloud.security.annotation.EnableLoginArgResolver;
import com.bmsoft.cloud.validator.config.EnableFormValidator;
import com.bmsoft.cloud.work.properties.TypeProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 作业管理服务启动类
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@SpringBootApplication
@EnableDiscoveryClient
@Configuration
@EnableFeignClients(value = { "com.bmsoft.cloud" })
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableConfigurationProperties({ TypeProperties.class })
@Slf4j
@EnableLoginArgResolver
@EnableFormValidator
public class WorkServerApplication {
	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(WorkServerApplication.class, args);
		Environment env = application.getEnvironment();
		log.info(
				"\n----------------------------------------------------------\n\t" + "应用 '{}' 启动成功! 访问连接:\n\t"
						+ "Swagger文档: \t\thttp://{}:{}/doc.html\n\t" + "数据库监控: \t\thttp://{}:{}/druid\n"
						+ "----------------------------------------------------------",
				env.getProperty("spring.application.name"), InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"), "127.0.0.1", env.getProperty("server.port"));
	}
}
