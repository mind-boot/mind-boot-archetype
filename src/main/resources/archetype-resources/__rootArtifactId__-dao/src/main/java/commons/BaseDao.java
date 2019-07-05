#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.commons;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用Mapper
 * 
 * @author caoyong
 * @since 2018年11月30日 下午2:29:33
 * @param <T>
 */
public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {
}
