#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.bean.query;

import com.mind.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 后台审核页query
 *
 * @version 1.0.0
 * @since 2019-6-18
 */

@ApiModel(value = "点评后台审核页")
@Getter
@Setter
@ToString
@Slf4j
public class AuditCommentQuery extends BaseQuery implements Serializable {

    private static final long serialVersionUID = 6178894740813448034L;

    @ApiModelProperty(value = "点评编号", example = "12")
    private Integer id;

    @ApiModelProperty(value = "精华状态")
    private Boolean isEssence;

    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus;

    @ApiModelProperty(value = "前台显示")
    private Boolean isShow;

    @ApiModelProperty(value = "是否有图")
    private Boolean isPicture;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "目的地名称")
    private String destName;

    @ApiModelProperty(value = "品类")
    private Integer categoryId;

    @ApiModelProperty(value = "来源")
    private String sourceCode;

    @ApiModelProperty(value = "审核日期(开始),格式yyyy-MM-dd HH:mm:ss")
    private String auditStartTime;

    @ApiModelProperty(value = "审核日期(结束),格式yyyy-MM-dd HH:mm:ss")
    private String auditEndTime;

    @ApiModelProperty(value = "发表日期(开始),格式yyyy-MM-dd HH:mm:ss")
    private String createStartTime;

    @ApiModelProperty(value = "发表日期(结束),格式yyyy-MM-dd HH:mm:ss")
    private String createEndTime;

    @ApiModelProperty(value = "店铺ID", example = "12")
    private Integer shopId;
}
