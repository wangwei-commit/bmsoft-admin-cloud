package com.bmsoft.cloud.authority.service.common;

import com.bmsoft.cloud.authority.entity.common.Area;
import com.bmsoft.cloud.base.service.SuperCacheService;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 地区表
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-02
 */
public interface AreaService extends SuperCacheService<Area> {

    /**
     * 递归删除
     *
     * @param ids
     * @return
     */
    boolean recursively(List<Long> ids);
}
