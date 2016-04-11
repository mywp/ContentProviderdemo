package com.example.scorpio.sqlitedemo1.test;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.scorpio.sqlitedemo1.dao.PersonDAO;
import com.example.scorpio.sqlitedemo1.db.PersonSQLiteOpenHelper;
import com.example.scorpio.sqlitedemo1.entities.Person;

import java.util.List;

/**
 * Created by Scorpio on 16/1/19.
 */
public class TestCase extends AndroidTestCase {

    private static final String TAG = "TestCase";

    public void test() {
        //数据库什么时候创建
        PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());

        //第一次连接数据库时创建数据库文件，onCreate会被调用
        openHelper.getReadableDatabase();
    }

    public void testInsert() {
        PersonDAO dao = new PersonDAO(getContext());

        for (int i=0;i<20;i++){
            dao.insert(new Person(0, "冠希"+i, 10+i));

        }
    }

    public void testDelete() {
        PersonDAO dao = new PersonDAO(getContext());
        dao.delete(1);
    }

    public void testUpdata() {
        PersonDAO dao = new PersonDAO(getContext());
        dao.update(1, "凤姐");
    }

    public void testQueryAll() {
        PersonDAO dao = new PersonDAO(getContext());

        List<Person> personList = dao.queryAll();

        for (Person person : personList) {
            Log.i(TAG, person.toString());
        }
    }

    public void testQueryItem() {
        PersonDAO dao = new PersonDAO(getContext());
        Person person = dao.queryItem(1);
        Log.i(TAG, person.toString());
    }

    public void testTransaction() {
        PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        if (db.isOpen()) {

            try {
                //开启事务
                db.beginTransaction();

                //1.从张三账户中扣1000块钱
                db.execSQL("update person set balance = balance -1000 where name ='zhangsan';");

                //ATM机，挂掉了
                // int result =10 /0;

                //2.从李四账户中加1000块钱
                db.execSQL("update person set balance = balance +1000 where name ='lisi';");

                //标记事务成功
                db.setTransactionSuccessful();
            } finally {
                //停止事务
                db.endTransaction();
            }

            db.close();
        }
    }

    public void testTransactionInsert() {
        PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        if (db.isOpen()) {

            //1.记住当前的时间
            long start = System.currentTimeMillis();

            //2.开始添加数据
            try {
                db.beginTransaction();
                for (int i = 0; i < 10000; i++) {
                    db.execSQL("insert into person(name,age,balance) values('wang" + i + "'," + (10 + i) + "," + (10000 + i) + ")");
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }

            //3.记住结束时间
            long end = System.currentTimeMillis();

            long diff = end - start;
            Log.i(TAG, "耗时：" + diff + "毫秒");

            db.close();
        }
    }
}
