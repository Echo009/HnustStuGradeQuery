/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 29, 2017 8:56:44 PM
 */
package cn.echo0.hnustquerystugrade.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.util.ImageHelper;

/**
 *  
 * @author Ech0
 */
public class VerifyCodeImgHelper {

    /**
     *
     * @param file 需要处理的验证码图片
     * @return BufferImage 去掉边框后灰化放大过后的图片
     */
    public static BufferedImage magnifyAndGrayImg(File file) {
        BufferedImage imageData = null;
        try( InputStream in = new FileInputStream(file)) {
            BufferedImage image = ImageIO.read(in);
            imageData = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(image, 3, 4, image.getWidth() - 20, image.getHeight() - 10));  //去边框 灰化
            imageData = ImageHelper.getScaledInstance(imageData, imageData.getWidth() * 5, imageData.getHeight() * 5);  //将图片扩大5倍
            imageData = ImageHelper.convertImageToBinary(imageData); //二值化
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    public static BufferedImage magnifyAndGrayImg(BufferedImage image) {
        image = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(image, 3, 4, image.getWidth() - 20, image.getHeight() - 10));  //去边框 灰化
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 5, image.getHeight() * 5);  //将图片扩大5倍
        image = ImageHelper.convertImageToBinary(image); //二值化
        return  image;
    }
}

