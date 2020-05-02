package com.bmsoft.cloud.authority.service.auth;

import com.bmsoft.cloud.authority.dto.auth.SystemApiScanSaveDTO;
import com.bmsoft.cloud.authority.entity.auth.SystemApi;
import com.bmsoft.cloud.base.service.SuperCacheService;

/**
 * <p>
 * 业务接口
 * API接口
 * </p>
 *
 * @author bmsoft
 * @date 2019-12-15
 */
public interface SystemApiService extends SuperCacheService<SystemApi> {
    /**
     * 批量保存
     *
     * @param data
     * @return
     */
    Boolean batchSave(SystemApiScanSaveDTO data);
}
