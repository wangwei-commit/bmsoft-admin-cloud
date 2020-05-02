package com.bmsoft.cloud.authority.service.common.impl;


import com.bmsoft.cloud.authority.dao.common.DictionaryMapper;
import com.bmsoft.cloud.authority.entity.common.Dictionary;
import com.bmsoft.cloud.authority.service.common.DictionaryService;
import com.bmsoft.cloud.base.service.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 字典类型
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-02
 */
@Slf4j
@Service

public class DictionaryServiceImpl extends SuperServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

}
