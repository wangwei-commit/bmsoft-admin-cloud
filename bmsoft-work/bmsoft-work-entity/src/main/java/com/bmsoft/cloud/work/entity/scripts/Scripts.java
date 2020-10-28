package com.bmsoft.cloud.work.entity.scripts;

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
import java.util.Map;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.SCENE_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.SCENE_ID_NAME_METHOD;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scripts")
@ApiModel(value = "Scripts", description = "剧本")
@AllArgsConstructor
public class Scripts extends Entity<Long> {
    /**
     * 名称
     */
    @ApiModelProperty(value = "剧本名称")
    @NotEmpty(message = "剧本名称不能为空")
    @Length(max = 32, message = "剧本名称长度不能超过32")
    @TableField(value = "name", condition = LIKE)
    @Excel(name = "剧本名称")
    private String name;
    /**
     * 名称
     */
    @ApiModelProperty(value = "剧本别名")
    @NotEmpty(message = "剧本别名不能为空")
    @Length(max = 32, message = "剧本别名长度不能超过32")
    @TableField(value = "alias", condition = LIKE)
    @Excel(name = "剧本别名")
    private String alias;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 256, message = "描述长度不能超过256")
    @TableField(value = "description", condition = LIKE)
    @Excel(name = "描述")
    private String description;

    /**
     * 场景ID
     *
     * @InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
     *                     RemoteData<String, String>
     */
    @ApiModelProperty(value = "场景ID")
    @NotNull(message = "场景ID不能为空")
    @TableField("scene_id")
    @InjectionField(api = SCENE_ID_CLASS, method = SCENE_ID_NAME_METHOD)
    @ExcelEntity(name = "")
    @Excel(name = "场景ID")
    private RemoteData<Long, String> scene;

    /**
     * 适用平台
     */
    @ApiModelProperty(value = "适用平台")
    @NotEmpty(message = "适用平台不能为空")
    @TableField(value = "platform", typeHandler = FastjsonTypeHandler.class)
    @Excel(name = "适用平台")
    private Map<String, Object> platform;

    /**
     * 依赖
     */
    @ApiModelProperty(value = "依赖")
    @Length(max = 256, message = "依赖长度不能超过256")
    @TableField(value = "depend", condition = LIKE)
    @Excel(name = "依赖")
    private String depend;

    /**
     * Tags
     */
    @ApiModelProperty(value = "Tags")
    @Length(max = 256, message = "依赖长度不能超过256")
    @TableField(value = "tags", condition = LIKE)
    @Excel(name = "Tags")
    private String tags;
    /**
     * 入参
     */
    @ApiModelProperty(value = "入参")
    @TableField(value = "in_param", typeHandler = FastjsonTypeHandler.class)
    @Excel(name = "入参")
    private Map<String, Object> inParam;

    /**
     * 出参
     */
    @ApiModelProperty(value = "出参")
    @TableField(value = "out_param", typeHandler = FastjsonTypeHandler.class)
    @Excel(name = "出参")
    private Map<String, Object> outParam;




}
