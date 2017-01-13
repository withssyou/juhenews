package edu.zhuoxin.feicui.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.news.fragment.NewsFragment;

/**
 * Created by Administrator on 2017/1/9.
 */
public class PagerFragmentAdapter extends FragmentPagerAdapter{
    List<NewsFragment> data = new ArrayList<>();

    public PagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<NewsFragment> getData() {
        return data;
    }

    public void setData(List<NewsFragment> data) {
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
