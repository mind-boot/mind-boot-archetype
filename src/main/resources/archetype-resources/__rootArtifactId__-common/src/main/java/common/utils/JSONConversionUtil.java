import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.common.utils;

/**
 * json转换工具类
 *
 * @author caoyong
 * @since 2018年11月29日 下午12:54:09
 */
@Slf4j
public class JSONConversionUtil {
    /**
     * 对象转string
     *
     * @param o 对象
     * @return 对象的json字符串
     */
    public static String objToString(Object o) {
        log.info("objToString start.");
        ObjectMapper om = new ObjectMapper();
        //非空
        om.setSerializationInclusion(Include.NON_NULL);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        StringWriter w = new StringWriter();
        try {
            om.writeValue(w, o);
            return w.toString();
        } catch (JsonGenerationException e) {
            log.info("objToString JsonGeneration error:", e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.info("objToString JsonMapping error:", e.getMessage(), e);
        } catch (IOException e) {
            log.info("objToString IO error:", e.getMessage(), e);
        } catch (Exception e) {
            log.error("objToString error:", e.getMessage(), e);
        }
        log.info("objToString end.");
        return "";
    }

    /**
     * json string 转对象
     *
     * @param content 对象json字符串
     * @param cls     类型
     * @return 对象
     */
    public static <T> T stringToObj(String content, Class<T> cls) {
        log.info("stringToObj start.");
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return om.readValue(content, cls);
        } catch (JsonParseException e) {
            log.info("stringToObj JsonParse error:", e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.info("stringToObj JsonMapping error:", e.getMessage(), e);
        } catch (IOException e) {
            log.info("stringToObj IO error:", e.getMessage(), e);
        } catch (Exception e) {
            log.error("stringToObj error:", e.getMessage(), e);
        }
        log.info("stringToObj end.");
        return null;
    }

    /**
     * 对象转换为List集合
     *
     * @param content 字符串
     * @param clazz   类
     * @param <T>     类型
     * @return List集合
     */
    public static <T> List<T> stringToList(String content, Class<T> clazz) {
        log.info("stringToList start, content:{}", content);
        List<T> list = null;
        try {
            JSONArray json = JSONArray.parseArray(content);
            T t;
            list = new ArrayList<>();
            for (Object obj : json) {
                JSONObject jsonObject = (JSONObject) obj;
                t = JSONObject.toJavaObject(jsonObject, clazz);
                list.add(t);
            }
        } catch (Exception e) {
            log.error("stringToList error:", e.getMessage(), e);
        }
        log.info("stringToList end.");
        return list;
    }

    /**
     * json转换成map对象
     *
     * @param strJSON
     * @return
     */
    public static Map<String, String> jsonToHashMap(String strJSON) {
        Map<String, String> dataMap = new HashMap<>();
        try {
            if (StringUtil.isValid(strJSON)) {
                JSONObject jsonObject = JSON.parseObject(strJSON);
                for (String key : jsonObject.keySet()) {
                    dataMap.put(key, jsonObject.getString(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * 对象转换成map
     */
    public static Map<String, String> objTOHashMap(Object o) {
        return jsonToHashMap(objToString(o));
    }

    /**
     * 对象转HashMap，基于反射
     *
     * @param obj          对象
     * @param ignoreFields 忽略字段
     * @return 结果
     */
    public static Map<String, String> objTOHashMap(Object obj, String... ignoreFields) {
        Map<String, String> map = new HashMap<>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (ignoreFields != null && Arrays.asList(ignoreFields).contains(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj).toString());
            }
        } catch (IllegalAccessException e) {
            log.info("praseParameterObj error:{}", e.getMessage(), e);
        }
        return map;
    }

    /**
     * 通过json对象中的key递归查找值
     *
     * @param object json任意格式
     * @param key    键
     * @return 对应string的值
     */
    public static String getValueByKeyFromJson(Object object, String key) {
        if (object == null || object == "") return null;
        Class<?> cls = object.getClass();
        if (cls == JSONObject.class) {
            JSONObject jo = (JSONObject) object;
            if (jo.containsKey(key)) {
                return jo.getString(key);
            }
            for (Object o : jo.values()) {
                String tmp = getValueByKeyFromJson(o, key);
                if (tmp != null) {
                    return tmp;
                }
            }
        } else if (cls == JSONArray.class) {
            JSONArray ja = (JSONArray) object;
            for (Object o : ja) {
                if (o != null && o != "") {
                    String tmp = getValueByKeyFromJson(o, key);
                    if (tmp != null) {
                        return tmp;
                    }
                }
            }
        } else if (cls == String.class) {
            try {
                Object o = JSON.parse((String) object);
                String tmp = getValueByKeyFromJson(o, key);
                if (tmp != null) {
                    return tmp;
                }
            } catch (JSONException ignored) {
            }
        }
        return null;
    }
}
