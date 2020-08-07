package com.bmsoft.cloud.work.service.inventory;

import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.inventory.Group;

/**
 * <p>
 * 业务接口 清单组
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
public interface GroupService extends SuperCacheService<Group> {

	boolean saveAndParent(Group group, Long parent);
}
