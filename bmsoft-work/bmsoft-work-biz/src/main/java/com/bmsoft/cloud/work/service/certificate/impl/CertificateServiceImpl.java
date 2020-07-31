package com.bmsoft.cloud.work.service.certificate.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.utils.MapHelper;
import com.bmsoft.cloud.work.dao.certificate.CertificateMapper;
import com.bmsoft.cloud.work.entity.certificate.Certificate;
import com.bmsoft.cloud.work.properties.TypeProperties;
import com.bmsoft.cloud.work.properties.TypeProperties.Type;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;
import com.bmsoft.cloud.work.service.certificate.CertificateService;

import cn.hutool.core.convert.Convert;

/**
 * <p>
 * 业务实现类 凭证
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class CertificateServiceImpl extends SuperCacheServiceImpl<CertificateMapper, Certificate>
		implements CertificateService {

	@Resource
	private TypeProperties typeProperties;

	@Override
	protected String getRegion() {
		return CacheKey.CERTIFICATE;
	}

	@Override
	@InjectionResult
	public <E extends IPage<Certificate>> E page(E page, Wrapper<Certificate> queryWrapper) {
		return super.page(page, queryWrapper);
	}

	@Override
	public Map<Serializable, Object> findNameByIds(Set<Serializable> ids) {
		List<Certificate> list = getCertificates(ids);
		return MapHelper.uniqueIndex(list, Certificate::getId, Certificate::getName);
	}

	private List<Certificate> getCertificates(Set<Serializable> ids) {
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<Long> idList = ids.stream().mapToLong(Convert::toLong).boxed().collect(Collectors.toList());
		List<Certificate> list = null;
		if (idList.size() <= 1000) {
			list = idList.stream().map(this::getByIdCache).filter(Objects::nonNull).collect(Collectors.toList());
		} else {
			LbqWrapper<Certificate> query = Wraps.<Certificate>lbQ().in(Certificate::getId, idList);
			list = super.list(query);
			if (!list.isEmpty()) {
				list.forEach(item -> {
					String itemKey = key(item.getId());
					cacheChannel.set(getRegion(), itemKey, item);
				});
			}
		}
		return list;
	}

	@Override
	public List<Type> getTypeList() {
		return typeProperties.getCertificateType();
	}

	@Override
	public List<TypeField> getTypeFieldByType(String type) {
		return getTypeList().stream().filter(certificateType -> certificateType.getKey().equals(type))
				.map(certificateType -> certificateType.getFields()).findFirst().orElse(null);
	}
}
