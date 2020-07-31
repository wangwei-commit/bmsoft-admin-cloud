package com.bmsoft.cloud.work.controller.inventory;

import static com.bmsoft.cloud.work.util.VariableUtil.VARIABLE_ERROR_CODE;
import static com.bmsoft.cloud.work.util.VariableUtil.VARIABLE_ERROR_MESSAGE;
import static com.bmsoft.cloud.work.util.VariableUtil.vaildVariable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.model.RemoteData;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.dto.inventory.GroupPageDTO;
import com.bmsoft.cloud.work.dto.inventory.GroupSaveDTO;
import com.bmsoft.cloud.work.dto.inventory.GroupUpdateDTO;
import com.bmsoft.cloud.work.entity.inventory.Group;
import com.bmsoft.cloud.work.entity.inventory.GroupHost;
import com.bmsoft.cloud.work.entity.inventory.GroupParent;
import com.bmsoft.cloud.work.service.inventory.GroupHostService;
import com.bmsoft.cloud.work.service.inventory.GroupParentService;
import com.bmsoft.cloud.work.service.inventory.GroupService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器 清单组
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Validated
@RestController
@RequestMapping("/group")
@Api(value = "Group", tags = "清单组")
//@PreAuth(replace = "group:")
public class GroupController
		extends SuperCacheController<GroupService, Long, Group, GroupPageDTO, GroupSaveDTO, GroupUpdateDTO> {

	@Resource
	private GroupParentService groupParentService;

	@Resource
	private GroupHostService groupHostService;

	@Override
	public R<Group> save(GroupSaveDTO saveDTO) {
		R<Group> result = super.save(saveDTO);
		if (result.getIsSuccess() && saveDTO.getParent() != null) {
			GroupParent groupParent = GroupParent.builder().toId(saveDTO.getParent()).fromId(result.getData().getId())
					.build();
			groupParentService.save(groupParent);
		}
		return result;
	}

	@Override
	public R<Group> handlerSave(GroupSaveDTO model) {
		if (!vaildVariable(model.getVariableType(), model.getVariableValue())) {
			return R.fail(VARIABLE_ERROR_CODE, VARIABLE_ERROR_MESSAGE);
		}
		return super.handlerSave(model);
	}

	@Override
	public R<Group> handlerUpdate(GroupUpdateDTO model) {
		if (!vaildVariable(model.getVariableType(), model.getVariableValue())) {
			return R.fail(VARIABLE_ERROR_CODE, VARIABLE_ERROR_MESSAGE);
		}
		return super.handlerUpdate(model);
	}

	@Override
	public void query(PageParams<GroupPageDTO> params, IPage<Group> page, Long defSize) {
		if (params.getModel().getInventory() != null && params.getModel().getInventory().getKey() != null) {
			super.query(params, page, defSize);
		}
	}

	@Override
	public void handlerWrapper(QueryWrap<Group> wrapper, PageParams<GroupPageDTO> params) {
		if (CollUtil.isNotEmpty(params.getMap())) {
			Map<String, String> map = params.getMap();
			if (map.containsKey("parent") && NumberUtil.isLong(map.get("parent"))) {
				Long parentId = Long.parseLong(map.get("parent"));
				List<Long> childs = groupParentService
						.list(Wraps.lbQ(GroupParent.builder().build()).eq(GroupParent::getToId, parentId)).stream()
						.map(parent -> parent.getFromId()).collect(Collectors.toList());
				if (childs.isEmpty()) {
					wrapper.eq("id", -1L);
				} else {
					wrapper.in("id", childs);
				}
			}
		}
		super.handlerWrapper(wrapper, params);
	}

	@ApiOperation(value = "新增子组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "inventoryId", value = "清单id", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "toId", value = "父组id", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "fromIds[]", value = "子组id", dataType = "array", paramType = "query", required = true), })
	@PostMapping("/child")
	@SysLog("'新增子组:' + #formIds + ',父组:' + #toId + ',清单:' + #inventoryId")
	@PreAuth("hasPermit('{}addChild')")
	public R<Boolean> addChild(@RequestParam("inventoryId") Long inventoryId, @RequestParam("toId") Long toId,
			@RequestParam("fromIds[]") List<Long> fromIds) {
		List<Group> groupList = baseService
				.list(Wraps.lbQ(Group.builder().build()).eq(Group::getInventory, new RemoteData<>(toId)));
		List<Long> groupIds = groupList.stream().map(group -> group.getId()).collect(Collectors.toList());
		List<GroupParent> parentList = groupParentService
				.list(Wraps.lbQ(GroupParent.builder().build()).in(GroupParent::getFromId, groupIds));
		List<Long> addFromIds = fromIds.stream()
				.filter(fromId -> !parentList.stream()
						.anyMatch(parent -> parent.getToId().equals(toId) && parent.getFromId().equals(fromId)))
				.collect(Collectors.toList());
		if (addFromIds.stream().anyMatch(fromId -> isCyclical(toId, fromId, parentList))) {
			return R.fail(400, "有循环节点");
		}
		List<GroupParent> addParentList = addFromIds.stream()
				.map(fromId -> GroupParent.builder().fromId(fromId).toId(toId).build()).collect(Collectors.toList());
		return success(groupParentService.saveBatchSomeColumn(addParentList));
	}

	@ApiOperation(value = "删除子组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "toId", value = "父组id", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "fromIds[]", value = "子组id", dataType = "array", paramType = "query", required = true), })
	@DeleteMapping("/child")
	@SysLog("'删除新增子组:' + #formIds + ',父组:' + #toId")
	@PreAuth("hasPermit('{}deleteChild')")
	public R<Boolean> deleteChild(@RequestParam("toId") Long toId, @RequestParam("fromIds[]") List<Long> fromIds) {
		return success(groupParentService.removeFromByTo(toId, fromIds));
	}

	@ApiOperation(value = "新增主机")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "组id", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "hostIds[]", value = "主机id", dataType = "array", paramType = "query", required = true), })
	@PostMapping("/host")
	@SysLog("'新增主机:' + #hostIds + ',组:' + #groupId")
	@PreAuth("hasPermit('{}addHost')")
	public R<Boolean> addHost(@RequestParam("groupId") Long groupId, @RequestParam("hostIds[]") List<Long> hostIds) {
		List<Long> groupHostIds = groupHostService
				.list(Wraps.lbQ(GroupHost.builder().build()).eq(GroupHost::getGroupId, groupId)).stream()
				.map(groupHost -> groupHost.getHostId()).collect(Collectors.toList());
		List<GroupHost> addList = hostIds.stream().filter(hostId -> !groupHostIds.contains(hostId))
				.map(hostId -> GroupHost.builder().hostId(hostId).groupId(groupId).build())
				.collect(Collectors.toList());
		return success(groupHostService.saveBatchSomeColumn(addList));
	}

	@ApiOperation(value = "删除主机")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "组id", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "hostIds[]", value = "主机id", dataType = "array", paramType = "query", required = true), })
	@DeleteMapping("/host")
	@SysLog("'删除主机:' + #hostIds + ',组:' + #groupId")
	@PreAuth("hasPermit('{}deleteHost')")
	public R<Boolean> deleteHost(@RequestParam("groupId") Long groupId, @RequestParam("hostIds[]") List<Long> hostIds) {
		return success(groupHostService.removeHostByGroup(groupId, hostIds));
	}

	private boolean isCyclical(Long toId, Long fromId, List<GroupParent> list) {
		return list.stream()
				.anyMatch(parent -> parent.getToId().equals(fromId)
						? (parent.getFromId().equals(toId) ? true : isCyclical(toId, parent.getFromId(), list))
						: false);
	}

	/**
	 * Excel导入后的操作
	 *
	 * @param list
	 */
	@Override
	public R<Boolean> handlerImport(List<Map<String, String>> list) {
		List<Group> groupList = list.stream().map((map) -> {
			Group group = Group.builder().build();
			// TODO 请在这里完成转换
			return group;
		}).collect(Collectors.toList());

		return R.success(baseService.saveBatch(groupList));
	}
}
