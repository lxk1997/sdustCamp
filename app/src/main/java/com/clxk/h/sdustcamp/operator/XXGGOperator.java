package com.clxk.h.sdustcamp.operator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.UpdatingsXXGG;
import com.clxk.h.sdustcamp.utils.SdkdUpdatingsSQLUtils;

import java.util.ArrayList;

public class XXGGOperator {

    private SdkdUpdatingsSQLUtils mySQLiteUtil;//辅助数据库
    private SQLiteDatabase db;//数据库

    public XXGGOperator(Context context) {
        mySQLiteUtil = new SdkdUpdatingsSQLUtils(context, "xxgg_db.db", null,1);
        db = mySQLiteUtil.getWritableDatabase();
    }

    //add
    public void add(String title, String time, String content) {
        Log.i("123","aaaaa");
        db.execSQL("insert into xxgg_db values(null,?,?,?)", new Object[] {title,time,content});
    }

    //update
    public void update(UpdatingsXXGG node, int id) {
        db.execSQL("update xxgg_db set title=?, time=?, content=? where _id=?",new Object[] {node.getTitle(),node.getTime()
               ,node.getContent(), id+""});
    }

    //delete
    public void delete(Object id) {
        db.execSQL("delete from xxgg_db where _id=?", new Object[] {id});

    }

    //deleteAll
    public void deleteAll() {
        db.execSQL("delete from xxgg_db");
    }

    //search
    public boolean search(String title, String time, String content) {
        Cursor cursor = db.rawQuery("select * from xxgg_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(title) && cursor.getString(2).equals(time) && cursor.getString(3).equals(content))
                return true;
        }
        cursor.close();
        return false;
    }

    //search
    public int getId(String title, String time, String content) {
        Cursor cursor = db.rawQuery("select * from xxgg_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(title) && cursor.getString(2).equals(time) && cursor.getString(3).equals(content))
                return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    //query
    public UpdatingsXXGG query(int id) {
        UpdatingsXXGG node = new UpdatingsXXGG();
        Cursor cursor = db.rawQuery("select * from xxgg_db where _id=?", new String[] {id+""});
        while (cursor.moveToNext()) {
            node.setTitle(cursor.getString(1));
            node.setTime(cursor.getString(2));
            node.setContent(cursor.getString(3));
        }
        cursor.close();
        return node;
    }

    //query all text
    public ArrayList<UpdatingsXXGG> queryAll() {
        ArrayList<UpdatingsXXGG> records = new ArrayList<UpdatingsXXGG>();
        Cursor cursor = db.rawQuery("select * from xxgg_db", null);
        while (cursor.moveToNext()) {
            UpdatingsXXGG node = new UpdatingsXXGG();
            node.setTitle(cursor.getString(1));
            node.setTime(cursor.getString(2));
            node.setContent(cursor.getString(3));
            records.add(node);
        }
        cursor.close();
        return records;
    }
}
