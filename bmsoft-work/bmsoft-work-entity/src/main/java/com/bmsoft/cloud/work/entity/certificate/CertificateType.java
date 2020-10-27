package com.bmsoft.cloud.work.entity.certificate;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.bmsoft.cloud.base.entity.Entity;
import com.bmsoft.cloud.work.enumeration.inventory.VariableType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.ArrayTypeHandler;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类 凭证类型管理
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
@TableName(value = "certificateType", autoResultMap = true)
@ApiModel(value = "CertificateType", description = "凭证")
@AllArgsConstructor
public class CertificateType extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@NotEmpty(message = "名称不能为空")
	@Length(max = 32, message = "名称长度不能超过32")
	@TableField(value = "name", condition = LIKE)
	@Excel(name = "名称")
	private String display;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "key")
	@Length(max = 32, message = "key长度不能超过32")
	@TableField(value = "`key`", condition = LIKE)
	@Excel(name = "key")
	private String key;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	@TableField(value = "description", condition = LIKE)
	@Excel(name = "描述")
	private String description;
	/**
	 * 类型 #VariableType{YAML:yaml;JSON:json}
	 */
	@ApiModelProperty(value = "类型")
	@NotNull(message = "类型不能为空")
	@TableField("certificate_type")
	@Excel(name = "类型", replace = { "yaml_YAML", "json_JSON", "_null"  })
	private VariableType certificateType;

	/**
	 * 类型详细
	 */
	@ApiModelProperty(value = "类型详细")
	@NotEmpty(message = "类型详细不能为空")
	@TableField(value = "certificate_value", typeHandler = FastjsonTypeHandler.class)
	@Excel(name = "类型详细")
	private List<Map<String, Object>> fields;

	/**
	 * 是否内置
	 */
	@ApiModelProperty(value = "是否内置")
	@TableField(value = "isDefault", condition =EQUAL)
	@Excel(name = "是否内置")
	private Boolean isDefault;

	@Builder
	public CertificateType(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                           String display, String description,   VariableType certificateType, List<Map<String, Object>> fields,Boolean isDefault) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.display = display;
		this.description = description;
	 	this.certificateType = certificateType;
	 	this.fields = fields;
	 	this.isDefault=isDefault;
	}

}
