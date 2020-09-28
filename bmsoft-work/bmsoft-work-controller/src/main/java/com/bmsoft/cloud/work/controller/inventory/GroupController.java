package com.bmsoft.cloud.work.controller.inventory;

import static com.bmsoft.cloud.work.util.VariableUtil.handler;
import static com.bmsoft.cloud.common.constant.CacheKey.GROUP_LOCK;
import static com.bmsoft.cloud.common.constant.CacheKey.GROUP_HOST_LOCK;

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
import com.bmsoft.cloud.lock.DistributedLock;
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

import cn.hutool.core.bean.BeanUtil;
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

	@Resource
	private DistributedLock distributedLock;

	@Override
	public R<Group> save(GroupSaveDTO saveDTO) {
		R<Group> result = handlerSave(saveDTO);
		if (result.getDefExec()) {
			Group group = BeanUtil.toBean(saveDTO, getEntityClass());
			baseService.saveAndParent(group, saveDTO.getParent());
			result.setData(group);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Group> handlerSave(GroupSaveDTO model) {
		return handler(model.getVariableType(), model.getVariableValue(), model,
				dto -> super.handlerSave((GroupSaveDTO) dto));
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Group> handlerUpdate(GroupUpdateDTO model) {
		return handler(model.getVariableType(), model.getVariableValue(), model,
				dto -> super.handlerUpdate((GroupUpdateDTO) dto));
	}

	@Override
	public void query(PageParams<GroupPageDTO> params, IPage<Group> page, Long defSize) {
		if (params.getModel().getInventory() != null && params.getModel().getInventory().getKey() != null) {
			super.query(params, page, defSize);
		}
	}

	@Override
	public void handlerWrapper(QueryWrap<Group> wrapper, PageParams<GroupPageDTO> params) {
		if (params.getModel().getParent() != null) {
			List<Long> childs = groupParentService
					.list(Wraps.lbQ(GroupParent.builder().build()).eq(GroupParent::getToId,
							params.getModel().getParent()))
					.stream().map(GroupParent::getFromId).collect(Collectors.toList());
			if (childs.isEmpty()) {
				wrapper.eq("id", -1L);
			} else {
				wrapper.in("id", childs);
			}
		}
		if (params.getModel().getIgnoreIds() != null) {
			wrapper.notIn("id", params.getModel().getIgnoreIds());
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
		distributedLock.lock(GROUP_LOCK + inventoryId);
		List<Group> groupList = baseService
				.list(Wraps.lbQ(Group.builder().build()).eq(Group::getInventory, new RemoteData<>(toId)));
		List<Long> groupIds = groupList.stream().map(Group::getId).collect(Collectors.toList());
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
		R<Boolean> result = success(groupParentService.saveBatchSomeColumn(addParentList));
		distributedLock.releaseLock(GROUP_LOCK + inventoryId);
		return result;
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
		distributedLock.lock(GROUP_HOST_LOCK);
		List<Long> groupHostIds = groupHostService
				.list(Wraps.lbQ(GroupHost.builder().build()).eq(GroupHost::getGroupId, groupId)).stream()
				.map(GroupHost::getHostId).collect(Collectors.toList());
		List<GroupHost> addList = hostIds.stream().filter(hostId -> !groupHostIds.contains(hostId))
				.map(hostId -> GroupHost.builder().hostId(hostId).groupId(groupId).build())
				.collect(Collectors.toList());
		R<Boolean> result = success(groupHostService.saveBatchSomeColumn(addList));
		distributedLock.releaseLock(GROUP_HOST_LOCK);
		return result;
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
