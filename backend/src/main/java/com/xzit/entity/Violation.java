package com.xzit.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@Getter
@Setter
@Data
@Accessors(chain = true)
@TableName("busi_violation")
@ApiModel(value = "Violation对象", description = "")
public class Violation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("违章id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("车辆id")
    private Integer autoId;

    private String autoNum;

    @ApiModelProperty("违章时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vtime;

    @ApiModelProperty("违章事由")
    private String reason;

    @ApiModelProperty("违章地点")
    private String location;

    @ApiModelProperty("扣分")
    private Integer deductPoints;

    @ApiModelProperty("罚款")
    private Integer fine;

    @ApiModelProperty("是否处理 0 -未处理  1-已处理")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @TableField(exist = false)
    private Integer lowFine;
    @TableField(exist = false)
    private Integer highFine;
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lowVtime;
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime highVtime;

}
