package com.bmsoft.cloud.work.entity.inventory;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bmsoft.cloud.base.entity.SuperEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类 清单组与主机关系
 * </p>
 *
 * @author bmsoft
 * @since 2020-07-24
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("inventory_group_host")
@ApiModel(value = "GroupHost", description = "清单组与主机关系")
@AllArgsConstructor
public class GroupHost extends SuperEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 组ID
	 */
	@ApiModelProperty(value = "组ID")
	@NotNull(message = "组ID不能为空")
	@TableField("group_id")
	@Excel(name = "组ID")
	private Long groupId;

	/**
	 * 主机ID
	 */
	@ApiModelProperty(value = "主机ID")
	@NotNull(message = "主机ID不能为空")
	@TableField("host_id")
	@Excel(name = "主机ID")
	private Long hostId;

	@Builder
	public GroupHost(Long id, LocalDateTime createTime, Long createUser, Long groupId, Long hostId) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.groupId = groupId;
		this.hostId = hostId;
	}

}
