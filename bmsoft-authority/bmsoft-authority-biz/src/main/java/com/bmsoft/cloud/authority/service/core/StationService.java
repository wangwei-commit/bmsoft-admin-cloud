package com.bmsoft.cloud.authority.service.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.authority.dto.core.StationPageDTO;
import com.bmsoft.cloud.authority.entity.core.Station;
import com.bmsoft.cloud.base.service.SuperCacheService;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口
 * 岗位
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-22
 */
public interface StationService extends SuperCacheService<Station> {
    /**
     * 按权限查询岗位的分页信息
     *
     * @param page
     * @param data
     * @return
     */
    IPage<Station> findStationPage(IPage page, StationPageDTO data);

    /**
     * 根据id 查询
     *
     * @param ids
     * @return
     */
    Map<Serializable, Object> findStationByIds(Set<Serializable> ids);

    /**
     * 根据id 查询 岗位名称
     *
     * @param ids
     * @return
     */
    Map<Serializable, Object> findStationNameByIds(Set<Serializable> ids);
}
