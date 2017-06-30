/*
 * Author : Echo0 
 * Email  : ech0.extreme@foxmail.com
 * Time   : Jun 30, 2017 11:12:46 AM
 */
package cn.echo0.hnustquerystugrade.util;

import cn.echo0.hnustquerystugrade.pojo.Grade;
import cn.echo0.hnustquerystugrade.pojo.StudentGradeAggregation;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Ech0
 */
public class ParseHtml {
//            3   学号 
//            4   姓名
//            5   开课学期
//            6   课程名称
//            7   分数  （等级或者分数）
//            9   课程性质
//            10 课程类别
//            12  学分

    /**
     * 从HTML中提取成绩信息 
     * @param htmlFile
     * @return json String like { "studentId": "1405020207", "stuName": "Echo0","gradeList": [] }
     * @throws IOException
     */
    public static String getGradeFromHtml(File htmlFile) throws IOException {
        if (htmlFile == null) {
            return "";
        }
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        Element table = doc.getElementById("mxh");
        Elements trs = table.getElementsByTag("tr");
        StudentGradeAggregation aggregation = new StudentGradeAggregation();
        String[] properties = new String[6];
        AtomicBoolean flag = new AtomicBoolean(true);
        trs.forEach( // traverse rows 
                (trE) -> {
                    if (flag.get()) {
                        flag.set(false);
                        int[] currentTdIndex = new int[1];
                        currentTdIndex[0] = 1;

                        setName:
                        trE.getElementsByTag("td").forEach( // setStudentId and setStuName
                        e -> {
                            if (currentTdIndex[0] == 3) {
                                aggregation.setStudentId(e.text());
                            } else if (currentTdIndex[0] == 4) {
                                aggregation.setStuName(e.text());
                            }
                            currentTdIndex[0]++;
                        });
                    }
                    int[] currentTdIndex = new int[1];
                    currentTdIndex[0] = 1;
                    int[] propertiesIndex = new int[1];
                    propertiesIndex[0] = 0;
                    Elements tds = trE.getElementsByTag("td");
                    tds.forEach(// traverse tds
                            tdE -> {
                                if (currentTdIndex[0] == 1 || currentTdIndex[0] == 2
                                || currentTdIndex[0] == 3 || currentTdIndex[0] == 4
                                || currentTdIndex[0] == 8 || currentTdIndex[0] == 11
                                || currentTdIndex[0] == 13 || currentTdIndex[0] == 14) { //skip
                                    currentTdIndex[0]++;
                                    return;
                                }
                                properties[propertiesIndex[0]++] = tdE.text().trim();
                                currentTdIndex[0]++;
                            }
                    );
                    Grade grade = new Grade(properties);
                    aggregation.addGradeToList(grade);
                }
        );
        return new GsonBuilder().create().toJson(aggregation);
    }
    public static String getGradeFromHtml(String htmlString) throws IOException {
        if (htmlString == null) {
            return "";
        }
        Document doc = Jsoup.parse(htmlString, "UTF-8");
        Element table = doc.getElementById("mxh");
        Elements trs = table.getElementsByTag("tr");
        StudentGradeAggregation aggregation = new StudentGradeAggregation();
        String[] properties = new String[6];
        AtomicBoolean flag = new AtomicBoolean(true);
        trs.forEach( // traverse rows 
                (trE) -> {
                    if (flag.get()) {
                        flag.set(false);
                        int[] currentTdIndex = new int[1];
                        currentTdIndex[0] = 1;

                        setName:
                        trE.getElementsByTag("td").forEach( // setStudentId and setStuName
                        e -> {
                            if (currentTdIndex[0] == 3) {
                                aggregation.setStudentId(e.text());
                            } else if (currentTdIndex[0] == 4) {
                                aggregation.setStuName(e.text());
                            }
                            currentTdIndex[0]++;
                        });
                    }
                    int[] currentTdIndex = new int[1];
                    currentTdIndex[0] = 1;
                    int[] propertiesIndex = new int[1];
                    propertiesIndex[0] = 0;
                    Elements tds = trE.getElementsByTag("td");
                    tds.forEach(// traverse tds
                            tdE -> {
                                if (currentTdIndex[0] == 1 || currentTdIndex[0] == 2
                                || currentTdIndex[0] == 3 || currentTdIndex[0] == 4
                                || currentTdIndex[0] == 8 || currentTdIndex[0] == 11
                                || currentTdIndex[0] == 13 || currentTdIndex[0] == 14) { //skip
                                    currentTdIndex[0]++;
                                    return;
                                }
                                properties[propertiesIndex[0]++] = tdE.text().trim();
                                currentTdIndex[0]++;
                            }
                    );
                    Grade grade = new Grade(properties);
                    aggregation.addGradeToList(grade);
                }
        );
        return new GsonBuilder().create().toJson(aggregation);
    }
}
