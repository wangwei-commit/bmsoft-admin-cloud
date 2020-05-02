package com.bmsoft.cloud.authority.service.auth.impl;


import com.bmsoft.cloud.authority.dao.auth.ApplicationMapper;
import com.bmsoft.cloud.authority.entity.auth.Application;
import com.bmsoft.cloud.authority.service.auth.ApplicationService;
import com.bmsoft.cloud.base.service.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 应用
 * </p>
 *
 * @author bmsoft
 * @date 2019-12-15
 */
@Slf4j
@Service

public class ApplicationServiceImpl extends SuperServiceImpl<ApplicationMapper, Application> implements ApplicationService {

}
