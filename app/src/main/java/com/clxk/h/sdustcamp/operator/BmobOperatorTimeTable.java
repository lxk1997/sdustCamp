package com.clxk.h.sdustcamp.operator;

import android.content.Context;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.TimeTable;
import com.clxk.h.sdustcamp.utils.ProperUtil;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobOperatorTimeTable {
    private static TimeTable node;
    private static Context context;
    private static MySQLiteOperator mySql;

    public BmobOperatorTimeTable() {
    }

    public void add(Context context, TimeTable node) {
        this.context = context;
        this.node = node;
        node.save(new SaveListener<String>() {

            @Override
            public void done(String arg0, BmobException arg1) {
                // TODO Auto-generated method stub
                if(arg1 == null) {
                    Log.i("数据插入成功", "1111");
                } else {
                    Log.i("数据插入失败", "1111");
                }
            }
        });
    }

    public void delete(Context context, TimeTable node) {
        this.context = context;
        this.node = node;
        node.delete(new UpdateListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null) {
                    Log.i("数据删除成功","22222");
                } else {
                    Log.i("数据删除失败","22222");
                }
            }
        });
    }

    public void update(Context context, TimeTable node) {
        this.context = context;
        this.node = node;
        node.update(new UpdateListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null) {
                    Log.i("数据更新成功","22222");
                } else {
                    Log.i("数据更新失败","22222");
                }
            }
        });
    }

    public void queryAll(Context context) {
        this.context = context;
        mySql = new MySQLiteOperator(context);
        BmobQuery<TimeTable> bmobQuery = new BmobQuery<TimeTable>();
        bmobQuery.findObjects(new FindListener<TimeTable>() {

            @Override
            public void done(List<TimeTable> object, BmobException arg1) {
                // TODO Auto-generated method stub
                if(arg1 == null) {
                    Log.i("查询成功：共" + object.size() + "条数据。","123");
                    File f = new File("/sdcard/configure.properties");
                    if(!f.exists()) {
                        ProperUtil.writeDateToLocalFile("is_loadInToMySql", "1");
                        for(TimeTable t: object) {
                            mySql.add(t.getClassName(), t.getClassRoom(), t.getTeacher(), t.getClassStart(), t.getClassEnd(), t.getClassDay(), t.getClassNum());
                        }
                    }
                } else {
                    Log.i("查询失败","123");
                }
            }
        });
    }
}
