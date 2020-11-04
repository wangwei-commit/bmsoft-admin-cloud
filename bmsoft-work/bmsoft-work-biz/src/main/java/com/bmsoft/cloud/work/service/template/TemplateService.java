package com.bmsoft.cloud.work.service.template;

import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.template.Template;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口 作业模板
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
public interface TemplateService extends SuperCacheService<Template> {


	/**
	 * 根据ID查找脚本或剧本
	 * @param ids
	 * @return
	 */
	Map<Serializable, Object> findNameByIds(Set<Serializable> ids);
}
