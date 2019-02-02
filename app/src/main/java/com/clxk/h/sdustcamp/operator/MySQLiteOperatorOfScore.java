package com.clxk.h.sdustcamp.operator;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.Score;
import com.clxk.h.sdustcamp.bean.TimeTable;
import com.clxk.h.sdustcamp.utils.MySQLiteUtil;

/**
 * 本地的成绩数据库操作
 */
public class MySQLiteOperatorOfScore {

    private MySQLiteUtil mySQLiteUtil;//辅助数据库
    private SQLiteDatabase db;//数据库

    public MySQLiteOperatorOfScore(Context context) {
        mySQLiteUtil = new MySQLiteUtil(context, "score.db", null,1);
        db = mySQLiteUtil.getWritableDatabase();
    }

    //add
    public void add(String id, String term,String classId, String classname, String score, String scoretag
            , String classprop, String credit, String gpa, String testway, String minor, String remark) {
        db.execSQL("insert into score_db values(null,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {id,term,classId,classname,score,scoretag,classprop,credit,gpa,testway,minor,remark});
    }

    //update
    public void update(Score node, int id) {
        db.execSQL("update score_db set id=?,term=?,classId=?,className=?,score=?,scoretag=?,classprop=?, credit=?, gpa=?, testway=?,minor=?, remark=? where _id=?",new Object[] {node.getId(), node.getTerm(),
                node.getClassId(), node.getClassname(),node.getScore(),node.getScoretag(), node.getClassprop(), node.getCredit(), node.getGpa(), node.getTestway(), node.getMinor(), node.getRemark(), id+""});
    }

    //delete
    public void delete(Object id) {
        db.execSQL("delete from score_db where _id=?", new Object[] {id});

    }

    //deleteAll
    public void deleteAll() {
        db.execSQL("delete from score_db");
    }

    //search
    public boolean search(String id, String term,String classId, String classname, String score, String scoretag
            , String classprop, String credit, String gpa, String testway, String minor, String remark) {
        Cursor cursor = db.rawQuery("select * from score_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(id) &&  cursor.getString(2).equals(term) && cursor.getString(3).equals(classId)
                    && cursor.getString(4).equals(classname) && cursor.getString(5).equals(score) && cursor.getString(6).equals(scoretag)
                    && cursor.getString(7).equals(classprop) && cursor.getString(8).equals(credit) && cursor.getString(9).equals(gpa)
                    && cursor.getString(10).equals(testway) && cursor.getString(11).equals(minor) && cursor.getString(12).equals(remark))
                return true;
        }
        cursor.close();
        return false;
    }

    //search
    public int getId(String id, String term,String classId, String classname, String score, String scoretag
            , String classprop, String credit, String gpa, String testway, String minor, String remark) {
        Cursor cursor = db.rawQuery("select * from score_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(id) &&  cursor.getString(2).equals(term) && cursor.getString(3).equals(classId)
                    && cursor.getString(4).equals(classname) && cursor.getString(5).equals(score) && cursor.getString(6).equals(scoretag)
                    && cursor.getString(7).equals(classprop) && cursor.getString(8).equals(credit) && cursor.getString(9).equals(gpa)
                    && cursor.getString(10).equals(testway) && cursor.getString(11).equals(minor) && cursor.getString(12).equals(remark))
                return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    //query
    public Score query(int id) {
        Score node = new Score();
        Cursor cursor = db.rawQuery("select * from score_db where _id=?", new String[] {id+""});
        while (cursor.moveToNext()) {
            node.setId(cursor.getString(1));
            node.setTerm(cursor.getString(2));
            node.setClassId(cursor.getString(3));
            node.setClassname(cursor.getString(4));
            node.setScore(cursor.getString(5));
            node.setScoretag(cursor.getString(6));
            node.setClassprop(cursor.getString(7));
            node.setCredit(cursor.getString(8));
            node.setGpa(cursor.getString(9));
            node.setTestway(cursor.getString(10));
            node.setMinor(cursor.getString(11));
            node.setRemark(cursor.getString(12));
        }
        cursor.close();
        return node;
    }

    //query all text
    public ArrayList<Score> queryAll() {
        ArrayList<Score> records = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from score_db", null);
        while (cursor.moveToNext()) {
            Score node = new Score();
            node.setId(cursor.getString(1));
            node.setTerm(cursor.getString(2));
            node.setClassId(cursor.getString(3));
            node.setClassname(cursor.getString(4));
            node.setScore(cursor.getString(5));
            node.setScoretag(cursor.getString(6));
            node.setClassprop(cursor.getString(7));
            node.setCredit(cursor.getString(8));
            node.setGpa(cursor.getString(9));
            node.setTestway(cursor.getString(10));
            node.setMinor(cursor.getString(11));
            node.setRemark(cursor.getString(12));
            records.add(node);
        }
        cursor.close();
        return records;
    }

}

