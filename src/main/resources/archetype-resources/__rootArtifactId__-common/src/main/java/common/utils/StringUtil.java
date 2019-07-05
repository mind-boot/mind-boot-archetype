#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.utils;




import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;


public class StringUtil {
	
	
	/**
	 * 判断对象是否true
	 */
	public static Boolean nullToBoolean(Object value){
		if(value == null 
				|| "null".equals(value.toString()))
			return false;
		if("1".equals(value.toString().trim()) 
				|| "true".equalsIgnoreCase(value.toString().trim())
				|| "是".equalsIgnoreCase(value.toString().trim()))
			return true;
		return false;
	}
	
	/**
	 * 字符串是否有效(null,""时无效)
	 * when null or ""  false
	 * or true
	 * @param str String 字符串
	 * @return boolean 是否有效
	 */
    public static boolean isValid(String str){
    	if(str==null||"".equals(str) || "null".equals(str))
    		return false;
    	else
    		return true; 
    }
    public static Integer stringToInteger(String value) {
		Integer l;
		value = nullToString(value);
		if ("".equals(value)) {
			l = 0;
		} else {
			try {
				l = Integer.valueOf(value);

			} catch (Exception e) {
				l = 0;
			}
		}
		return l;
	}
    public static String nullToString(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	public static String nullToString(Object value) {
		return value == null ? "" : value.toString().trim();
	}
    
    public static Integer nullToInteger(Object value){
		return value == null || "null".equals(value.toString()) ? 0: stringToInteger(value.toString());
	}
	/**
	 * 当字符串为空时,返回defaultValue,否则返回原字符串
	 * @param str 原字符串 
	 * @param defaultValue 被替换的字符
	 * @return String
	 */
    public static String nvl(String str, String defaultValue) {
        if (str==null||"".equals(str)){
        	str = defaultValue;
        }
        return str;
    }
    
	/**
	 * 当字符串为空时,返回defaultValue,否则返回原字符串
	 * @param str 原字符串 
	 * @param defaultValue 被替换的字符
	 * @param level 0-null,1-null&"",2-null&""&trim""
	 * @return String
	 */
    public static String nvl(String str, String defaultValue ,int level) {
        if (str==null){
        	str = defaultValue;
        }else if(level==1&&"".equals(str)){
        	str = defaultValue;
        }else if(level==2&&("".equals(str)||"".equals(str.trim()))){
        	str = defaultValue;
        }
        return str;
    }
    
    /**
     * 判断两个字符串是否相等
     * @param str1 字符串1
     * @param str2 字符串2
     * @return boolean 是否相等
     */
    public static boolean equals(String str1,String str2){
    	if(str1==null&&str2==null||str1!=null&&str1.equals(str2)) return true;
    	else return false;
    }
    
    /**
     * 判断两个字符串trim是否相等
     * @param str1 字符串1
     * @param str2 字符串2
     * @return boolean 是否相等
     */
    public static boolean equalsWithTrim(String str1,String str2){
    	if(str1==null&&str2==null||str1!=null&&str1.trim().equals(str2.trim())) return true;
    	else return false;
    }
    
    /**
     * 按字节长度得到子字符串(不允许半个字符)
     * @param str 原字符串
     * @param length 字节长度
     * @return String 子字符串
     */
    public static String subStringByByteLength(String str,int length){
    	return subStringByByteLength(str,length,false,true);
    }
    
    /**
     * 按字节长度得到子字符串
     * @param str 原字符串
     * @param length 字节长度
     * @param allowHalf 是否允许半个字符
     * @param fromHead 是否从头开始截取,true从头部,false从尾部
     * @return String 子字符串
     */
	public static String subStringByByteLength(String str,int length,boolean allowHalf,boolean fromHead){
    	if(str==null||length<0||str.getBytes().length<=length) return str;
    	StringBuffer sb=new StringBuffer();
    	byte[] strByte=str.getBytes();
    	if(fromHead){
    		sb.append(new String(strByte,0,length));
        	if(!allowHalf){
    	    	int endPos=Math.min(sb.length()-1,str.length()-1);
    	    	while(endPos>=0&&sb.charAt(endPos)!=str.charAt(endPos)){
    	    		sb.deleteCharAt(endPos);
    	    		endPos--;
    	    	}
        	}
    	}else{
    		sb.append(new String(strByte,strByte.length-length,length));
        	if(!allowHalf){
    	    	int minLength=Math.min(sb.length(),str.length());
    	    	while(minLength>=1&&sb.charAt(sb.length()-minLength)!=str.charAt(str.length()-minLength)){
    	    		sb.deleteCharAt(sb.length()-minLength);
    	    		minLength--;
    	    	}
        	}
    	}
    	return sb.toString();
	}
    
    /**
     * 制造重复字符串
     * @param dupStr 需要重复的字符串
     * @param splitStr 分隔符
     * @param dupTime 重复次数
     * @param needEnd 是否需要分隔符结束
     * @return String 重复字符串
     */
    public static String productDupString(String dupStr,String splitStr,int dupTime,boolean needEnd){
		if(dupTime<1) return "";
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<dupTime;i++){
			sb.append(dupStr);
			if(!needEnd&&i!=dupTime-1) sb.append(splitStr);
		}
		return sb.toString();
	}
    
