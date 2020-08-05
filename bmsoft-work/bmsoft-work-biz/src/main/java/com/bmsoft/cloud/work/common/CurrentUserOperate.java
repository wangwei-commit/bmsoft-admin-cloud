package com.bmsoft.cloud.work.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bmsoft.cloud.context.BaseContextHandler;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.oauth.api.UserApi;

@Component
public class CurrentUserOperate {

	@Resource
	private UserApi userApi;

	public void setQueryWrapByOrg(QueryWrap<?> queryWrap, String orgField) {
		Long currenUserId = BaseContextHandler.getUserId();
		queryWrap.in(orgField, userApi.getUserOrgIdChildById(currenUserId));
	}
}
