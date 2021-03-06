package com.bmsoft.cloud.oauth.controller;

import com.bmsoft.cloud.authority.dto.auth.ResourceQueryDTO;
import com.bmsoft.cloud.authority.entity.auth.Resource;
import com.bmsoft.cloud.authority.service.auth.ResourceService;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.security.annotation.LoginUser;
import com.bmsoft.cloud.security.model.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * 资源
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-22
 */
@Slf4j
@RestController
@RequestMapping("/resource")
@Api(value = "Resource", tags = "资源")
public class OauthResourceController {
    @Autowired
    private ResourceService resourceService;


    /**
     * 查询用户可用的所有资源
     *
     * @param resource <br>
     *                 menuId 菜单 <br>
     *                 userId 当前登录人id
     * @return
     */
    @ApiOperation(value = "查询用户可用的所有资源", notes = "查询用户可用的所有资源")
    @GetMapping("/visible")
    public R<List<String>> visible(ResourceQueryDTO resource, @ApiIgnore @LoginUser SysUser sysUser) {
        if (resource == null) {
            resource = new ResourceQueryDTO();
        }

        if (resource.getUserId() == null) {
            resource.setUserId(sysUser.getId());
        }
        List<Resource> list = resourceService.findVisibleResource(resource);
        return R.success(list.stream().filter(Objects::nonNull).map(Resource::getCode).collect(Collectors.toList()));
    }


}
