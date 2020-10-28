package com.bmsoft.cloud.work.service.script;

import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.properties.TypeProperties;

import java.util.List;

/**
 * <p>
 * 业务接口 脚本
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
public interface ScenariosService extends SuperCacheService<Scenarios> {

    List<TypeProperties.PlateForm> getTypeList();

}
