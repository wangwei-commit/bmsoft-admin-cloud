package com.bmsoft.cloud.authority.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import com.bmsoft.cloud.authority.dao.auth.SystemApiMapper;
import com.bmsoft.cloud.authority.dto.auth.SystemApiSaveDTO;
import com.bmsoft.cloud.authority.dto.auth.SystemApiScanSaveDTO;
import com.bmsoft.cloud.authority.entity.auth.SystemApi;
import com.bmsoft.cloud.authority.service.auth.SystemApiService;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bmsoft.cloud.common.constant.CacheKey.SYSTEM_API;

/**
 * <p>
 * 业务实现类
 * API接口
 * </p>
 *
 * @author bmsoft
 * @date 2019-12-15
 */
@Slf4j
@Service

public class SystemApiServiceImpl extends SuperCacheServiceImpl<SystemApiMapper, SystemApi> implements SystemApiService {
    @Override
    protected String getRegion() {
        return SYSTEM_API;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSave(SystemApiScanSaveDTO data) {
        List<SystemApiSaveDTO> list = data.getSystemApiList();
        if (CollUtil.isEmpty(list)) {
            return false;
        }

        list.forEach((dto) -> {
            try {
                SystemApi systemApi = BeanPlusUtil.toBean(dto, SystemApi.class);
                SystemApi save = this.getApi(dto.getCode());
                if (save == null) {
                    systemApi.setIsOpen(false);
                    systemApi.setIsPersist(true);
                    super.save(systemApi);
                } else {
                    systemApi.setId(save.getId());
                    super.updateById(systemApi);
                }
            } catch (Exception e) {
                log.warn("api初始化失败", e);
            }
        });

        return true;
    }

    public SystemApi getApi(String code) {
        LbqWrapper<SystemApi> wrapper = Wraps.<SystemApi>lbQ().eq(SystemApi::getCode, code);
        return baseMapper.selectOne(wrapper);
    }
}
