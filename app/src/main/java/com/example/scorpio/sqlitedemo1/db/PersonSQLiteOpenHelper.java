package com.example.scorpio.sqlitedemo1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库帮助类，用于创建和管理数据库
 * Created by Scorpio on 16/1/18.
 */
public class PersonSQLiteOpenHelper extends SQLiteOpenHelper {
    private String TAG;

    /*数据库的构造函数
    * @param content
    * 
    * name 数据库名称
    * factory 游标工程
    * version 数据库的版本号，不可以小于1*/

    public PersonSQLiteOpenHelper(Context context) {
        
        super(context, "Scorpio.db", null, 2);
    }


    /*数据库第一次创建时回调此方法
    * 初始化一些表*/
    @Override
    public void onCreate(SQLiteDatabase db) {
    //操作数据库
        String sql="Create table person( _id integer primary key,name varchar(20),age integer);";
        db.execSQL(sql);//创建person类
    }

    /*数据库的版本号更新时回调此方法，
    * 更新数据库的内容(删除表，添加表，修改表)*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion==1&&newVersion==2){
            Log.i(TAG,"数据库更新啦");
        //在person表中添加一个余额列balance
            db.execSQL("alter table person add balance integer;");
            
        }
    }
}
 