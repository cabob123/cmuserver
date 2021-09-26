package com.formssi.cas.util;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 基于HttpClient 封装post 和 get 请求
 *
 * @author zzg
 *
 */
public class HTTPUtil {
    public static String doGet(String url, Map<String, Object> map) {
        String strResult = "";
        // 4. 获取默认的client实例
        CloseableHttpClient client = HttpClients.createDefault();
        try {

            // 1.创建URIBuilder
            URIBuilder uriBuilder = new URIBuilder(url);

            // 2.设置请求参数
            if (map != null) {
                // 遍历请求参数
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    // 封装请求参数
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }

            // 3.创建请求对象httpGet
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            // 4.使用httpClient发起请求
            CloseableHttpResponse response = client.execute(httpGet);
            try {
                // 5. 获取响应entity
                HttpEntity respEntity = response.getEntity();
                strResult = EntityUtils.toString(respEntity, "UTF-8");
            } finally {
                // 6. 关闭响应对象
                response.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. 关闭连接，释放资源
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    /**
     * 普通POST提交
     *
     * @param url
     * @param map
     * @return
     */
    public static String doPost(String url, Map<String, Object> map) {
        String strResult = "";
        // 1. 获取默认的client实例
        CloseableHttpClient client = HttpClients.createDefault();
        // 2. 创建httppost实例
        HttpPost httpPost = new HttpPost(url);
        // 3. 创建参数队列（键值对列表）
        List<NameValuePair> paramPairs = new ArrayList<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            Object val = map.get(key);
            paramPairs.add(new BasicNameValuePair(key, val.toString()));
        }
        UrlEncodedFormEntity entity;
        try {
            // 4. 将参数设置到entity对象中
            entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
            // 5. 将entity对象设置到httppost对象中
            httpPost.setEntity(entity);
            // 6. 发送请求并回去响应
            CloseableHttpResponse resp = client.execute(httpPost);
            try {
                // 7. 获取响应entity
                HttpEntity respEntity = resp.getEntity();
                strResult = EntityUtils.toString(respEntity, "UTF-8");
            } finally {
                // 9. 关闭响应对象
                resp.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 10. 关闭连接，释放资源
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    public static String doDelete(String url, Map<String, Object> map) {
        String strResult = "";
        // 1. 获取默认的client实例
        CloseableHttpClient client = HttpClients.createDefault();

        try {
            // 2.创建URIBuilder
            StringBuilder builder = new StringBuilder();
            builder.append(url);
            // 3.设置请求参数
            if (map != null) {
                // 遍历请求参数
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    // 封装请求参数
                    builder.append("/").append(entry.getValue().toString());
                }
            }
            // 4. 创建httpDelete实例
            HttpDelete httpDelete = new HttpDelete(builder.toString());
            // 5.使用httpClient发起请求
            CloseableHttpResponse response = client.execute(httpDelete);
            try {
                // 5. 获取响应entity
                HttpEntity respEntity = response.getEntity();
                strResult = EntityUtils.toString(respEntity, "UTF-8");
            } finally {
                // 6. 关闭响应对象
                response.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. 关闭连接，释放资源
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }

}
