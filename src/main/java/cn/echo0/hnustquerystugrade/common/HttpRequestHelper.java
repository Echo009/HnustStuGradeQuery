/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : 2017年6月29日21:30:07
 */
package cn.echo0.hnustquerystugrade.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 *
 * @author Ech0
 */
/*
    发送Post 或者Get 请求
 */
public class HttpRequestHelper {

    public static String genParameterString(Properties properties) {
        if (properties==null||properties.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder(200);
        boolean[] flag = new boolean[1];//false 
        properties.forEach((key, value) -> {
            if (flag[0]) {
                result.append("&" + key + "=" + value);
            } else {
                flag[0] = true;
                result.append(key + "=" + value);
            }
        });
        return result.toString();
    }

    /**
     *
     * @param url 请求链接
     * @param requestProperties 请求头属性
     * @param parameters 参数
     * @param encodeType 编码类型
     * @return String 响应内容
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String sendGet(String url, Properties requestProperties, Properties parameters, String encodeType) throws MalformedURLException, IOException {
        if (parameters != null && !parameters.isEmpty()) {
            String parameterString = genParameterString(parameters);
            url += parameterString;
        }
        URL targetUrl = new URL(url);
        StringBuilder result = new StringBuilder(2 << 11);
        URLConnection conn = targetUrl.openConnection();
        // 设置通用的请求属性
        setGeneralPorperties(conn);
        //设置传入的请求属性
        if (requestProperties != null && !requestProperties.isEmpty()) {
            requestProperties.forEach((Object name, Object value) -> {
                conn.setRequestProperty((String) name, (String) value);
            });
        }
        //设置编码字符集
        conn.setRequestProperty("Accept-Charset", encodeType);
        conn.connect();
        //读取内容
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), encodeType)
        )) {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line + "\n");
            }
        }
        return result.toString().trim();
    }

    /**
     *
     * @param url 请求链接
     * @param requestProperties 请求头属性
     * @param parameters 参数
     * @param encodeType 编码类型
     * @return String 响应内容
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String sendPost(String url, Properties requestProperties, Properties parameters, String encodeType) throws MalformedURLException, IOException {
        URL targetUrl = new URL(url);
        StringBuilder result = new StringBuilder(2 << 11);
        URLConnection conn = targetUrl.openConnection();
        // 设置通用的请求属性
        setGeneralPorperties(conn);
        //设置传入的请求属性
        if (requestProperties != null&& !requestProperties.isEmpty()) {
            requestProperties.forEach((Object name, Object value) -> {
                conn.setRequestProperty((String) name, (String) value);
            });
        }
        //设置编码字符集
        conn.setRequestProperty("Accept-Charset", encodeType);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        //设置请求体参数
        try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
            String paramaterString = genParameterString(parameters);
            out.print(paramaterString);
            out.flush();
        }
        //读取内容
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), encodeType)
        )) {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line + "\n");
            }
        }
        return result.toString().trim();
    }

    public static void setGeneralPorperties(URLConnection conn) {
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729)");
    }
}