	/**
	 * 格式化字符串,添加空格
	 * @param str 原字符串
	 * @param totalLen 结果总长
	 * @param isappend 是否附加,true:加在后面,false:加在前面
	 * @return String 格式化的字符串
	 */
    public static String addBlank(String str, int totalLen,boolean isappend){
    	return addSingleStr(str," ",totalLen,isappend);
    }
    
	/**
	 * 格式化字符串,添加单字符
	 * @param str 原字符串
	 * @param sstr 单字符串
	 * @param totalLen 结果总长
	 * @param isappend 是否附加,true:sstr加在前面,false:sstr加在hou面
	 * @return String 格式化的字符串
	 */
    public static String addSingleStr(String str,String sstr, int totalLen,boolean isappend){
    	if(str==null) str="null";
    	if(sstr==null||sstr.length()!=1) return str;
        int len = str.length();
        StringBuffer sb = new StringBuffer();
        
        if(totalLen-len<0)
        {
        	sb.append(str.substring(0, totalLen));
        }
        else
        {
        	if(!isappend) sb.append(str);
        	for (int i = 0; i <totalLen-len; i++){
                sb.append(sstr);
            }
            if(isappend) sb.append(str);
        }
        
        return sb.toString();
    }
    
    /**
     * 通过标志获取xml中的第一个字符串
     * @param xml xml字符串
     * @param flag 标志
     * @return String 获取到的字符串值,非null
     */
	public static String getFirstXmlStrByFlag(String xml,String flag) {
		if(!isValid(xml)||!isValid(flag)) return "";
		String beginFlag=spellXmlName(flag,true);
		String endFlag=spellXmlName(flag,false);
		String retStr="";
		int beginPos=xml.indexOf(beginFlag)+beginFlag.length();
		int endPos=xml.indexOf(endFlag);
		if(beginPos>-1&&endPos>=beginPos){
			retStr=xml.substring(beginPos,endPos);
		}
		return retStr;
	}
	
	/**
	 * 通过标志设置xml中的第一个字符串
	 * @param xml xml字符串
	 * @param flag 标志
	 * @param str 字符串值
	 * @return String 设置入字符串值的xml
	 */
	public static String setFirstXmlStrByFlag(String xml,String flag,String str){
		if(!isValid(xml)||!isValid(flag)) return xml;
		String startFlag=spellXmlName(flag,true);
		String endFlag=spellXmlName(flag,false);
		int startPos=xml.indexOf(startFlag)+startFlag.length();
		int endPos=xml.indexOf(endFlag);
		if(startPos>-1&&endPos>=startPos){
			xml=new StringBuffer().append(xml.substring(0,startPos)).append(str).append(xml.substring(endPos)).toString();
		}
		return xml;
	}
	
