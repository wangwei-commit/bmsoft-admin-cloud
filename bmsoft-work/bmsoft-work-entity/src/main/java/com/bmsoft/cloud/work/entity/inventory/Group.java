package com.bmsoft.cloud.work.entity.inventory;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_NAME_METHOD;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bmsoft.cloud.base.entity.Entity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import com.bmsoft.cloud.work.enumeration.inventory.VariableType;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * 实体类 清单组
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
@TableName("inventory_group")
@ApiModel(value = "Group", description = "清单组")
@AllArgsConstructor
public class Group extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 清单ID
	 * 
	 * @InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	 *                     RemoteData<String, String>
	 */
	@ApiModelProperty(value = "清单ID")
	@NotNull(message = "清单ID不能为空")
	@TableField("inventory_id")
	@InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	@ExcelEntity(name = "")
	@Excel(name = "清单ID")
	private RemoteData<Long, String> inventory;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@NotEmpty(message = "名称不能为空")
	@Length(max = 32, message = "名称长度不能超过32")
	@TableField(value = "name", condition = LIKE)
	@Excel(name = "名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	@TableField(value = "description", condition = LIKE)
	@Excel(name = "描述")
	private String description;

	/**
	 * 变量类型 #VariableType{YAML:yaml;JSON:json}
	 */
	@ApiModelProperty(value = "变量类型")
	@TableField("variable_type")
	@Excel(name = "变量类型", replace = { "yaml_YAML", "json_JSON", "_null" })
	private VariableType variableType;

	/**
	 * 变量值
	 */
	@ApiModelProperty(value = "变量值")
	@TableField("variable_value")
	@Excel(name = "变量值")
	private String variableValue;

	@Builder
	public Group(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
			RemoteData<Long, String> inventoryId, String name, String description, VariableType variableType,
			String variableValue) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.inventory = inventoryId;
		this.name = name;
		this.description = description;
		this.variableType = variableType;
		this.variableValue = variableValue;
	}

}
