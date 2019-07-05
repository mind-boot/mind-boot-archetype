#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.service;

import com.github.pagehelper.PageInfo;
import com.mind.common.base.ResultBase;
import ${package}.core.bean.dto.AuditUserCommentDTO;
import ${package}.core.bean.query.AuditCommentQuery;

/**
 * 产品点评相关服务接口
 *
 * @version 1.0.0
 * @since 2019-06-12
 */
public interface ProductCommentService {


    /**
     * 查询点评列表（后台）
     *
     * @param query
     * @return
     * @since 2019-06-18
     */
    ResultBase<PageInfo<AuditUserCommentDTO>> selectAuditCommentList(AuditCommentQuery query);

}