	/**
	 * 拼写xml名
	 * @param flag 标志
	 * @param isBegin 是否是开始节点
	 * @return String xml名
	 */
	public static String spellXmlName(String flag,boolean isBegin){
		flag=nvl(flag,"");
		StringBuffer sb=new StringBuffer();
		if(isBegin) sb.append("<").append(flag).append(">");
		else sb.append("</").append(flag).append(">");
		return sb.toString();
	}
	
	/**
	 * decode函数,参数数目为0时返回null,参数数目小于3时返回第一个参数,默认返回null,其余同oracle中decode
	 * @param strs 字符串
	 * @return 
	 */
	public static String decode(String... strs){
		if(strs==null||strs.length==0) return null;
		if(strs.length<3) return strs[0];
		String sourceStr=strs[0];
		String retStr=null;
		if(strs.length%2==0){
			retStr=strs[strs.length-1];
		}
		for(int i=1;i+2<=strs.length;i=i+2){
			if(StringUtil.equals(sourceStr, strs[i])){
				retStr=strs[i+1];
				break;
			}
		}
		return retStr;
	}
	
	/**
	 * 通过正则表达式获取第一个匹配的字符串
	 * @param str 原字符串
	 * @param regex 正则表达式
	 * @return 匹配到的字符串
	 */
	public static String getFirstStrByregex(String str,String regex){
		if(!StringUtil.isValid(str)||!StringUtil.isValid(regex)) return str;
		String retStr=null;
		Matcher matcher=Pattern.compile(regex).matcher(str);
		if(matcher.find()){
			retStr=matcher.group();
		}
		return retStr;
	}
	
	/**
	 * 以开始结束符,分割字符串
	 * @param str
	 * @param length
	 * @param bstr
	 * @param estr
	 * @return
	 */
	public static List<String> splitStringByByteLength(String str,int length,String bstr,String estr){
		List<String> retList=new ArrayList<String>();
		int strl=0;
		int bstrl=0;
		int estrl=0;
		if(!StringUtil.isValid(str)||length<=0||(strl=str.getBytes().length)<=length||length<=(bstrl=bstr.getBytes().length)+(estrl=estr.getBytes().length)+2){
			retList.add(str);
			return retList;
		}
		int begin=0;
		int end=begin+length-bstrl;
		String tmp=null;
		byte[] bs=str.getBytes();
		while(begin<end){
			if(strl-begin+estrl<=length){
				end=strl;
				new String();
				retList.add(estr+new String(bs,begin,end-begin));
				begin=end;
			}else{
				tmp=subStringByByteLength(new String(bs,begin,strl-begin), end-begin);
				if(begin==0){
					retList.add(tmp+bstr);
				}else{
					retList.add(estr+tmp+bstr);
				}
				begin=begin+tmp.getBytes().length;
				end=begin+length-bstrl-estrl;
			}
		}
		return retList;
	}
	
