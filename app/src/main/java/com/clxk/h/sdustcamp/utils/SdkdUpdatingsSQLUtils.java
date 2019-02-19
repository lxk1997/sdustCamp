package com.clxk.h.sdustcamp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SdkdUpdatingsSQLUtils extends SQLiteOpenHelper {

    public static final String CREATE_TABLE_KDYW="create table kdyw_db(_id integer primary key autoincrement,title,context,time,image)";
    public static final String CREATE_TABLE_SPKD="create table spkd_db(_id integer primary key autoincrement,image,title,time,href)";
    public static final String CREATE_TABLE_XXGG="create table xxgg_db(_id integer primary key autoincrement,title,time,content)";
    private Context mContext;
    public SdkdUpdatingsSQLUtils(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_KDYW);
        db.execSQL(CREATE_TABLE_SPKD);
        db.execSQL(CREATE_TABLE_XXGG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
