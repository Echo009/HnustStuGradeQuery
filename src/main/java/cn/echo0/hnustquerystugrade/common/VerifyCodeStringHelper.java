/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 29, 2017 9:07:53 PM
 */
package cn.echo0.hnustquerystugrade.common;

/**
 *
 * @author Ech0
 */
public class VerifyCodeStringHelper {
       public static String handleResultString(String result){
        return  result.toLowerCase().replaceAll("[^a-z0-9]","");
    }
}