	/**
	 * String解析到Map
	 * @param str
	 * @param tag1
	 * @param tag2
	 * @return
	 */
	public static Map<String,String> parseString2Map(String str,String tag1,String tag2){
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtil.isValid(str)&&StringUtil.isValid(tag1)&&StringUtil.isValid(tag2)){
			String[] strs1=str.split(tag1,Integer.MAX_VALUE);
			int pos=0;
			for(String str1:strs1){
				pos++;
				String[] strs2=str1.split(tag2,2);
				if(strs2.length==1){
					map.put(pos+"",strs2[0]);
				}else{
					map.put(strs2[0],strs2[1]);
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 字符串个数
	 * @param string
	 * @param str
	 * @return
	 */
	public static int countStr(String string,String str){
		int count=0;
		int stringLength=string==null?0:string.length();
		int strLength=str==null?0:str.length();
		if(stringLength>0&&strLength>0){
			int index=-1;
			while((index=string.indexOf(str,index+strLength))>=0){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 切割字符串,非正则
	 * @param string
	 * @param str
	 * @param limit
	 * @return
	 */
	public static String[] split(String string,String str,int limit){
		List<String> list=new ArrayList<String>();
		int stringLength=string==null?0:string.length();
		int strLength=str==null?0:str.length();
		if(stringLength>0&&strLength>0){
			int index=-1;
			int lastIndex=0;
			boolean isLimited = limit > 0;
			while((!isLimited||--limit>0)&&(index=string.indexOf(str,lastIndex))>=0){
				list.add(string.substring(lastIndex,index));
				lastIndex=index+strLength;
			}
			if(!isLimited||limit>=0){
				list.add(string.substring(lastIndex,stringLength));
			}
		}else if(string!=null){
			list.add(string);
		}
		return list.toArray(new String[0]);
	}
	
	/**
	 * 切割字符串,非正则
	 * @param string
	 * @param str
	 * @return
	 */
	public static String[] split(String string,String str){
		return split(string,str,0);
	}
	
	/**
	 * 反向切割字符串,非正则
	 * @param string
	 * @param str
	 * @param limit
	 * @return
	 */
	public static String[] splitReverse(String string,String str,int limit){
		List<String> list=new ArrayList<String>();
		int stringLength=string==null?0:string.length();
		int strLength=str==null?0:str.length();
		if(stringLength>0&&strLength>0){
			int index=-1;
			int lastIndex=stringLength;
			boolean isLimited = limit > 0;
			while((!isLimited||--limit>0)&&(index=string.lastIndexOf(str,lastIndex-1))>=0){
				list.add(string.substring(index+strLength,lastIndex));
				lastIndex=index;
			}
			if(!isLimited||limit>=0){
				list.add(string.substring(0,lastIndex));
			}
		}else if(string!=null){
			list.add(string);
		}
		return list.toArray(new String[0]);
	}
	
	/**
	 * 反向切割字符串,非正则
	 * @param string
	 * @param str
	 * @return
	 */
	public static String[] splitReverse(String string,String str){
		return splitReverse(string,str,0);
	}
	
	/**
	 * 使用指定的格式格式当前的日期对象
	 * 
	 * @param obj
	 *            Date 要格式化的时间对象 为空时返回但前时间
	 * @param format
	 *            String 指定的格式
	 * @return String 返回的时间串
	 */
	public static String date2String(java.util.Date obj, String format) {
		if (obj == null) {
			obj = new java.util.Date();
		} // yyyyMMddHHmmss
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.format(obj);
	}
	
	/**
	 * 
	* @Title: getTimerSeq 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param format 日期字符格式，默认yyyyMMddHHmmssSSS
	* @param @param randomFlag 是否追加随机数,默认1位,最大10位
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String  getTimerSeq(String format,boolean randomFlag,int randomLen)
	{
		format = isValid(format)?format:"yyyyMMddHHmmssSSS";
		String timerStr = new SimpleDateFormat(format).format(new Date());
		
		String ret = timerStr;
		if(randomFlag)
		{
			Random random = new Random();
			randomLen = randomLen <1 ? 1: randomLen;
			randomLen = randomLen >10 ? 10: randomLen;
			StringBuffer sb = new StringBuffer();
			for(int i =0;i<randomLen;i++)
			{
				sb.append(random.nextInt(10));
			}
			ret=ret+sb.toString();
		}
		return ret;
	}
	
	/**
	 * 
	* @Title: generateCorn 
	* @Description: TODO 获取传入时间信息的corn信息
	* @param @param date
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String generateCorn(Date date)
	{
		
		return new SimpleDateFormat("ss mm HH dd MM ? yyyy").format(date);
				
	}
	
	public static String getPercent(int x, int y,String format)
	{
		String baifenbi = "";// 接受百分比的值
		double baiy = x * 1.0;
		double baiz = y * 1.0;
		double fen = baiy / baiz;
		DecimalFormat df1 = new DecimalFormat(format); 
		// baifenbi=nf.format(fen);
		baifenbi = df1.format(fen);
		if (".00%".equals(baifenbi))
		{
			baifenbi = "0%";
		}
		if ("100.00%".equals(baifenbi))
		{
			baifenbi = "100%";
		}
		if (baifenbi.endsWith(".00%"))
		{
			baifenbi = baifenbi.substring(0, baifenbi.length() - 4) + "%";
		} 
		return baifenbi;
	}
	
	/**
	 * 
	* @Title: generateCorn 
	* @Description: TODO 获取传入时间信息的corn信息
	* @param @param year
	* @param @param month
	* @param @param date
	* @param @param hourOfDay
	* @param @param minute
	* @param @param second
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String generateCorn(int year, int month, int date, int hourOfDay, int minute,
            int second)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date, hourOfDay, minute, second);;
		return new SimpleDateFormat("ss mm HH dd MM ? yyyy").format(calendar.getTime());
				
	}
	
	public static void printlnStack()
	{
		
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		if(stack != null && stack.length > 0)
		{
			for(int i = 0;i<stack.length;i++)
			{
				System.out.println(stack[i]);
			}
			
		}
				
	}
	/**
	 * 判断字符串是不是纯数字
	*/
	public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
	/**
	 * 判断字符串是不是浮点型值
	*/
	public static boolean isDoubleNumeric(String str) {
		try {
			Double.parseDouble(str.trim());
			return true;
		} catch (Exception e) {
			return false;
		}
    }
	/**
	 * 获取末尾子串
	*/
	public static String getEndStringByChar(String str,String subStr) {
        if(!StringUtil.isValid(str)){
        	return "";
        }
        String[] strs = str.split(subStr);
        int strsNum = strs.length;
        return strs[strsNum-1];
    }
	/**
	 * 多入参
	 * 返回最大值
	*/
	public static int getMaxValue(int... list){
		int result = 0;
		for(int v : list){
			if(v > result){
				result = v;
			}
		}
		return result;
	}
	/**
	 * 格式化double数值，四舍五入，保留一位小数
	 * 
	*/
	public static String formatDouble(double d) {
        return String.format("%.1f", d);
    }
	/**
	 * 格式化double数值，四舍五入，保留2位小数
	 * 
	*/
	public static String formatDouble2(double d) {
        return String.format("%.2f", d);
    }
	
	/**
	 * 对象转Json
	 */
	public static String objectToJSON(Object object){
		ObjectMapper om = new ObjectMapper();  
		Writer w = new StringWriter();  
		String json = null;  
		try {  
			om.writeValue(w, object);  
			json = w.toString();  
			w.close();  
		} catch (IOException e) {  
			e.printStackTrace();
		}  
		return json;
	}
	
	/**
	 * 生成n位随机数
	 * @param digit 位数
	 * @return
	 */
	public static String getRandomStr(int digit){
		
		if (digit > 0){
			Random random = new Random();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i< digit; i ++){
				buffer.append(random.nextInt(10));
			}
			return buffer.toString();
		}
		return "";
	}
	
	/**
     * 获取异常的具体信息
     *
     */
    public static String getExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try{
            e.printStackTrace(new PrintWriter(sw));
        }finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("${symbol_escape}${symbol_escape}${symbol_dollar}","T");
    }
    
    /**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}
	
	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}
    
	/**
	 * 去掉指定后缀
	 * 
	 * @param str 字符串
	 * @param suffix 后缀
	 * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
	 */
	public static String removeSuffix(String str, String suffix) {
		if(!isValid(str) || !isValid(suffix)){
			return str;
		}
		
		if (str.endsWith(suffix)) {
			return str.substring(0, str.length() - suffix.length());
		}
		return str;
	}
	
	// 随机生成16位字符串
	public static String getRandomStr() {
			String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 16; i++) {
				int number = random.nextInt(base.length());
				sb.append(base.charAt(number));
			}
			return sb.toString();
		}

		
	public static void main(String[] args) {
		System.out.println(getRandomStr(5));;
	}
}

