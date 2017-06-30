/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 29, 2017 10:52:17 PM
 */
package cn.echo0.hnustquerystugrade.util;

import cn.echo0.hnustquerystugrade.common.ApiUrl;
import cn.echo0.hnustquerystugrade.common.HttpRequestHelper;
import static cn.echo0.hnustquerystugrade.common.HttpRequestHelper.sendPost;
import static cn.echo0.hnustquerystugrade.util.VerifyCodeAndCookieIdHelper.getValidVerifyCodeAndSessionID;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Ech0
 */
public class QueryGradeHelper {
//    http://kdjw.hnust.cn/kdjw/Logon.do?method=logon
//    PASSWORD: ***
//    RANDOMCODE: xxc1
//    USERNAME: ***
//    if success then  the length of String returned should be 107 
//    if fail length = 9930 
    /**
     *
     * @param username
     * @param password
     * @return sessionId if success , else return ""
     * @throws IOException
     * @throws MalformedURLException
     * @throws TesseractException
     */
    public static String tryToLogin(String username, String password) throws IOException, MalformedURLException, TesseractException {
        String verifyCodeAndSessionId = getValidVerifyCodeAndSessionID();
        String verifyCode = verifyCodeAndSessionId.substring(0, 4);
        String sessionId = verifyCodeAndSessionId.substring(5);
        Properties requestProperties = new Properties();
        requestProperties.setProperty("Cookie", sessionId);
        Properties requestParameters = new Properties();
        requestParameters.setProperty("USERNAME", username);
        requestParameters.setProperty("PASSWORD", password);
        requestParameters.setProperty("RANDOMCODE", verifyCode);
        String result = sendPost(ApiUrl.LOGON, requestProperties, requestParameters, "utf-8");
        int tryTimes = 0;
        while (result.length() > 200 && tryTimes < 5) {//尝试五次
            //重新获取验证码
            verifyCodeAndSessionId = getValidVerifyCodeAndSessionID();
            verifyCode = verifyCodeAndSessionId.substring(0, 4);
            sessionId = verifyCodeAndSessionId.substring(5);
            //重新填充属性
            requestProperties.setProperty("Cookie", sessionId);
            requestParameters.setProperty("RANDOMCODE", verifyCode);
            tryTimes++;
            result = sendPost(ApiUrl.LOGON, requestProperties, requestParameters, "utf-8");
        }
        if (result.length() < 200) {
            return sessionId;
        } else {
            return "";
        }
    }
    public static String getGradeHtml(String sessionId) throws IOException{
        if(sessionId==null||sessionId.length()==0){
            return "";
        }
        Properties requestProperties = new Properties();
        requestProperties.setProperty("Cookie", sessionId);
        return HttpRequestHelper.sendGet(ApiUrl.QUERYGRADE, requestProperties, null, "UTF-8");
    }
    
    public static String getGrade(String stuId , String password ) throws IOException, MalformedURLException, TesseractException{
       return ParseHtml.getGradeFromHtml(getGradeHtml(tryToLogin(stuId, password)));
    }
    public static void main(String[] args) throws IOException, MalformedURLException, TesseractException {
        System.out.println(getGrade("1405020207", "200219"));
    }
}
