package com.bmsoft.cloud.work.entity.scene;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bmsoft.cloud.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类 清单组
 * </p>
 *
 * @author bmsoft
 * @since 2020-10-12
 */

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scene")
@ApiModel(value = "Scene", description = "运维场景")
@AllArgsConstructor
public class Scene extends Entity<Long> {

    /**
     * 名称
     */
    @ApiModelProperty(value = "场景名称")
    @NotEmpty(message = "场景名称不能为空")
    @Length(max = 32, message = "场景名称长度不能超过32")
    @TableField(value = "name", condition = LIKE)
    @Excel(name = "场景名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "场景描述")
    @Length(max = 128, message = "场景描述长度不能超过128")
    @TableField(value = "description", condition = LIKE)
    @Excel(name = "场景描述")
    private String description;

    /**
     * 图标
     */
    @ApiModelProperty(value = "场景描述")
    @Length(max = 20, message = "场景描述长度不能超过20")
    @TableField(value = "icon", condition = EQUAL)
    @Excel(name = "场景描述")
    private String icon;

    /**
     * 是否内置字段
     */
    @ApiModelProperty(value = "场景描述")
    @TableField(value = "isDefault", condition =EQUAL)
    @Excel(name = "场景描述")
    private Boolean isDefault;
}
