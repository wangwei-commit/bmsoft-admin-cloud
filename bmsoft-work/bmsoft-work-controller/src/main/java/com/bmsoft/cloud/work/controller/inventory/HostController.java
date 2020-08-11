package com.bmsoft.cloud.work.controller.inventory;

import static com.bmsoft.cloud.common.constant.CacheKey.GROUP_HOST_LOCK;
import static com.bmsoft.cloud.work.util.VariableUtil.handler;

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

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.lock.DistributedLock;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.dto.inventory.HostPageDTO;
import com.bmsoft.cloud.work.dto.inventory.HostSaveDTO;
import com.bmsoft.cloud.work.dto.inventory.HostUpdateDTO;
import com.bmsoft.cloud.work.entity.inventory.GroupHost;
import com.bmsoft.cloud.work.entity.inventory.Host;
import com.bmsoft.cloud.work.service.inventory.GroupHostService;
import com.bmsoft.cloud.work.service.inventory.HostService;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器 清单主机
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Validated
@RestController
@RequestMapping("/host")
@Api(value = "Host", tags = "清单主机")
@PreAuth(replace = "host:")
public class HostController
		extends SuperCacheController<HostService, Long, Host, HostPageDTO, HostSaveDTO, HostUpdateDTO> {

	@Resource
	private GroupHostService groupHostService;

	@Resource
	private DistributedLock distributedLock;

	@Override
	public R<Host> save(HostSaveDTO saveDTO) {
		R<Host> result = handlerSave(saveDTO);
		if (result.getDefExec()) {
			Host host = BeanUtil.toBean(saveDTO, getEntityClass());
			baseService.saveAndGroup(host, saveDTO.getGroupId());
			result.setData(host);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Host> handlerSave(HostSaveDTO model) {
		return handler(model.getVariableType(), model.getVariableValue(), model,
				dto -> super.handlerSave((HostSaveDTO) dto));
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Host> handlerUpdate(HostUpdateDTO model) {
		return handler(model.getVariableType(), model.getVariableValue(), model,
				dto -> super.handlerUpdate((HostUpdateDTO) dto));
	}

	@Override
	public void handlerWrapper(QueryWrap<Host> wrapper, PageParams<HostPageDTO> params) {
		if (params.getModel().getGroup() != null) {
			List<Long> hostIds = groupHostService
					.list(Wraps.lbQ(GroupHost.builder().build()).eq(GroupHost::getGroupId,
							params.getModel().getGroup()))
					.stream().map(GroupHost::getHostId).collect(Collectors.toList());
			if (hostIds.isEmpty()) {
				wrapper.eq("id", -1L);
			} else {
				wrapper.in("id", hostIds);
			}
		}
		super.handlerWrapper(wrapper, params);
	}

	@ApiOperation(value = "新增组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "hostId", value = "主机id", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "groupIds[]", value = "组id", dataType = "array", paramType = "query", required = true), })
	@PostMapping("/group")
	@SysLog("'新增组:' + #groupIds + ',主机:' + #hostId")
	@PreAuth("hasPermit('{}addGroup')")
	public R<Boolean> addGroup(@RequestParam("hostId") Long hostId, @RequestParam("groupIds[]") List<Long> groupIds) {
		distributedLock.lock(GROUP_HOST_LOCK);
		List<Long> groupHostIds = groupHostService
				.list(Wraps.lbQ(GroupHost.builder().build()).eq(GroupHost::getHostId, hostId)).stream()
				.map(GroupHost::getGroupId).collect(Collectors.toList());
		List<GroupHost> addList = groupIds.stream().filter(groupId -> !groupHostIds.contains(groupId))
				.map(groupId -> GroupHost.builder().hostId(hostId).groupId(groupId).build())
				.collect(Collectors.toList());
		R<Boolean> result = success(groupHostService.saveBatchSomeColumn(addList));
		distributedLock.releaseLock(GROUP_HOST_LOCK);
		return result;
	}

	@ApiOperation(value = "删除组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "hostId", value = "主机id", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "groupIds[]", value = "组id", dataType = "array", paramType = "query", required = true), })
	@DeleteMapping("/group")
	@SysLog("'删除组:' + #groupIds + ',主机:' + #hostId")
	@PreAuth("hasPermit('{}deleteGroup')")
	public R<Boolean> deleteGroup(@RequestParam("hostId") Long hostId,
			@RequestParam("groupIds[]") List<Long> groupIds) {
		return success(groupHostService.removeGroupByHost(hostId, groupIds));
	}

	/**
	 * Excel导入后的操作
	 *
	 * @param list
	 */
	@Override
	public R<Boolean> handlerImport(List<Map<String, String>> list) {
		List<Host> hostList = list.stream().map((map) -> {
			Host host = Host.builder().build();
			// TODO 请在这里完成转换
			return host;
		}).collect(Collectors.toList());

		return R.success(baseService.saveBatch(hostList));
	}
}
