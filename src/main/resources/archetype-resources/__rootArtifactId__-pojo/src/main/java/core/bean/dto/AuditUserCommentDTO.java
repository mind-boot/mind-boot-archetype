#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ${package}.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户评论基本信息
 *
 * @version 1.0.0
 * @since 2019-06-17
 */

@Slf4j
@Getter
@Setter
@ToString
@ApiModel(value = "用户评论基本信息")
public class AuditUserCommentDTO implements Serializable {

    private static final long serialVersionUID = -5172624320556355850L;

    @ApiModelProperty(value = "评论ID", example = "12")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "品类ID", example = "11")
    @JsonIgnore
    private Integer categoryId;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    @ApiModelProperty(value = "产品ID", example = "12")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "评论内容")
    private String comment;

    @ApiModelProperty(value = "图片数量")
    private Integer picNum;

    @ApiModelProperty(value = "是否精华")
    @JsonIgnore
    private Boolean isEssencebol;

    @ApiModelProperty(value = "是否精华")
    private String isEssence;

    @ApiModelProperty(value = "审核状态")
    @JsonIgnore
    private Boolean auditStatusbol;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

    @ApiModelProperty(value = "前台显示")
    @JsonIgnore
    private Boolean isShowbol;

    @ApiModelProperty(value = "前台显示")
    private String isShow;

    @ApiModelProperty(value = "审核时间")
    @JsonIgnore
    private Date auditTimeDate;

    @ApiModelProperty(value = "审核时间")
    private String auditTime;

    @ApiModelProperty(value = "评论发表时间")
    @JsonIgnore
    private Date createTimeDate;

    @ApiModelProperty(value = "评论发表时间")
    private String createTime;

    @ApiModelProperty(value = "审核结果：0，1，2", example = "0")
    private Integer auditResult;

    @ApiModelProperty(value = "审核结果: 0正常，1建议，2投诉")
    private String auditResultName;

    /** 后台审核用字段 **/
    @ApiModelProperty(value = "总体评分（星级）")
    private Integer entireScore;

    @ApiModelProperty(value = "详细评价1（如：景区服务）")
    private Integer detailScore1;

    @ApiModelProperty(value = "详细评价2（如：游玩体验）")
    private Integer detailScore2;

    @ApiModelProperty(value = "详细评价3（如：预订便捷）")
    private Integer detailScore3;

    @ApiModelProperty(value = "详细评价4（如：性价比）")
    private Integer detailScore4;

    @ApiModelProperty(value = "审核人")
    private String auditUser;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "系统来源代码")
    private String sourceCode;

    @ApiModelProperty(value = "系统来源名称")
    private String sourceCodeName;

    @ApiModelProperty(value = "评论是否有图片")
    private Boolean isPicture;

    @ApiModelProperty(value = "图片")
    private List<String> picUrls;
    /** 后台审核用字段 **/


    public String getIsEssence() {
        return null == isEssencebol ? null : isEssencebol ? "是" : "否";
    }

    public String getAuditStatus() {
        return null == auditStatusbol ? "未审核" : auditStatusbol ? "审核通过" : "审核不通过";
    }

    public String getIsShow() {
        return null == isShowbol ? null : isShowbol ? "是" : "否";
    }

    public String getAuditTime() {
        return null == auditTimeDate ? null : DateUtil.getDateTime(auditTimeDate);
    }

    public String getCreateTime() {
        return null == createTimeDate ? null : DateUtil.getDateTime(createTimeDate);
    }

    public String getAuditResultName() {
        if (null != auditResult) {
            switch (auditResult.intValue()) {
                case 0:
                    return "正常";
                case 1:
                    return "建议";
                case 2:
                    return "投诉";
                default:
                    return null;
            }
        }
        return String.valueOf(auditResult);
    }
}
