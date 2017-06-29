/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 29, 2017 9:08:34 PM
 */
package cn.echo0.hnustquerystugrade.util;

import static cn.echo0.hnustquerystugrade.common.VerifyCodeStringHelper.handleResultString;
import static cn.echo0.hnustquerystugrade.util.VerifyCodeImgHelper.magnifyAndGrayImg;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Ech0
 */
public class VerifyCodeAndCookieIdHelper {
    public static String urlString = "http://kdjw.hnust.cn/kdjw/verifycode.servlet";
//    wall;JSESSIONID=B6C643FE4DC36E74A4E95C64E6E82C69
    public static String getVerifyCodeAndSessionID() throws MalformedURLException, IOException, TesseractException {
        ITesseract instance = new Tesseract();  // JNA Interface Mapping  
        instance.setLanguage("eng");
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //Set-Cookie: JSESSIONID=B6C643FE4DC36E74A4E95C64E6E82C69; Path=/kdjw
        String sessionID = conn.getHeaderField("Set-Cookie").split(";")[0];
        InputStream is = conn.getInputStream();
        BufferedImage image = ImageIO.read(is);
        image = magnifyAndGrayImg(image);
        return handleResultString(instance.doOCR(image))+";"+sessionID;
    }
    /**
     * 
     * @return  verifyCode and SessionID  , like : "321x;JSESSIONID=730CD7C1C8DF2C1941D165CF1E0A87CB"
     * @throws IOException
     * @throws MalformedURLException
     * @throws TesseractException 
     */
    public static String getValidVerifyCodeAndSessionID() throws IOException, MalformedURLException, TesseractException {
         String result = getVerifyCodeAndSessionID();
        while (result.charAt(4)!=';') {
            result = getVerifyCodeAndSessionID();
        }
        return result;
    }
    public static void main(String[] args) throws IOException, MalformedURLException, TesseractException {
        System.out.println(getValidVerifyCodeAndSessionID());
    }
}

