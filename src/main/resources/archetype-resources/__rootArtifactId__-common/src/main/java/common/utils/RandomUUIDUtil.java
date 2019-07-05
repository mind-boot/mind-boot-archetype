#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.utils;

import java.util.UUID;

/**
 * 随机获取uuid(不带'-')
 * 
 * @author caoyong
 * @since 2018年11月29日 下午12:54:26
 */
public class RandomUUIDUtil {

    /**
     * 获取uuid
     * 
     * @return
     */
    public static String getRadomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
