package com.bmsoft.cloud.work.entity.inventory;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.CERTIFICATE_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.CERTIFICATE_ID_NAME_METHOD;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_FEIGN_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_NAME_METHOD;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bmsoft.cloud.base.entity.Entity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import com.bmsoft.cloud.work.enumeration.inventory.InventoryType;
import com.bmsoft.cloud.work.enumeration.inventory.SynStatus;
import com.bmsoft.cloud.work.enumeration.inventory.VariableType;
import com.bmsoft.cloud.work.handler.RemoteDataListHandler;

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
 * 实体类 清单
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
@TableName(value = "inventory", autoResultMap = true)
@ApiModel(value = "Inventory", description = "清单")
@AllArgsConstructor
public class Inventory extends Entity<Long> {

	private static final long serialVersionUID = 1L;

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
	 * 类型 #InventoryType{STANDARD:标准清单;SMART:智能清单}
	 */
	@ApiModelProperty(value = "类型")
	@NotNull(message = "类型不能为空")
	@TableField("type")
	@Excel(name = "类型", replace = { "标准清单_STANDARD", "智能清单_SMART", "_null" })
	private InventoryType type;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	@TableField(value = "description", condition = LIKE)
	@Excel(name = "描述")
	private String description;

	/**
	 * 同步状态 #SynStatus{NORMAL:正常;FAILURE:失败;UNKNOWN:未知}
	 */
	@ApiModelProperty(value = "同步状态")
	@NotNull(message = "同步状态不能为空")
	@TableField("syn_status")
	@Excel(name = "同步状态", replace = { "正常_NORMAL", "失败_FAILURE", "未知_UNKNOWN", "_null" })
	private SynStatus synStatus;

	/**
	 * 组织ID
	 * 
	 * @InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	 *                     RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "组织ID")
//	@NotNull(message = "组织ID不能为空")
	@TableField("org_id")
	@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	@ExcelEntity(name = "")
	@Excel(name = "组织ID")
	private RemoteData<Long, String> org;

	/**
	 * 凭证ID
	 * 
	 * @InjectionField(api = CERTIFICATE_ID_CLASS, method =
	 *                     CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "凭证ID")
	@TableField(value = "certificate_ids", typeHandler = RemoteDataListHandler.class)
	@InjectionField(api = CERTIFICATE_ID_CLASS, method = CERTIFICATE_ID_NAME_METHOD)
	@ExcelEntity(name = "")
	@Excel(name = "凭证ID")
	private List<RemoteData<Long, String>> certificates;
	
	/**
	 * 实例组
	 */
	@ApiModelProperty(value = "实例组")
	@Length(max = 128, message = "实例组长度不能超过128")
	@TableField(value = "instance_group", condition = LIKE)
	@Excel(name = "实例组")
	private String instanceGroup;

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
	public Inventory(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
			String name, InventoryType type, String description, SynStatus synStatus, RemoteData<Long, String> orgId,
			List<RemoteData<Long, String>> certificateIds, VariableType variableType, String variableValue, String instanceGroup) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.name = name;
		this.type = type;
		this.description = description;
		this.synStatus = synStatus;
		this.org = orgId;
		this.certificates = certificateIds;
		this.variableType = variableType;
		this.variableValue = variableValue;
		this.instanceGroup = instanceGroup;
	}

}
