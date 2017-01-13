package edu.zhuoxin.feicui.news.api;

/**
 * Created by Administrator on 2017/1/5.
 */

public interface HttpClientListener {
    void getResultSucceed(String result);
    void getResultFailer(String result);
    void getResultExctption(Exception e);
}
