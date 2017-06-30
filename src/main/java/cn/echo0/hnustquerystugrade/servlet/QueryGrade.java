/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 30, 2017 1:57:14 PM
 */
package cn.echo0.hnustquerystugrade.servlet;

import cn.echo0.hnustquerystugrade.pojo.User;
import cn.echo0.hnustquerystugrade.util.QueryGradeHelper;
import cn.echo0.hnustquerystugrade.util.ServletBeanUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Ech0
 */
@WebServlet(name = "doQueryGrade", urlPatterns = {"/doQueryGrade"})
public class QueryGrade extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
//   stuId;
//   password;
        User user = new User();
        ServletBeanUtil.populate(user, req);
        System.out.println(user.getStuId() + "  " + user.getPassword());
        try {
            String result = QueryGradeHelper.getGrade(user.getStuId(), user.getPassword());
            OutputStream out  = resp.getOutputStream();
            out.write(result.getBytes("utf-8"));
            out.flush();
        } catch (TesseractException | IOException ex) {
            Logger.getLogger(QueryGrade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
