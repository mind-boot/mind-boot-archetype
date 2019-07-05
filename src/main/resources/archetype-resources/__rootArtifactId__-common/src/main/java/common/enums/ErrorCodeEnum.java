#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.enums;

import java.util.stream.Stream;

/**
 * 错误枚举
 *
 * @author caoyong
 * @since 2018年1月29日 上午11:34:26
 */
public enum ErrorCodeEnum{

    UNKOWN_ERROR("500", "未知错误"),

    DATA_BASE_ACCESS_ERROR("1001", "访问数据库错误"),
    PARAMETER_CAN_NOT_BE_NULL("1002", "参数不能为空"),
    NUM_FORMATE_ERROR("1003", "数字格式化错误"),
    IO_ERROR("1005", "IO错误"),
    PROCESS_DATA_ERROR("1006", "数据处理错误"),
    PARAMETER_ERROR("1008", "参数错误"),
    USER_NAME_IS_EXIST("1011", "用户名已存在"),
    REDIS_ACCESS_ERROR("1014", "redis链接错误"),
    PARAMETER_NOT_MATCH("1015", "参数不匹配"),
    GET_REDIS_LOCK_FAILURE("1021","获取redis锁失败"),

    SERVICE_SAVE_ERROR("2001", "Service保存失败"),
    SERVICE_UPDATE_ERROR("2002", "Service更新失败"),
    SERVICE_BATCH_SAVE_ERROR("2003", "Service批量保存失败"),
    SERVICE_QUERY_ERROR("2003", "Service查询失败"),
    PASSWORD_IS_ERROR("2004", "密码不正确"),
    OPERATION_NOT_EXIST("2005", "操作类型不存在"),
    PRIMARY_KEY_NOT_EXIST("2006", "主键不存在"),
    DETAIL_TYPE_NOT_EXIST("2007", "详情类型不存在"),

    COMMENT_ID_NOT_EXIST("3001", "通过评论id找不到相关评论"),

    UPLOAD_FILE_ERROR("6001", "上传文件失败"),

    SUCCESS("200", "成功"),
    QUERY_ERROR("301", "查询失败"),
    QUERY_DATA_NOT_EXIST("302", "相关数据不存在"),
    QUERY_DATA_IS_EXIST("304", "相关数据已存在"),
    USER_NOT_EXIST("001", "用户不存在"),
    NO_LOGIN_ERROR("000", "未登录");

    private String code;
    private String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 获得枚举
     *
     * @param code
     * @return
     */
    public static ErrorCodeEnum getEnum(String code) {
        return Stream.of(ErrorCodeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }
}
