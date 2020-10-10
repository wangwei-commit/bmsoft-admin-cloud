package com.bmsoft.cloud.work.controller.inventory;

import static com.bmsoft.cloud.work.util.VariableUtil.handler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.common.CurrentUserOperate;
import com.bmsoft.cloud.work.dto.inventory.InventoryPageDTO;
import com.bmsoft.cloud.work.dto.inventory.InventorySaveDTO;
import com.bmsoft.cloud.work.dto.inventory.InventoryUpdateDTO;
import com.bmsoft.cloud.work.entity.inventory.Inventory;
import com.bmsoft.cloud.work.service.certificate.CertificateService;
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
@PreAuth(replace = "inventory:")
public class InventoryController extends
		SuperCacheController<InventoryService, Long, Inventory, InventoryPageDTO, InventorySaveDTO, InventoryUpdateDTO> {

	@Resource
	private CurrentUserOperate currentUserOperate;

	@Resource
	private CertificateService certificateService;

	@SuppressWarnings("unchecked")
	@Override
	public R<Inventory> handlerSave(InventorySaveDTO model) {
		return handler(model.getVariableType(), model.getVariableValue(), model,
				dto -> super.handlerSave((InventorySaveDTO) dto));
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Inventory> handlerUpdate(InventoryUpdateDTO model) {
		return handler(model.getVariableType(), model.getVariableValue(), model,
				dto -> super.handlerUpdate((InventoryUpdateDTO) dto));
	}

	@Override
	public void handlerWrapper(QueryWrap<Inventory> wrapper, PageParams<InventoryPageDTO> params) {
//		currentUserOperate.setQueryWrapByOrg(wrapper, getDbField("org", getEntityClass()));
		super.handlerWrapper(wrapper, params);
	}

	@Override
	public void handlerResult(IPage<Inventory> page) {
		super.handlerResult(page);
		Set<Long> certificates = page.getRecords().stream()
				.flatMap(inventory -> Optional.ofNullable(inventory.getCertificates())
						.map(list -> list.stream().map(certificate -> certificate.getKey())).orElse(Stream.empty()))
				.collect(Collectors.toSet());
		Map<Long, String> map = certificateService.findNameByIds(certificates);
		page.getRecords().stream().forEach(inventory -> Optional.ofNullable(inventory.getCertificates()).map(list -> {
			list.stream().forEach(certificate -> certificate.setData(map.get(certificate.getKey())));
			return null;
		}));
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
