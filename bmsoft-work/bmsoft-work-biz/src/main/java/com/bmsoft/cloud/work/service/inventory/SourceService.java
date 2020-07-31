package com.bmsoft.cloud.work.service.inventory;

import java.util.List;

import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.inventory.Source;
import com.bmsoft.cloud.work.properties.TypeProperties.Type;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;

/**
 * <p>
 * 业务接口 清单源
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
public interface SourceService extends SuperCacheService<Source> {

	List<Type> getSourceTypeList();
	
	List<TypeField> getTypeFieldByType(String type);
}
