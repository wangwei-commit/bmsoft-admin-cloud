package com.bmsoft.cloud.work.entity.inventory;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bmsoft.cloud.base.entity.Entity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_FEIGN_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_NAME_METHOD;

/**
 * <p>
 * 实体类 清单脚本
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
@TableName("inventory_script")
@ApiModel(value = "Script", description = "清单脚本")
@AllArgsConstructor
public class Script extends Entity<Long> {

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
	 *                     RemoteData<String, String>
	 */
	@ApiModelProperty(value = "组织ID")
	@NotNull(message = "组织ID不能为空")
	@TableField("org_id")
	@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	@ExcelEntity(name = "")
	@Excel(name = "组织ID")
	private RemoteData<Long, String> org;

	/**
	 * 脚本
	 */
	@ApiModelProperty(value = "脚本")
	@NotEmpty(message = "脚本不能为空")
	@Length(max = 65535, message = "脚本长度不能超过65535")
	@TableField("script")
	@Excel(name = "脚本")
	private String script;

	@Builder
	public Script(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
			String name, String description, RemoteData<Long, String> orgId, String script) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.name = name;
		this.description = description;
		this.org = orgId;
		this.script = script;
	}

}
