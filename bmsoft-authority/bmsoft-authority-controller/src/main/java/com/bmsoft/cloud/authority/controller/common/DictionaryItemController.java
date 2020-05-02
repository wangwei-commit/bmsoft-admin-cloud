package com.bmsoft.cloud.authority.controller.common;


import com.bmsoft.cloud.authority.dto.common.DictionaryItemSaveDTO;
import com.bmsoft.cloud.authority.dto.common.DictionaryItemUpdateDTO;
import com.bmsoft.cloud.authority.entity.common.DictionaryItem;
import com.bmsoft.cloud.authority.service.common.DictionaryItemService;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 字典项
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dictionaryItem")
@Api(value = "DictionaryItem", tags = "字典项")
@PreAuth(replace = "dict:")
public class DictionaryItemController extends SuperCacheController<DictionaryItemService, Long, DictionaryItem, DictionaryItem, DictionaryItemSaveDTO, DictionaryItemUpdateDTO> {
    @Override
    public void handlerWrapper(QueryWrap<DictionaryItem> wrapper, PageParams<DictionaryItem> params) {
        super.handlerWrapper(wrapper, params);
        DictionaryItem model = params.getModel();
        wrapper.lambda().ignore(DictionaryItem::setDictionaryType)
                .eq(DictionaryItem::getDictionaryType, model.getDictionaryType());
    }

}
