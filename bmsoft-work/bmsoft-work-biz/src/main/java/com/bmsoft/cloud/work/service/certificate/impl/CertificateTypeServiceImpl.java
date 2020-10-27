package com.bmsoft.cloud.work.service.certificate.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.utils.MapHelper;
import com.bmsoft.cloud.work.dao.certificate.CertificateMapper;
import com.bmsoft.cloud.work.dao.certificate.CertificateTypeMapper;
import com.bmsoft.cloud.work.entity.certificate.Certificate;
import com.bmsoft.cloud.work.entity.certificate.CertificateType;
import com.bmsoft.cloud.work.properties.TypeProperties;
import com.bmsoft.cloud.work.properties.TypeProperties.Type;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;
import com.bmsoft.cloud.work.service.certificate.CertificateService;
import com.bmsoft.cloud.work.service.certificate.CertificateTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类 凭证
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class CertificateTypeServiceImpl extends SuperCacheServiceImpl<CertificateTypeMapper, CertificateType>
		implements CertificateTypeService {


	@Override
	protected String getRegion() {
		return CacheKey.CERTIFICATE;
	}

	@Override
	@InjectionResult
	public <E extends IPage<CertificateType>> E page(E page, Wrapper<CertificateType> queryWrapper) {
		return super.page(page, queryWrapper);
	}



}
