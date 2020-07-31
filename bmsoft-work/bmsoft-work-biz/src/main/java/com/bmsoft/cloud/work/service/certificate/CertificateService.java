package com.bmsoft.cloud.work.service.certificate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.certificate.Certificate;
import com.bmsoft.cloud.work.properties.TypeProperties.Type;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;

/**
 * <p>
 * 业务接口 凭证
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
public interface CertificateService extends SuperCacheService<Certificate> {

	Map<Serializable, Object> findNameByIds(Set<Serializable> ids);
	
	List<Type> getTypeList();
	
	List<TypeField> getTypeFieldByType(String type);
}
