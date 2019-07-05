#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.github.pagehelper.PageInfo;
import com.mind.common.base.ResultBase;
import ${package}.core.bean.dto.AuditUserCommentDTO;
import ${package}.core.bean.query.AuditCommentQuery;
import ${package}.core.service.ProductCommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.spring.annotation.MapperScan;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@MapperScan(basePackages = "${package}.core.dao")
@Slf4j
public class TestComment {

    @Autowired
    private ProductCommentService productCommentService;

    @Test
    public void testAuditComment() {
        AuditCommentQuery query = new AuditCommentQuery();
        ResultBase<PageInfo<AuditUserCommentDTO>> result = productCommentService.selectAuditCommentList(query);
        System.out.println(result);
    }
}
