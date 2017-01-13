package edu.zhuoxin.feicui.news.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/1/12.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_NEWS = "CREATE TABLE News (newsURL text,imageURL text," +
            "date text,title text,author_name text)";
    public static final String DB_NAME = "db.db";
    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表（newsInfo 中有什么字段，数据库中就要有什么字段）
        db.execSQL(CREATE_NEWS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
