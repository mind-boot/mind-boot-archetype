#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.controller;

import com.github.pagehelper.PageInfo;
import com.mind.common.base.ResultBase;
import ${package}.core.bean.dto.AuditUserCommentDTO;
import ${package}.core.bean.query.AuditCommentQuery;
import ${package}.core.service.ProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0
 * @since 2019-06-12
 */

@Api(value = "产品点评相关接口", tags = {"所有产品点评相关接口"})
@Slf4j
@RestController
@RequestMapping("/productComment")
public class ProductCommentController {

    @Autowired
    private ProductCommentService productCommentService;


    @RequestMapping("auditCommentList")
    @ApiOperation(value = "查询点评列表（后台）", httpMethod = "GET")
    public ResultBase<PageInfo<AuditUserCommentDTO>> selectAuditCommentList(AuditCommentQuery query) {
        return productCommentService.selectAuditCommentList(query);
    }

}
