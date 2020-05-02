package com.bmsoft.cloud.tenant.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.bmsoft.cloud.base.mapper.SuperMapper;
import com.bmsoft.cloud.tenant.entity.GlobalUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 全局账号
 * </p>
 *
 * @author bmsoft
 * @date 2019-10-25
 */
@Repository
@SqlParser(filter = true)
public interface GlobalUserMapper extends SuperMapper<GlobalUser> {

}
