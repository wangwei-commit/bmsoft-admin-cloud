package com.bmsoft.cloud.work.entity.certificate;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_FEIGN_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_NAME_METHOD;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.bmsoft.cloud.base.entity.Entity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;

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
 * 实体类 凭证
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
@TableName(value = "certificate", autoResultMap = true)
@ApiModel(value = "Certificate", description = "凭证")
@AllArgsConstructor
public class Certificate extends Entity<Long> {

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
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	@TableField(value = "description", condition = LIKE)
	@Excel(name = "描述")
	private String description;

	/**
	 * 组织ID
	 * 
	 * @InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	 *                     RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "组织ID")
	@NotNull(message = "组织ID不能为空")
	@TableField("org_id")
	@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	@ExcelEntity(name = "")
	@Excel(name = "组织ID")
	private RemoteData<Long, String> org;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	@NotEmpty(message = "类型不能为空")
	@Length(max = 32, message = "类型长度不能超过32")
	@TableField(value = "type", condition = LIKE)
	@Excel(name = "类型")
	private String type;

	/**
	 * 类型详细
	 */
	@ApiModelProperty(value = "类型详细")
	@NotEmpty(message = "类型详细不能为空")
	@TableField(value = "type_details", typeHandler = FastjsonTypeHandler.class)
	@Excel(name = "类型详细")
	private Map<String, Object> typeDetails;

	@Builder
	public Certificate(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
			String name, String description, RemoteData<Long, String> orgId, String type, Map<String, Object> typeDetails) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.name = name;
		this.description = description;
		this.org = orgId;
		this.type = type;
		this.typeDetails = typeDetails;
	}

}
