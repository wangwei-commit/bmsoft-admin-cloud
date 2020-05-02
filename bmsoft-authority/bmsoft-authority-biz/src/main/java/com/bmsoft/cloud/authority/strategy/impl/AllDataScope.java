package com.bmsoft.cloud.authority.strategy.impl;

import com.bmsoft.cloud.authority.entity.core.Org;
import com.bmsoft.cloud.authority.service.core.OrgService;
import com.bmsoft.cloud.authority.strategy.AbstractDataScopeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 所有
 *
 * @author bmsoft
 * @version 1.0
 * @date 2019-06-08 16:27
 */
@Component("ALL")
public class AllDataScope implements AbstractDataScopeHandler {

    @Autowired
    private OrgService orgService;

    @Override
    public List<Long> getOrgIds(List<Long> orgList, Long userId) {
        List<Org> list = orgService.lambdaQuery().select(Org::getId).list();
        return list.stream().map(Org::getId).collect(Collectors.toList());
    }


}
