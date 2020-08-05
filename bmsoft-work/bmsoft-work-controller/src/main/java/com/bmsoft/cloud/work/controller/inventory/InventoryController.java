package com.bmsoft.cloud.work.controller.inventory;

import static com.bmsoft.cloud.work.util.VariableUtil.VARIABLE_ERROR_CODE;
import static com.bmsoft.cloud.work.util.VariableUtil.VARIABLE_ERROR_MESSAGE;
import static com.bmsoft.cloud.work.util.VariableUtil.vaildVariable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.work.common.CurrentUserOperate;
import com.bmsoft.cloud.work.dto.inventory.InventoryPageDTO;
import com.bmsoft.cloud.work.dto.inventory.InventorySaveDTO;
import com.bmsoft.cloud.work.dto.inventory.InventoryUpdateDTO;
import com.bmsoft.cloud.work.entity.inventory.Inventory;
import com.bmsoft.cloud.work.service.inventory.InventoryService;

import io.swagger.annotations.Api;;

/**
 * <p>
 * 前端控制器 清单
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Validated
@RestController
@RequestMapping("/inventory")
@Api(value = "Inventory", tags = "清单")
//@PreAuth(replace = "inventory:")
public class InventoryController extends
		SuperCacheController<InventoryService, Long, Inventory, InventoryPageDTO, InventorySaveDTO, InventoryUpdateDTO> {

	@Resource
	private CurrentUserOperate currentUserOperate;

	@Override
	public R<Inventory> handlerSave(InventorySaveDTO model) {
		if (!vaildVariable(model.getVariableType(), model.getVariableValue())) {
			return R.fail(VARIABLE_ERROR_CODE, VARIABLE_ERROR_MESSAGE);
		}
		return super.handlerSave(model);
	}

	@Override
	public R<Inventory> handlerUpdate(InventoryUpdateDTO model) {
		if (!vaildVariable(model.getVariableType(), model.getVariableValue())) {
			return R.fail(VARIABLE_ERROR_CODE, VARIABLE_ERROR_MESSAGE);
		}
		return super.handlerUpdate(model);
	}

	@Override
	public void handlerWrapper(QueryWrap<Inventory> wrapper, PageParams<InventoryPageDTO> params) {
		currentUserOperate.setQueryWrapByOrg(wrapper, getDbField("org", getEntityClass()));
		super.handlerWrapper(wrapper, params);
	}

	/**
	 * Excel导入后的操作
	 *
	 * @param list
	 */
	@Override
	public R<Boolean> handlerImport(List<Map<String, String>> list) {
		List<Inventory> inventoryList = list.stream().map((map) -> {
			Inventory inventory = Inventory.builder().build();
			// TODO 请在这里完成转换
			return inventory;
		}).collect(Collectors.toList());

		return R.success(baseService.saveBatch(inventoryList));
	}
}
