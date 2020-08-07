package com.bmsoft.cloud.work.service.inventory;

import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.inventory.Host;

/**
 * <p>
 * 业务接口 清单主机
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
public interface HostService extends SuperCacheService<Host> {

	boolean saveAndGroup(Host host, Long groupId);
}
