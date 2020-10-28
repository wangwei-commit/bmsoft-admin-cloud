package com.bmsoft.cloud.work.entity.inventory;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bmsoft.cloud.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类 清单组父子关系
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
@TableName("inventory_group_parent")
@ApiModel(value = "GroupParent", description = "清单组父子关系")
@AllArgsConstructor
public class GroupParent extends SuperEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 子点
	 */
	@ApiModelProperty(value = "子点")
	@NotNull(message = "子点不能为空")
	@TableField("from_id")
	@Excel(name = "子点")
	private Long fromId;

	/**
	 * 父点
	 */
	@ApiModelProperty(value = "父点")
	@NotNull(message = "父点不能为空")
	@TableField("to_id")
	@Excel(name = "父点")
	private Long toId;

	@Builder
	public GroupParent(Long id, LocalDateTime createTime, Long createUser, Long fromId, Long toId) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.fromId = fromId;
		this.toId = toId;
	}

}
