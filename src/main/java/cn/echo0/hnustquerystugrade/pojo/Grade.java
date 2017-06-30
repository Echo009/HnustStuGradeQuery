/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 30, 2017 12:50:20 PM
 */
package cn.echo0.hnustquerystugrade.pojo;

/**
 *
 * @author Ech0
 */
public class Grade {
    //开课学期
    //课程名称
    //分数  （等级或者分数）
    //课程性质
    //课程类别
    //学分
    private String semester;
    private String courseName;
    private String grade;
    private String courseNature;
    private String courseCategory;
    private String credit;

    public Grade() {
    }
    
    public Grade(String[] properties) {
        this();
        if (properties == null || properties.length != 6) {
            return;
        }
        this.semester = properties[0];
        this.courseName = properties[1];
        this.grade = properties[2];
        this.courseNature = properties[3];
        this.courseCategory = properties[4];
        this.credit = properties[5];
    }
    
    public Grade(String semester, String courseName, String grade, String courseNature, String courseCategory, String credit) {
        this.semester = semester;
        this.courseName = courseName;
        this.grade = grade;
        this.courseNature = courseNature;
        this.courseCategory = courseCategory;
        this.credit = credit;
    }
    
    
    @Override
    public String toString() {
        return "Grades{" + "semester=" + semester + ", courseName=" + courseName + ", grade=" + grade + ", courseNature=" + courseNature + ", courseCategory=" + courseCategory + ", credit=" + credit + '}';
    }

    
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourseNature() {
        return courseNature;
    }

    public void setCourseNature(String courseNature) {
        this.courseNature = courseNature;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
    
}
