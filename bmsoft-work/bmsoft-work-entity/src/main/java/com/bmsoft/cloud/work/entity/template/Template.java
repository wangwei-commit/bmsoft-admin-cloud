package com.bmsoft.cloud.work.entity.template;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.bmsoft.cloud.base.entity.Entity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import com.bmsoft.cloud.work.enumeration.inventory.TemplateFileType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.*;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("template")
@ApiModel(value = "Template", description = "作业模板")
@AllArgsConstructor
public class Template extends Entity<Long> {
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
    @Length(max = 256, message = "描述长度不能超过256")
    @TableField(value = "description", condition = LIKE)
    @Excel(name = "描述")
    private String description;
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
     * 过滤主机
     */
    @ApiModelProperty(value = "过滤主机")
    @Length(max = 256, message = "过滤主机长度不能超过256")
    @TableField(value = "filter_host", condition = LIKE)
    @Excel(name = "过滤主机")
    private String filterHost;

    /**
     * 凭证ID
     *
     * @InjectionField(api = CERTIFICATE_ID_CLASS, method =
     *                     CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>
     */
    @ApiModelProperty(value = "凭证ID")
    @TableField(value = "certificate_id")
    @InjectionField(api = CERTIFICATE_ID_CLASS, method = CERTIFICATE_ID_NAME_METHOD)
    @ExcelEntity(name = "")
    @Excel(name = "凭证ID")
    private RemoteData<Long, String> certificates;

    /**
     * 脚本类型 #ScriptType{SCENARIOS:scenarios;SCRIPTS:scripts}
     */
    @ApiModelProperty(value = "脚本类型")
    @NotNull(message = "脚本类型")
    @TableField("script_type")
    @Excel(name = "脚本类型", replace = { "SCENARIOS_scenarios", "SCRIPTS_scripts" })
    private TemplateFileType templateFileType;


    /**
     * 脚本/剧本ID
     *
     * @InjectionField(api = CERTIFICATE_ID_CLASS, method =
     *                     CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>
     */
    @ApiModelProperty(value = "脚本/剧本ID")
    @TableField(value = "script_id")
    @InjectionField(api = TEMPLATE_ID_CLASS, method = TEMPLATE_ID_NAME_METHOD)
    @ExcelEntity(name = "")
    @Excel(name = "脚本/剧本ID")
    private RemoteData<Long, String> scriptId;


    /**
     * work_tag
     */
    @ApiModelProperty(value = "work_tag")
    @Length(max = 256, message = "依赖长度不能超过256")
    @TableField(value = "work_tag", condition = LIKE)
    @Excel(name = "work_tag")
    private String workTag;

    /**
     * skip_tag
     */
    @ApiModelProperty(value = "skip_tag")
    @Length(max = 256, message = "依赖长度不能超过256")
    @TableField(value = "skip_tag", condition = LIKE)
    @Excel(name = "skip_tag")
    private String skipTag;

    /**
     * 分片
     */
    @ApiModelProperty(value = "分片")
    @Max(9999)
    @TableField(value = "zone", condition = EQUAL)
    @Excel(name = "分片")
    private Integer zone;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数")
    @TableField(value = "param", typeHandler = FastjsonTypeHandler.class)
    @Excel(name = "参数")
    private Map<String, Object> param;




}
