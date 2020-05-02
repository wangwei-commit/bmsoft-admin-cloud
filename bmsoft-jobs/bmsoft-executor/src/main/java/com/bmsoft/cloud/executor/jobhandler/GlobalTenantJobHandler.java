package com.bmsoft.cloud.executor.jobhandler;

import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.tenant.entity.Tenant;
import com.bmsoft.cloud.tenant.enumeration.TenantStatusEnum;
import com.bmsoft.cloud.tenant.service.TenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public abstract class GlobalTenantJobHandler extends IJobHandler {

    @Autowired
    private TenantService tenantService;

    @Override
    public ReturnT<String> execute2(String param) throws Exception {
        //记录日志的方法推荐使用这个:XxlJobLogger.log ，因为这个记录的日志，可以在bmsoft-jobs-server管理后台查看
        XxlJobLogger.log("执行结果--->param={} ", param);

        LbqWrapper<Tenant> wrapper = Wraps.<Tenant>lbQ()
                .eq(Tenant::getStatus, TenantStatusEnum.NORMAL);

        List<Tenant> list = tenantService.list(wrapper);

        list.forEach((tenant) -> {
            executeBiz(tenant, param);
        });

        return SUCCESS;
    }

    public abstract ReturnT<String> executeBiz(Tenant tenant, String param);
}
