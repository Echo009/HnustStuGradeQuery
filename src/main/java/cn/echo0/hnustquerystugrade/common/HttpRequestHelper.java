/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : 2017��6��29��21:30:07
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
    ����Post ����Get ����
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
     * @param url ��������
     * @param requestProperties ����ͷ����
     * @param parameters ����
     * @param encodeType ��������
     * @return String ��Ӧ����
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
        // ����ͨ�õ���������
        setGeneralPorperties(conn);
        //���ô������������
        if (requestProperties != null && !requestProperties.isEmpty()) {
            requestProperties.forEach((Object name, Object value) -> {
                conn.setRequestProperty((String) name, (String) value);
            });
        }
        //���ñ����ַ���
        conn.setRequestProperty("Accept-Charset", encodeType);
        conn.connect();
        //��ȡ����
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
     * @param url ��������
     * @param requestProperties ����ͷ����
     * @param parameters ����
     * @param encodeType ��������
     * @return String ��Ӧ����
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String sendPost(String url, Properties requestProperties, Properties parameters, String encodeType) throws MalformedURLException, IOException {
        URL targetUrl = new URL(url);
        StringBuilder result = new StringBuilder(2 << 11);
        URLConnection conn = targetUrl.openConnection();
        // ����ͨ�õ���������
        setGeneralPorperties(conn);
        //���ô������������
        if (requestProperties != null&& !requestProperties.isEmpty()) {
            requestProperties.forEach((Object name, Object value) -> {
                conn.setRequestProperty((String) name, (String) value);
            });
        }
        //���ñ����ַ���
        conn.setRequestProperty("Accept-Charset", encodeType);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        //�������������
        try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
            String paramaterString = genParameterString(parameters);
            out.print(paramaterString);
            out.flush();
        }
        //��ȡ����
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
