package com.bmsoft.cloud.authority.service.common;

import com.bmsoft.cloud.authority.entity.common.Parameter;
import com.bmsoft.cloud.base.service.SuperService;

/**
 * <p>
 * 业务接口
 * 参数配置
 * </p>
 *
 * @author bmsoft
 * @date 2020-02-05
 */
public interface ParameterService extends SuperService<Parameter> {
    /**
     * 根据参数键查询参数值
     *
     * @param key    参数键
     * @param defVal 参数值
     * @return
     */
    String getValue(String key, String defVal);
}
