/*
 * Author : echo0 
 * Email   : ech0.extreme@foxmail.com
 * Time    : 2017Äê6ÔÂ30ÈÕ14:02:19 
 */
package cn.echo0.hnustquerystugrade.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 
 * @author Ech0
 */
public class ServletBeanUtil {
    /**
     * use request parameter populate bean 
     * @param bean
     * @param request 
     */
    public static void populate(Object bean, HttpServletRequest request) {
        Class<? extends Object> clazz = bean.getClass();
        Method ms[] = clazz.getDeclaredMethods();
        String mname, field, fieldType, value;
        for (Method m : ms) {
            mname = m.getName();
            if (!mname.startsWith("set") || ArrayUtils.isEmpty(m.getParameterTypes())) {
                continue;
            }
            try {
                field = mname.toLowerCase().charAt(3) + mname.substring(4, mname.length());
                value = request.getParameter(field);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                fieldType = m.getParameterTypes()[0].getName();
                //value -- > String 
                if (String.class.getName().equals(fieldType)) {
                    m.invoke(bean, value);
                } else if (Integer.class.getName().equals(fieldType) && NumberUtils.isDigits(value)) {
                    m.invoke(bean, Integer.valueOf(value));
                } else if (Short.class.getName().equals(fieldType) && NumberUtils.isDigits(value)) {
                    m.invoke(bean, Short.valueOf(value));
                } else if (Float.class.getName().equals(fieldType) && NumberUtils.isParsable(value)) {
                    m.invoke(bean, Float.valueOf(value));
                } else if (Double.class.getName().equals(fieldType) && NumberUtils.isParsable(value)) {
                    m.invoke(bean, Double.valueOf(value));
                } else if (Date.class.getName().equals(fieldType)) {
                    m.invoke(bean, DateUtils.parseDate(value, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"));
                } else {
                    m.invoke(bean, value);
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
