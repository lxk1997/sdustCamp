package com.clxk.h.sdustcamp.operator;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.TimeTable;
import com.clxk.h.sdustcamp.utils.MySQLiteUtil;

public class MySQLiteOperator {

    private MySQLiteUtil mySQLiteUtil;//辅助数据库
    private SQLiteDatabase db;//数据库

    public MySQLiteOperator(Context context) {
        mySQLiteUtil = new MySQLiteUtil(context, "timetable.db", null,1);
        db = mySQLiteUtil.getWritableDatabase();
    }

    //add
    public void add(String className, String classRoom, String teacher, String classStart, String classEnd, String classDay, String classNum) {
        db.execSQL("insert into user_tb values(null,?,?,?,?,?,?,?)", new Object[] {className.toString(), classRoom.toString(), teacher.toString(), classStart.toString(), classEnd.toString(), classDay.toString(), classNum.toString()});
    }

    //update
    public void update(TimeTable node, int id) {
        db.execSQL("update user_tb set className=?,classRoom=?,teacher=?,classStart=?,classEnd=?,classDay=?,classNum=? where _id=?",new Object[] {node.getClassName(), node.getClassRoom(),
                node.getTeacher(), node.getClassStart(),node.getClassEnd(),node.getClassDay(), node.getClassNum(), id+""});
    }

    //delete
    public void delete(Object id) {
        db.execSQL("delete from user_tb where _id=?", new Object[] {id});

    }

    //search
    public boolean search(String className, String classRoom, String teacher, String classStart, String classEnd, String classDay, String classNum) {
        Cursor cursor = db.rawQuery("select * from user_tb", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(className.toString()) &&  cursor.getString(2).equals(classRoom.toString()) && cursor.getString(3).equals(teacher.toString())
                    && cursor.getString(4).equals(classStart.toString()) && cursor.getString(5).equals(classEnd.toString()) && cursor.getString(6).equals(classDay.toString())
                    && cursor.getString(7).equals(classNum.toString()))
                return true;
        }
        cursor.close();
        return false;
    }

    //search
    public int getId(String className, String classRoom, String teacher, String classStart, String classEnd, String classDay, String classNum) {
        Cursor cursor = db.rawQuery("select * from user_tb", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(className.toString()) &&  cursor.getString(2).equals(classRoom.toString()) && cursor.getString(3).equals(teacher.toString())
                    && cursor.getString(4).equals(classStart.toString()) && cursor.getString(5).equals(classEnd.toString()) && cursor.getString(6).equals(classDay.toString())
                    && cursor.getString(7).equals(classNum.toString()))
                return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    //query
    public TimeTable query(int id) {
        TimeTable node = new TimeTable();
        Cursor cursor = db.rawQuery("select * from user_tb where _id=?", new String[] {id+""});
        while (cursor.moveToNext()) {
            node.setClassName(cursor.getString(1));
            node.setClassRoom(cursor.getString(2));
            node.setTeacher(cursor.getString(3));
            node.setClassStart(cursor.getString(4));
            node.setClassEnd(cursor.getString(5));
            node.setClassDay(cursor.getString(6));
            node.setClassNum(cursor.getString(7));
        }
        cursor.close();
        return node;
    }

    //query all text
    public ArrayList<TimeTable> queryAll() {
        ArrayList<TimeTable> records = new ArrayList<TimeTable>();
        Cursor cursor = db.rawQuery("select * from user_tb", null);
        while (cursor.moveToNext()) {
            TimeTable node = new TimeTable();
            node.setClassName(cursor.getString(1));
            node.setClassRoom(cursor.getString(2));
            node.setTeacher(cursor.getString(3));
            node.setClassStart(cursor.getString(4));
            node.setClassEnd(cursor.getString(5));
            node.setClassDay(cursor.getString(6));
            node.setClassNum(cursor.getString(7));
            records.add(node);
        }
        cursor.close();
        return records;
    }

}

