package com.bmsoft.cloud.work.dto.scene;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.bmsoft.cloud.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;

/**
 * <p>
 * 实体类 运维场景
 * </p>
 *
 * @author bmsoft
 * @since 2020-10-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SceneUpdateDTO", description = "运维场景")
public class SceneUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键", required = true)
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotEmpty(message = "名称不能为空")
	@Length(max = 32, message = "名称长度不能超过32")
	private String name;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	private String description;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "图标")
	@Length(max = 20, message = "图标长度不能超过20")
	private String icon;
	/**
	 * 是否内置字段
	 */
	@ApiModelProperty(value = "场景描述")
	@TableField(value = "isDefault(0为内置，1为添加)", condition =EQUAL)
	@Excel(name = "场景描述")
	private boolean isDefault;
}
