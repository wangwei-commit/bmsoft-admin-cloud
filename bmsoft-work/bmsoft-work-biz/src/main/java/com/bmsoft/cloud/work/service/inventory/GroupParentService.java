package com.bmsoft.cloud.work.service.inventory;

import com.bmsoft.cloud.base.service.SuperService;
import com.bmsoft.cloud.work.entity.inventory.GroupParent;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 业务接口 清单组父子关系
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
public interface GroupParentService extends SuperService<GroupParent> {

	boolean removeByGroupIds(Collection<? extends Serializable> idList);

	boolean removeFromByTo(Serializable toId, Collection<? extends Serializable> fromList);
}
