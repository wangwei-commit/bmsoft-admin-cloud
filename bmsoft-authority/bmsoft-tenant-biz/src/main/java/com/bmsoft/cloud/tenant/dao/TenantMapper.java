package com.bmsoft.cloud.tenant.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.bmsoft.cloud.base.mapper.SuperMapper;
import com.bmsoft.cloud.tenant.entity.Tenant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 企业
 * </p>
 *
 * @author bmsoft
 * @date 2019-10-25
 */
@Repository
@SqlParser(filter = true)
public interface TenantMapper extends SuperMapper<Tenant> {

    /**
     * 根据租户编码查询
     *
     * @param code 租户编码
     * @return
     */
    Tenant getByCode(@Param("code") String code);
}
