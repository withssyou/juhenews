package edu.zhuoxin.feicui.news.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.news.entity.NewsInfo;

/**
 * Created by Administrator on 2017/1/12.
 */

public class NewsDao {
    private MyOpenHelper helper;
    private SQLiteDatabase db;

    public NewsDao(Context context) {
        helper = new MyOpenHelper(context);
    }

    //写增删改查的方法
    public void init(){
        //打开数据库
        db = helper.getReadableDatabase();
    }
    //添加的方法
    public boolean insert(NewsInfo info){
        boolean isExist = isNewsExist(info);
        if (isExist){
            db.close();
            return false; //返回添加失败
        }else {
            ContentValues contentValues = new ContentValues();

            contentValues.put("newsURL", info.getUrl());
            contentValues.put("imageURL", info.getImageurl());
            contentValues.put("date", info.getDate());
            contentValues.put("title", info.getTitle());
            contentValues.put("author_name", info.getAuthor_name());

            db.insert("News", null, contentValues);
            db.close();
            return true;//返回添加成功
        }
    }
    //删除的方法
    public void delete( NewsInfo info) {
        init();
        //根据newsURL进行数据删除
        db.delete("News", "newsURL = ? ", new String[]{info.getUrl()});
        db.close();
    }
    //查询的方法
    public List<NewsInfo> select(){
        init();
        List<NewsInfo> list = new ArrayList<>();
        Cursor cursor = db.query("News", null, null, null, null, null, null);
        while (cursor.moveToNext()) {

            String newsURL = cursor.getString(cursor.getColumnIndex("newsURL"));
            String imageURL = cursor.getString(cursor.getColumnIndex("imageURL"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String author_name = cursor.getString(cursor.getColumnIndex("author_name"));

            NewsInfo newsInfo = new NewsInfo(title,date ,author_name,newsURL, imageURL);

            list.add(newsInfo);
        }
        return  list;
    }
    //判断是否存在
    public boolean isNewsExist( NewsInfo newsInfo) {
        init();
        Cursor cursor = db.query("News", null, "newsURL = ?", new String[]{newsInfo.getUrl()}, null, null, null);
        Log.i("Tag",newsInfo.getUrl());
        if (cursor.moveToFirst()) {
            return true; // 已经存在该数据
        } else {
            return false;//不存在
        }
    }




}
