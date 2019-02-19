package com.clxk.h.sdustcamp.operator;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.clxk.h.sdustcamp.bean.Score;
import com.clxk.h.sdustcamp.utils.SdkdLoginSQLUtils;

/**
 * 本地的成绩数据库操作
 */
public class ScoreOperator {

    private SdkdLoginSQLUtils mySQLiteUtil;//辅助数据库
    private SQLiteDatabase db;//数据库

    public ScoreOperator(Context context) {
        mySQLiteUtil = new SdkdLoginSQLUtils(context, "scor_db", null,1);
        db = mySQLiteUtil.getWritableDatabase();
    }

    //add
    public void add(String term, String classname, String score, String scoretag
            , String classprop, String credit, String gpa, String testway, String remark) {
        db.execSQL("insert into scor_db values(null,?,?,?,?,?,?,?,?,?)", new Object[] {term,classname,score,scoretag,classprop,credit,gpa,testway,remark});
    }

    //update
    public void update(Score node, int id) {
        db.execSQL("update scor_db set term=?,className=?,score=?,scoretag=?,classprop=?, credit=?, gpa=?, testway=?,remark=? where _id=?",new Object[] {node.getTerm(),
                 node.getClassname(),node.getScore(),node.getScoretag(), node.getClassprop(), node.getCredit(), node.getGpa(), node.getTestway(), node.getRemark(), id+""});
    }

    //delete
    public void delete(Object id) {
        db.execSQL("delete from scor_db where _id=?", new Object[] {id});

    }

    //deleteAll
    public void deleteAll() {
        db.execSQL("delete from scor_db");
    }

    //search
    public boolean search(String term,String classname, String score, String scoretag
            , String classprop, String credit, String gpa, String testway, String remark) {
        Cursor cursor = db.rawQuery("select * from scor_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(term)
                    && cursor.getString(2).equals(classname) && cursor.getString(3).equals(score) && cursor.getString(4).equals(scoretag)
                    && cursor.getString(5).equals(classprop) && cursor.getString(6).equals(credit) && cursor.getString(7).equals(gpa)
                    && cursor.getString(8).equals(testway)&& cursor.getString(9).equals(remark))
                return true;
        }
        cursor.close();
        return false;
    }

    //search
    public int getId(String term,String classname, String score, String scoretag
            , String classprop, String credit, String gpa, String testway,String remark) {
        Cursor cursor = db.rawQuery("select * from scor_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(term)
                    && cursor.getString(2).equals(classname) && cursor.getString(3).equals(score) && cursor.getString(4).equals(scoretag)
                    && cursor.getString(5).equals(classprop) && cursor.getString(6).equals(credit) && cursor.getString(7).equals(gpa)
                    && cursor.getString(8).equals(testway)&& cursor.getString(9).equals(remark))
                return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    //query
    public Score query(int id) {
        Score node = new Score();
        Cursor cursor = db.rawQuery("select * from scor_db where _id=?", new String[] {id+""});
        while (cursor.moveToNext()) {
            node.setTerm(cursor.getString(1));
            node.setClassname(cursor.getString(2));
            node.setScore(cursor.getString(3));
            node.setScoretag(cursor.getString(4));
            node.setClassprop(cursor.getString(5));
            node.setCredit(cursor.getString(6));
            node.setGpa(cursor.getString(7));
            node.setTestway(cursor.getString(8));
            node.setRemark(cursor.getString(9));
        }
        cursor.close();
        return node;
    }

    //query all text
    public ArrayList<Score> queryAll() {
        ArrayList<Score> records = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from scor_db", null);
        while (cursor.moveToNext()) {
            Score node = new Score();
            node.setTerm(cursor.getString(1));
            node.setClassname(cursor.getString(2));
            node.setScore(cursor.getString(3));
            node.setScoretag(cursor.getString(4));
            node.setClassprop(cursor.getString(5));
            node.setCredit(cursor.getString(6));
            node.setGpa(cursor.getString(7));
            node.setTestway(cursor.getString(8));
            node.setRemark(cursor.getString(9));
            records.add(node);
        }
        cursor.close();
        return records;
    }

}

