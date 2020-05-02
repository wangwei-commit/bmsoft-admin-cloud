package com.bmsoft.cloud.authority.dao.auth;

import com.bmsoft.cloud.authority.entity.auth.Menu;
import com.bmsoft.cloud.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 菜单
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-03
 */
@Repository
public interface MenuMapper extends SuperMapper<Menu> {

    /**
     * 查询用户可用菜单
     *
     * @param userId
     * @return
     */
    List<Menu> findVisibleMenu(@Param("userId") Long userId);
}
