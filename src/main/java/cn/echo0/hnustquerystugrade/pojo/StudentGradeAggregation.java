/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 30, 2017 12:55:42 PM
 */
package cn.echo0.hnustquerystugrade.pojo;

import java.util.LinkedList;

/**
 *
 * @author Ech0
 */
public class StudentGradeAggregation {

    private String studentId;
    private String stuName;
    private final LinkedList<Grade> gradeList = new LinkedList<>();
    
    public StudentGradeAggregation(){
    }

    public StudentGradeAggregation(String studentId, String stuName) {
        this.studentId = studentId;
        this.stuName = stuName;
    }
    
    public void addGradeToList(Grade grade){
        gradeList.add(grade);
    }
    
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public String toString() {
        return "StudentGradeAggregation{" + "studentId=" + studentId + ", stuName=" + stuName + ", gradeList=" + gradeList + '}';
    }
    
}
