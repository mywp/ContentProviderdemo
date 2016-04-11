package com.example.scorpio.sqlitedemo1.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.scorpio.sqlitedemo1.db.PersonSQLiteOpenHelper;

/**
 * Created by Scorpio on 16/1/22.
 */
public class PersonContentProvider extends ContentProvider {

    private static final String AUTHORITY = "PersonContentProvider";
    private static final int PRESON_INSERT_CODE = 0;//操作person表添加的操作的uri匹配码
    private static final int PRESON_DELETE_CODE = 1;
    private static final int PRESON_UPDATE_CODE = 2;
    private static final int PRESON_QUERY_ALL_CODE = 3;
    private static final int PRESON_QUERY_ITEM_CODE=4;

    private static UriMatcher uriMatcher;
    private PersonSQLiteOpenHelper mOpenHelper;//person表的数据库帮助对象

   

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //添加一些uri(分机号)

        //content://PersonContentProvider/person/insert
        uriMatcher.addURI(AUTHORITY, "person/insert", PRESON_INSERT_CODE);

        //content://PersonContentProvider/person/delete
        uriMatcher.addURI(AUTHORITY, "person/delete", PRESON_DELETE_CODE);

        //content://PersonContentProvider/person/update
        uriMatcher.addURI(AUTHORITY, "person/update", PRESON_UPDATE_CODE);

        //content://PersonContentProvider/person/queryAll
        uriMatcher.addURI(AUTHORITY, "person/queryAll", PRESON_QUERY_ALL_CODE);

        //content://PersonContentProvider/person/query/#
        uriMatcher.addURI(AUTHORITY, "person/query/#", PRESON_QUERY_ITEM_CODE);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PersonSQLiteOpenHelper(getContext());
        return true;//

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case PRESON_QUERY_ALL_CODE://查询所有人的uri
                
                if (db.isOpen()){
                    Cursor cursor=db.query("person",projection,selection,selectionArgs,null,null,sortOrder);
                    //db.close();返回cursor结果集时，不可以关闭数据库
                    return cursor;
                }
                break;
            case PRESON_QUERY_ITEM_CODE:
                if (db.isOpen()){
                    
                    long id = ContentUris.parseId(uri);
                    Cursor cursor=db.query("person",projection,"_id = ?",new String[]{ id+""},null,null,sortOrder);
                    return cursor;
                }
                break;
            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case PRESON_QUERY_ALL_CODE://返回多条的MINE-type
                return "vnd.android.cursor.dir/person";
            case PRESON_QUERY_ITEM_CODE://返回单条的MINE-type
                return "vnd.android.cursor.item/person";
            default:
                break;
        }
        
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (uriMatcher.match(uri)) {
            case PRESON_INSERT_CODE://添加人到person表中

                SQLiteDatabase db=mOpenHelper.getWritableDatabase();
                
                if (db.isOpen()){
                    
                    long id=db.insert("person",null,values);
                    
                    //通知内容观察者改变
                    getContext().getContentResolver().notifyChange(
                            Uri.parse("content://PersonContentProvider"),null);
                    
                    
                    db.close();
                    
                    return ContentUris.withAppendedId(uri,id);
                }

                break;
            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case PRESON_DELETE_CODE://删除表的操作
                SQLiteDatabase db=mOpenHelper.getWritableDatabase();
                if (db.isOpen()){
                    int count=db.delete("person",selection,selectionArgs);

                    //通知内容观察者改变
                    getContext().getContentResolver().notifyChange(
                            Uri.parse("content://PersonContentProvider"),null);
                    
                    db.close();
                    return count;
                }
                break;
            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case PRESON_UPDATE_CODE://更新表的操作
                SQLiteDatabase db=mOpenHelper.getWritableDatabase();
                if (db.isOpen()){
                    int count=db.update("person", values, selection, selectionArgs);

                    //通知内容观察者改变
                    getContext().getContentResolver().notifyChange(
                            Uri.parse("content://PersonContentProvider"),null);
                    
                    db.close();
                    return count;
                }
                break;
            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return 0;
    }
}
