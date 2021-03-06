package com.bmsoft.cloud.authority.service.common.impl;

import cn.hutool.core.convert.Convert;
import com.bmsoft.cloud.authority.dao.common.AreaMapper;
import com.bmsoft.cloud.authority.entity.common.Area;
import com.bmsoft.cloud.authority.service.common.AreaService;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bmsoft.cloud.common.constant.CacheKey.AREA;

/**
 * <p>
 * 业务实现类
 * 地区表
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-02
 */
@Slf4j
@Service

public class AreaServiceImpl extends SuperCacheServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    protected String getRegion() {
        return AREA;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recursively(List<Long> ids) {
        boolean removeFlag = removeByIds(ids);
        delete(ids);
        return removeFlag;
    }

    private void delete(List<Long> ids) {
        // 查询子节点
        List<Long> childIds = super.listObjs(Wraps.<Area>lbQ().select(Area::getId).in(Area::getParentId, ids), Convert::toLong);
        if (!childIds.isEmpty()) {
            removeByIds(childIds);
            delete(childIds);
        }
        log.debug("退出地区数据递归");
    }
}
