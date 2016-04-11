package com.example.scorpio.sqlitedemo1.test;

import android.test.AndroidTestCase;
import android.util.Log;


import com.example.scorpio.sqlitedemo1.dao.PersonDAO2;
import com.example.scorpio.sqlitedemo1.db.PersonSQLiteOpenHelper;
import com.example.scorpio.sqlitedemo1.entities.Person;

import java.util.List;

/**
 * Created by Scorpio on 16/1/19.
 */
public class TestCase2 extends AndroidTestCase{

    private static final String TAG="TestCase2";

    public void test(){
        //数据库什么时候创建
        PersonSQLiteOpenHelper openHelper=new PersonSQLiteOpenHelper(getContext());
        
        //第一次连接数据库时创建数据库文件，onCreate会被调用
        openHelper.getReadableDatabase();
    }
    
    public void testInsert(){
        PersonDAO2 dao=new PersonDAO2(getContext());
        
        dao.insert(new Person(0, "周期", 28));
    }
    
    public void testDelete(){
        PersonDAO2 dao=new PersonDAO2(getContext());
        dao.delete(2);
    }

    public void testUpdata(){
        PersonDAO2 dao=new PersonDAO2(getContext());
        dao.update(3, "fengnjie");  
    }

    public void testQueryAll(){
        PersonDAO2 dao=new PersonDAO2(getContext());
        
        List<Person> personList=dao.queryAll();
        
        for(Person person : personList){
            Log.i(TAG, person.toString());
        }
    }
    
    public void testQueryItem(){
        PersonDAO2 dao =new PersonDAO2(getContext());
        Person person=dao.queryItem(4);
        Log.i(TAG, person.toString());
    }
    
}
