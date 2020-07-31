package com.bmsoft.cloud.work.service.inventory;

import java.io.Serializable;
import java.util.Collection;

import com.bmsoft.cloud.base.service.SuperService;
import com.bmsoft.cloud.work.entity.inventory.GroupHost;

/**
 * <p>
 * 业务接口 清单组与主机关系
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
public interface GroupHostService extends SuperService<GroupHost> {

	boolean removeByHost(Collection<? extends Serializable> idList);
	
	boolean removeByGroup(Collection<? extends Serializable> idList);
	
	boolean removeGroupByHost(Serializable hostId, Collection<? extends Serializable> groupList);
	
	boolean removeHostByGroup(Serializable groupId, Collection<? extends Serializable> hostList);
	
}
