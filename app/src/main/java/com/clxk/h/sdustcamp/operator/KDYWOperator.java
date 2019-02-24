package com.clxk.h.sdustcamp.operator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.UpdatingsKDYW;
import com.clxk.h.sdustcamp.utils.SdkdUpdatingsSQLUtils;

import java.util.ArrayList;

public class KDYWOperator {

    private SdkdUpdatingsSQLUtils mySQLiteUtil;//辅助数据库
    private SQLiteDatabase db;//数据库

    public KDYWOperator(Context context) {
        mySQLiteUtil = new SdkdUpdatingsSQLUtils(context, "kdyw_db", null,1);
        db = mySQLiteUtil.getWritableDatabase();
    }

    //add
    public void add(String title, String context, String time, String image) {
        Log.i("123","aaaaa");
        db.execSQL("insert into kdyw_db values(null,?,?,?,?)", new Object[] {title, context,time,image});
    }

    //update
    public void update(UpdatingsKDYW node, int id) {
        db.execSQL("update kdyw_db set title=?, context=?, time=?, image=? where _id=?",new Object[] {node.getTitle(),node.getContext()
                ,node.getTime(),node.getImage(), id+""});
    }

    //delete
    public void delete(Object id) {
        db.execSQL("delete from kdyw_db where _id=?", new Object[] {id});

    }

    //deleteAll
    public void deleteAll() {
        db.execSQL("delete from kdyw_db");
    }

    //search
    public boolean search(String title, String context, String time, String image) {
        Cursor cursor = db.rawQuery("select * from kdyw_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(title) && cursor.getString(2).equals(context) && cursor.getString(3).equals(time)
            && cursor.getString(4).equals(image))
                return true;
        }
        cursor.close();
        return false;
    }

    //search
    public int getId(String title, String context, String time, String image) {
        Cursor cursor = db.rawQuery("select * from kdyw_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(title) && cursor.getString(2).equals(context) && cursor.getString(3).equals(time)
                    && cursor.getString(4).equals(image))
                return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    //query
    public UpdatingsKDYW query(int id) {
        UpdatingsKDYW node = new UpdatingsKDYW();
        Cursor cursor = db.rawQuery("select * from kdyw_db where _id=?", new String[] {id+""});
        while (cursor.moveToNext()) {
            node.setTitle(cursor.getString(1));
            node.setContext(cursor.getString(2));
            node.setTime(cursor.getString(3));
            node.setImage(cursor.getString(4));
        }
        cursor.close();
        return node;
    }

    //query all text
    public ArrayList<UpdatingsKDYW> queryAll() {
        ArrayList<UpdatingsKDYW> records = new ArrayList<UpdatingsKDYW>();
        Cursor cursor = db.rawQuery("select * from kdyw_db", null);
        while (cursor.moveToNext()) {
            UpdatingsKDYW node = new UpdatingsKDYW();
            node.setTitle(cursor.getString(1));
            node.setContext(cursor.getString(2));
            node.setTime(cursor.getString(3));
            node.setImage(cursor.getString(4));
            records.add(node);
        }
        cursor.close();
        return records;
    }
}
