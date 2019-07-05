#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xuzhongwei
 * @version 1.0.0
 * @since 2018-12-13 15:15
 * httpClient 封装方法
 **/
@Slf4j
public class HttpClientUtil {
    private static HttpClient client;

    static {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        int maxHostConnections = 100;
        params.setDefaultMaxConnectionsPerHost(maxHostConnections);
        int maxTotalConnections = 50;
        params.setMaxTotalConnections(maxTotalConnections);
        int connectionTimeOut = 60000;
        params.setConnectionTimeout(connectionTimeOut);
        params.setSoTimeout(connectionTimeOut);
        connectionManager.setParams(params);
        client = new HttpClient(connectionManager);
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    }

    /**
     * 使用get方式调用
     *
     * @param url    调用的URL
     * @param values 传递的参数值List
     * @return 调用得到的字符串
     */
    public static String httpClientGet(String url, NameValuePair[] values) {
        GetMethod getMethod = new GetMethod(url);
        //将表单的值放入getMethod中
        if (values != null && values.length > 0) {
            getMethod.setQueryString(values);
        }
        return getResponseStr(getMethod);
    }


    /**
     * 使用get方式调用
     *
     * @param url
     * @param values
     * @return
     */
    public static String httpClientGet(String url, Map<String, String> values) {
        return httpClientGet(url, praseParameterMap(values));
    }

    /**
     * 使用get方式调用(支持https)
     *
     * @param url   请求url
     * @param param 参数
     * @return 结果
     */
    public static String httpClientGet(String url, Object param) {
        try {
            String reqJSON = JSONConversionUtil.objToString(param);
            log.info("httpClientGet start, request url:{}, reqJSON:{}", url, reqJSON);
            DefaultHttpClient client = new SSLClient();
            //发送get请求
            URIBuilder builder = new URIBuilder(url);
            NameValuePair[] pairs = praseParameterObj(param, "serialVersionUID");
            if (pairs != null && pairs.length > 0) {
                for (NameValuePair pair : pairs) {
                    builder.setParameter(pair.getName(), pair.getValue());
                }
            }
            HttpGet request = new HttpGet(builder.build());
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            // 返回json格式
            String result = EntityUtils.toString(entity);
            if (statusCode == HttpStatus.SC_OK) {
                return result;
            } else {
                log.info("httpClientGetResponse error code:{}", statusCode);
            }
            log.info("httpClientGetResponse result:{}", result);
        } catch (Exception e) {
            log.error("httpClientGet error:{}", e.getMessage(), e);
        }
        return "";
    }


    /**
     * 将MAP转换成HTTP请求参数
     *
     * @param map
     * @return
     */
    public static NameValuePair[] praseParameterMap(Map<String, String> map) {

        NameValuePair[] nvps = new NameValuePair[map.size()];

        Set<String> keys = map.keySet();
        int i = 0;
        for (String key : keys) {
            nvps[i] = new NameValuePair();
            nvps[i].setName(key);
            nvps[i].setValue(map.get(key));

            i++;
        }

        return nvps;
    }

    /**
     * 对象转成HTTP请求参数
     *
     * @param obj          对象
     * @param ignoreFields 忽略字段
     * @return 请求参数
     */
    public static NameValuePair[] praseParameterObj(Object obj, String... ignoreFields) {
        try {
            if (obj != null) {
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                int ignore = ignoreFields == null ? 0 : ignoreFields.length;
                NameValuePair[] nvps = new NameValuePair[declaredFields.length - ignore];
                int i = 0;
                for (Field field : declaredFields) {
                    if (ignoreFields != null && Arrays.asList(ignoreFields).contains(field.getName())) {
                        continue;
                    }
                    field.setAccessible(true);
                    nvps[i] = new NameValuePair();
                    nvps[i].setName(field.getName());
                    nvps[i].setValue(field.get(obj).toString());
                    i++;
                }
                return nvps;
            }
        } catch (IllegalAccessException e) {
            log.info("praseParameterObj error:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 发送post或get请求获取响应信息
     *
     * @param method http请求类型,post或get请求
     * @return 服务器返回的信息
     */
    public static String getResponseStr(HttpMethodBase method) {
        StringBuilder sb = new StringBuilder();
        try {
            log.debug(String.format("[uri=%s, params=%s]", method.getURI(), method.getParams()));
            int statusCode = client.executeMethod(method);
            if (statusCode != 200) {
                log.error("HttpClient Error : statusCode = " + statusCode + ", uri :" + method.getURI());
                return "";
            }
            //以流的行式读入，避免中文乱码
            InputStream is = method.getResponseBodyAsStream();
            BufferedReader dis = null;
            if (StringUtil.isValid(method.getResponseCharSet())) {
                dis = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            } else {
                dis = new BufferedReader(new InputStreamReader(is, method.getResponseCharSet()));
            }
            String str = "";
            while ((str = dis.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            log.info("调用远程出错;发生网络异常!");
            e.printStackTrace();
        } finally {
            // 关闭连接
            method.releaseConnection();
        }
        return sb.toString();
    }


    /**
     * post请求
     *
     * @param url    请求url(支持https)
     * @param req    请求的参数对象
     * @param cls    返回的结果类型
     * @param <Resp> post返回对象泛型
     * @return 结果对象
     */

    public static <Resp> Resp doPost(String url, Object req, Class<Resp> cls) {
        Resp resp = null;
        try {
            DefaultHttpClient client = new SSLClient();
            HttpPost post = new HttpPost(url);
            String reqJSON = JSONConversionUtil.objToString(req);
            log.info("doPost start request url:{}, reqJSON:{}", url, reqJSON);
            StringEntity s = new StringEntity(reqJSON, "UTF-8");
            //发送json数据需要设置contentType
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            String result = EntityUtils.toString(entity);
            int statusCode = res.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                // 返回json格式
                resp = JSONConversionUtil.stringToObj(result, cls);
            } else {
                log.info("doPost http response code:{}", statusCode);
            }
            log.info("doPostResponse:{}", result);
        } catch (Exception e) {
            log.info("doPost error:{}", e.getMessage(), e);
        }
        return resp;
    }

    /**
     * get请求(支持https)
     *
     * @param url    请求url
     * @param param  请求参数
     * @param cls    返回类型
     * @param <Resp> get返回对象泛型
     * @return 结果对象
     */
    public static <Resp> Resp doGet(String url, Object param, Class<Resp> cls) {
        return JSONConversionUtil.stringToObj(httpClientGet(url, param), cls);
    }


    /**
     * get请求(支持https)
     *
     * @param url   请求url
     * @param param 请求参数
     */
    public static void doGet(String url, Object param) {
        String clientGet = httpClientGet(url, param);
        log.info("doGet resp:{}", clientGet);
    }

    /**
     * get请求(支持https)
     *
     * @param url    请求url
     * @param param  请求参数
     * @param cls    返回类型
     * @param <Resp> get返回对象泛型
     * @return 结果对象
     */
    public static <Resp> List<Resp> doGetList(String url, Object param, Class<Resp> cls) {
        return JSONConversionUtil.stringToList(httpClientGet(url, param), cls);
    }

}
