package com.clxk.h.sdustcamp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MySQLiteUtil extends SQLiteOpenHelper {

    public static final String CREATE_TABLE_SQL_SCHEDULE = "create table schedule_db(_id integer primary key autoincrement,className,classRoom,teacher,classStart,classEnd,classDay,classNum,term)";
    public static final String CREATE_TABLE_SQL_SCORE = "create table scor_db(_id integer primary key autoincrement, term, classname, score, scoretag, classprop, credit, gpa, testway, remark)";
    private Context mContext;
    public MySQLiteUtil(Context context, String name, CursorFactory factory, int version
    ) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_SQL_SCHEDULE);
        db.execSQL(CREATE_TABLE_SQL_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}
