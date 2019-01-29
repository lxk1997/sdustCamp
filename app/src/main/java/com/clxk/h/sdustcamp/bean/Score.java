package com.clxk.h.sdustcamp.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created on 19/1/29
 * 成绩对象类
 */
@SmartTable(name = "")
public class Score {

    @SmartColumn(id = 0, name = "序号")
    private String id;
    @SmartColumn(id = 1, name = "开课学期")
    private String term;
    @SmartColumn(id = 2, name = "课程编号")
    private String classId;
    @SmartColumn(id = 3, name = "课程名称")
    private String classname;
    @SmartColumn(id = 4, name = "成绩")
    private String score;
    @SmartColumn(id = 5, name = "成绩标志")
    private String scoretag;
    @SmartColumn(id = 6, name = "课程属性")
    private String classprop;
    @SmartColumn(id = 7, name = "学分")
    private String credit;
    @SmartColumn(id = 8, name = "绩点")
    private String gpa;
    @SmartColumn(id = 9, name = "考核方式")
    private String testway;
    @SmartColumn(id = 10, name = "辅修课程")
    private String minor;
    @SmartColumn(id = 11, name = "备注")
    private String remark;

    public Score() {

    }
    public Score(String id, String term,String classId, String classname, String score, String scoretag
    , String classprop, String credit, String gpa, String testway, String minor, String remark) {
        this.classId = classId;
        this.classname = classname;
        this.classprop = classprop;
        this.credit = credit;
        this.gpa = gpa;
        this.id = id;
        this.minor = minor;
        this.remark = remark;
        this.score = score;
        this.scoretag = scoretag;
        this.term = term;
        this.testway = testway;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setClassprop(String classprop) {
        this.classprop = classprop;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public void setScoretag(String scoretag) {
        this.scoretag = scoretag;
    }

    public void setTestway(String testway) {
        this.testway = testway;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTerm() {
        return term;
    }

    public String getId() {
        return id;
    }

    public String getClassname() {
        return classname;
    }

    public String getScore() {
        return score;
    }

    public String getClassId() {
        return classId;
    }

    public String getGpa() {
        return gpa;
    }

    public String getCredit() {
        return credit;
    }

    public String getClassprop() {
        return classprop;
    }

    public String getScoretag() {
        return scoretag;
    }

    public String getMinor() {
        return minor;
    }

    public String getRemark() {
        return remark;
    }

    public String getTestway() {
        return testway;
    }
}
