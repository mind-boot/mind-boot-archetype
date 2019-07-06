#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.exception;

import io.github.mind.boot.common.exception.BaseBizException;
import ${package}.common.enums.ErrorCodeEnum;
/**
 * service业务处理异常
 *
 * @author caoyong
 * @since 2018年1月29日 上午11:40:49
 */

public class BizException extends BaseBizException {


    public BizException() {
        super();
    }

    public BizException(String errorCode, String msg, Throwable cause) {
        super(errorCode, msg, cause);

    }

    public BizException(String errorCode, String msg) {
        super(errorCode, msg);

    }

    public BizException(ErrorCodeEnum error) {
        super(error.getCode(), error.getMsg());
    }

    public BizException(ErrorCodeEnum error, Throwable cause) {
        super(error.getCode(), error.getMsg(), cause);
    }

    public BizException(ErrorCodeEnum error, String msg, Throwable cause) {
        super(error.getCode(), msg, cause);
    }

}
