package edu.zhuoxin.feicui.news.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.feicui.news.R;
import edu.zhuoxin.feicui.news.adapter.ImageAdapter;
import edu.zhuoxin.feicui.news.app.App;
import edu.zhuoxin.feicui.news.entity.ImageInfo;
import edu.zhuoxin.feicui.news.entity.ImagetoGank;
import edu.zhuoxin.feicui.news.utils.HttpClientUtil;


/**
 * Created by Administrator on 2017/1/9.
 */

public class ImageFragment extends Fragment {
    @BindView(R.id.fragment_image_rv)
    RecyclerView rv;
    private List<ImageInfo> data = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null){
                switch (msg.what){
                    case App.IMAGR_LOAG:
                        ImagetoGank gank = (ImagetoGank) msg.obj;
                        data.addAll(gank.getResults());
                        adapter.notifyDataSetChanged();
                        break;
                    case 0x18:
                        Toast.makeText(getContext(),"网络异常",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    };
    private ImageAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image,container,false);
        ButterKnife.bind(this,view);
        
//        initData();
        //创建适配器
        adapter = new ImageAdapter(getContext());
        adapter.setList(data);
        //设置其中的每一项是否拥有固定大小。
        rv.setHasFixedSize(true);
        //给RecyclerView设置布局管理器  参数一: 显示几列  参数二:数据的排布方式
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        rv.setAdapter(adapter);
        return view;
    }

    private void initData() {
        for (int i = 0; i< 30 ;i++){
            data.add(new ImageInfo("http://f.hiphotos.baidu.com/image/pic/item" +
                    "/b151f8198618367ac7d2a1e92b738bd4b31ce5af.jpg"));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //网络访问获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClientUtil.getResult(App.IMAGE_BASE+App.IMAGE_PATH ,handler);
            }
        }).start();

    }
}
