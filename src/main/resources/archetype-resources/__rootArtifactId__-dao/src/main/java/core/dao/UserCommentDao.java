#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.dao;

import ${package}.commons.BaseDao;
import ${package}.core.bean.UserComment;
import ${package}.core.bean.dto.AuditUserCommentDTO;
import ${package}.core.bean.query.AuditCommentQuery;

import java.util.List;

/**
 * 用户评论表Dao
 *
 * @version 1.0.0
 */
public interface UserCommentDao extends BaseDao<UserComment> {

    /**
     * 查询点评列表（后台）
     *
     * @param query
     * @return
     * @since 2019-06-18
     */
    List<AuditUserCommentDTO> selectAuditCommentList(AuditCommentQuery query);
}