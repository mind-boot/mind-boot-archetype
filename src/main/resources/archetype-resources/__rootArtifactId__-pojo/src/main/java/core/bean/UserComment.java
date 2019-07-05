#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_comment")
@Getter
@Setter
@ToString
public class UserComment implements Serializable {
    /**
     * 评论ID
     */
    @Id
    private Integer id;

    /**
     * 产品ID
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 产品品类ID
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 目的地名称
     */
    @Column(name = "dest_name")
    private String destName;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 总体评分（星级）
     */
    @Column(name = "entire_score")
    private Integer entireScore;

    /**
     * 详细评价1（如：景区服务）
     */
    @Column(name = "detail_score1")
    private Integer detailScore1;

    /**
     * 详细评价2（如：游玩体验）
     */
    @Column(name = "detail_score2")
    private Integer detailScore2;

    /**
     * 详细评价3（如：预订便捷）
     */
    @Column(name = "detail_score3")
    private Integer detailScore3;

    /**
     * 详细评价4（如：性价比）
     */
    @Column(name = "detail_score4")
    private Integer detailScore4;

    /**
     * 是否有图片：0，否；1，是
     */
    @Column(name = "is_picture")
    private Boolean isPicture;

    /**
     * 图片数量
     */
    @Column(name = "pic_num")
    private Integer picNum;

    /**
     * 是否精华：0，否；1，是
     */
    @Column(name = "is_essence")
    private Boolean isEssence;

    /**
     * 点赞数
     */
    @Column(name = "like_num")
    private Integer likeNum;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    private Date auditTime;

    /**
     * 审核状态: 0未审核,1审核不通过,2审核通过
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 审核结果：0正常，1建议，2投诉
     */
    @Column(name = "audit_result")
    private Integer auditResult;

    /**
     * 前台是否显示0不显示，1显示
     */
    @Column(name = "is_show")
    private Boolean isShow;

    /**
     * 系统来源字典code
     */
    @Column(name = "source_code")
    private String sourceCode;

    /**
     * 评论发表时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除：0，否；1，是
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 审核人
     */
    @Column(name = "audit_user")
    private String auditUser;

    private static final long serialVersionUID = 1L;


}