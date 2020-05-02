package com.bmsoft.cloud.tenant.service.impl;

import cn.hutool.core.convert.Convert;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.tenant.dao.TenantMapper;
import com.bmsoft.cloud.tenant.dto.TenantSaveDTO;
import com.bmsoft.cloud.tenant.entity.Tenant;
import com.bmsoft.cloud.tenant.enumeration.TenantStatusEnum;
import com.bmsoft.cloud.tenant.enumeration.TenantTypeEnum;
import com.bmsoft.cloud.tenant.service.TenantService;
import com.bmsoft.cloud.tenant.strategy.InitSystemContext;
import com.bmsoft.cloud.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bmsoft.cloud.common.constant.CacheKey.TENANT;
import static com.bmsoft.cloud.utils.BizAssert.isFalse;

/**
 * <p>
 * 业务实现类
 * 企业
 * </p>
 *
 * @author bmsoft
 * @date 2019-10-24
 */
@Slf4j
@Service
public class TenantServiceImpl extends SuperCacheServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Autowired
    private InitSystemContext initSystemContext;

    @Override
    protected String getRegion() {
        return TENANT;
    }

    @Override
    public Tenant getByCode(String tenant) {
        return super.getOne(Wraps.<Tenant>lbQ().eq(Tenant::getCode, tenant));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tenant save(TenantSaveDTO data) {
        // defaults 库
        isFalse(check(data.getCode()), "编码重复，请重新输入");

        // 1， 保存租户 (默认库)
        Tenant tenant = BeanPlusUtil.toBean(data, Tenant.class);
        tenant.setStatus(TenantStatusEnum.NORMAL);
        tenant.setType(TenantTypeEnum.CREATE);
        // defaults 库
        save(tenant);

        // 3, 初始化库，表, 数据  考虑异步完成 // 租户库
        initSystemContext.init(tenant.getCode());
        return tenant;
    }

    @Override
    public boolean check(String tenantCode) {
        return super.count(Wraps.<Tenant>lbQ().eq(Tenant::getCode, tenantCode)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> ids) {
        List<String> tenantCodeList = listObjs(Wraps.<Tenant>lbQ().select(Tenant::getCode).in(Tenant::getId, ids), Convert::toStr);
        if (tenantCodeList.isEmpty()) {
            return true;
        }
        removeByIds(ids);

        return initSystemContext.delete(tenantCodeList);
    }
}
