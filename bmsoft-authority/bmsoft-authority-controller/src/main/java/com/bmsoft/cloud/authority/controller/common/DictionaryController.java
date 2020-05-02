package com.bmsoft.cloud.authority.controller.common;


import com.bmsoft.cloud.authority.dto.common.DictionarySaveDTO;
import com.bmsoft.cloud.authority.dto.common.DictionaryUpdateDTO;
import com.bmsoft.cloud.authority.entity.common.Dictionary;
import com.bmsoft.cloud.authority.entity.common.DictionaryItem;
import com.bmsoft.cloud.authority.service.common.DictionaryItemService;
import com.bmsoft.cloud.authority.service.common.DictionaryService;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperController;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * 字典类型
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dictionary")
@Api(value = "Dictionary", tags = "字典类型")
@PreAuth(replace = "dict:")
public class DictionaryController extends SuperController<DictionaryService, Long, Dictionary, Dictionary, DictionarySaveDTO, DictionaryUpdateDTO> {

    @Autowired
    private DictionaryItemService dictionaryItemService;

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        this.baseService.removeByIds(ids);
        this.dictionaryItemService.remove(Wraps.<DictionaryItem>lbQ().in(DictionaryItem::getDictionaryId, ids));
        return this.success(true);
    }
}
