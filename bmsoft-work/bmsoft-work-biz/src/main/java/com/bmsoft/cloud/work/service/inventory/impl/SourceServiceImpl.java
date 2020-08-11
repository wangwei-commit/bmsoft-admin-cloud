package com.bmsoft.cloud.work.service.inventory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.work.dao.inventory.SourceMapper;
import com.bmsoft.cloud.work.entity.inventory.Source;
import com.bmsoft.cloud.work.properties.TypeProperties;
import com.bmsoft.cloud.work.properties.TypeProperties.Type;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;
import com.bmsoft.cloud.work.service.inventory.SourceService;

/**
 * <p>
 * 业务实现类 清单源
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class SourceServiceImpl extends SuperCacheServiceImpl<SourceMapper, Source> implements SourceService {

	@Resource
	private TypeProperties typeProperties;

	@Override
	protected String getRegion() {
		return CacheKey.INVENTORY_SOURCE;
	}

	@Override
	public List<Type> getSourceTypeList() {
		return typeProperties.getInventorySource();
	}

	@Override
	public List<TypeField> getTypeFieldByType(String type) {
		return getSourceTypeList().stream().filter(sourceType -> sourceType.getKey().equals(type))
				.findFirst().map(Type::getFields).orElse(null);
	}
}
