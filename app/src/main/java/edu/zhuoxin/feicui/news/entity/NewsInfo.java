package edu.zhuoxin.feicui.news.entity;

/**
 * Created by Administrator on 2017/1/5.
 */

public class NewsInfo {
    String title ;
    String date;
    String author_name;
    String url ;
    String imageurl;

    public NewsInfo(String title, String date, String author_name, String url, String imageurl) {
        this.title = title;
        this.date = date;
        this.author_name = author_name;
        this.url = url;
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
