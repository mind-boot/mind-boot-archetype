#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.mind.boot.common.base.ResultBase;
import ${package}.core.bean.dto.AuditUserCommentDTO;
import ${package}.core.bean.query.AuditCommentQuery;
import ${package}.core.dao.UserCommentDao;
import ${package}.core.service.ProductCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductCommentServiceImpl implements ProductCommentService {


    @Autowired
    private UserCommentDao userCommentDao;

    /**
     * 查询点评列表（后台）
     *
     * @param query
     * @return
     */
    @Override
    public ResultBase<PageInfo<AuditUserCommentDTO>> selectAuditCommentList(AuditCommentQuery query) {
        ResultBase<PageInfo<AuditUserCommentDTO>> result = new ResultBase<>();
        try {
            PageHelper.startPage(query.getPageNo(), query.getLimit());
            List<AuditUserCommentDTO> userCommentList = userCommentDao.selectAuditCommentList(query);
            PageInfo<AuditUserCommentDTO> pageInfo = new PageInfo<>(userCommentList);
            result.setSuccess(true);
            result.setValue(pageInfo);
        } catch (Exception e) {
            log.error("selectAuditCommentList error: {}", e.getMessage(), e);
        }
        return result;
    }

}
