package com.clxk.h.sdustcamp.operator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.UpdatingsSPKD;
import com.clxk.h.sdustcamp.utils.SdkdUpdatingsSQLUtils;

import java.util.ArrayList;

public class SPKDOperator {

    private SdkdUpdatingsSQLUtils mySQLiteUtil;//辅助数据库
    private SQLiteDatabase db;//数据库

    public SPKDOperator(Context context) {
        mySQLiteUtil = new SdkdUpdatingsSQLUtils(context, "spkd_db.db", null,1);
        db = mySQLiteUtil.getWritableDatabase();
    }

    //add
    public void add(String image, String title, String time, String href) {
        Log.i("123","aaaaa");
        db.execSQL("insert into spkd_db values(null,?,?,?,?)", new Object[] {image, title, time, href});
    }

    //update
    public void update(UpdatingsSPKD node, int id) {
        db.execSQL("update spkd_db set image=?, title=?, time=?, href=? where _id=?",new Object[] {node.getImage(),node.getTitle()
                ,node.getTime(),node.getHref(), id+""});
    }

    //delete
    public void delete(Object id) {
        db.execSQL("delete from spkd_db where _id=?", new Object[] {id});

    }

    //deleteAll
    public void deleteAll() {
        db.execSQL("delete from spkd_db");
    }

    //search
    public boolean search(String image, String title, String time, String href) {
        Cursor cursor = db.rawQuery("select * from spkd_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(image) && cursor.getString(2).equals(title) && cursor.getString(3).equals(time)
                    && cursor.getString(4).equals(href))
                return true;
        }
        cursor.close();
        return false;
    }

    //search
    public int getId(String image, String title, String time, String href) {
        Cursor cursor = db.rawQuery("select * from spkd_db", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).equals(image) && cursor.getString(2).equals(title) && cursor.getString(3).equals(time)
                    && cursor.getString(4).equals(href))
                return cursor.getInt(0);
        }
        cursor.close();
        return -1;
    }

    //query
    public UpdatingsSPKD query(int id) {
        UpdatingsSPKD node = new UpdatingsSPKD();
        Cursor cursor = db.rawQuery("select * from spkd_db where _id=?", new String[] {id+""});
        while (cursor.moveToNext()) {
            node.setImage(cursor.getString(1));
            node.setTitle(cursor.getString(2));
            node.setTime(cursor.getString(3));
            node.setHref(cursor.getString(4));
        }
        cursor.close();
        return node;
    }

    //query all text
    public ArrayList<UpdatingsSPKD> queryAll() {
        ArrayList<UpdatingsSPKD> records = new ArrayList<UpdatingsSPKD>();
        Cursor cursor = db.rawQuery("select * from spkd_db", null);
        while (cursor.moveToNext()) {
            UpdatingsSPKD node = new UpdatingsSPKD();
            node.setImage(cursor.getString(1));
            node.setTitle(cursor.getString(2));
            node.setTime(cursor.getString(3));
            node.setHref(cursor.getString(4));
            records.add(node);
        }
        cursor.close();
        return records;
    }
}
