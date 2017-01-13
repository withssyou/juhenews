package edu.zhuoxin.feicui.news.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class ImagetoGank {
    private String error;
    private List<ImageInfo> results ;

    public ImagetoGank(String error, List<ImageInfo> results) {
        this.error = error;
        this.results = results;
    }

    public String getError() {
        return error;
    }

    public List<ImageInfo> getResults() {
        return results;
    }



}
