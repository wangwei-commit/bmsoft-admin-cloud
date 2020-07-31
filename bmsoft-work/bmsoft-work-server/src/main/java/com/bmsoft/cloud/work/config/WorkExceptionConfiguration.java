package com.bmsoft.cloud.work.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bmsoft.cloud.boot.handler.DefaultGlobalExceptionHandler;

/**
 * 作业管理服务-全局异常处理
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Configuration
@ControllerAdvice(annotations = { RestController.class, Controller.class })
@ResponseBody
public class WorkExceptionConfiguration extends DefaultGlobalExceptionHandler {
}
