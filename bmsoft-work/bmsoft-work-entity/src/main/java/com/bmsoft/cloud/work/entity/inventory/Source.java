package com.bmsoft.cloud.work.entity.inventory;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
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
import java.util.Map;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_NAME_METHOD;

/**
 * <p>
 * 实体类 清单源
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
@TableName(value = "inventory_source", autoResultMap = true)
@ApiModel(value = "Source", description = "清单源")
@AllArgsConstructor
public class Source extends Entity<Long> {

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
	 * 来源
	 */
	@ApiModelProperty(value = "来源")
	@NotEmpty(message = "来源不能为空")
	@Length(max = 32, message = "来源长度不能超过32")
	@TableField(value = "source")
	@Excel(name = "来源")
	private String source;

	/**
	 * 来源详细
	 */
	@ApiModelProperty(value = "来源详细")
	@NotEmpty(message = "来源详细不能为空")
	@TableField(value = "source_details", typeHandler = FastjsonTypeHandler.class)
	@Excel(name = "来源详细")
	private Map<String, Object> sourceDetails;

	@Builder
	public Source(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
			RemoteData<Long, String> inventoryId, String name, String description, String source,
			Map<String, Object> sourceDetails) {
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.inventory = inventoryId;
		this.name = name;
		this.description = description;
		this.source = source;
		this.sourceDetails = sourceDetails;
	}

}
