#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 金额格式化工具
 * 
 * @author yong.cao
 * @since 2019年03月21日下午11:33:51
 */
public class MoneyFormatUtil {
    public static String format(String money) {
        if (StringUtils.isNotBlank(money)) {
            //初始化金额，四舍五入
            BigDecimal decMoney = new BigDecimal(money).setScale(2, RoundingMode.HALF_UP);
            //格式化
            DecimalFormat fm = new DecimalFormat("0.00");
            return fm.format(decMoney);
        }
        return "0.00";
    }
}
