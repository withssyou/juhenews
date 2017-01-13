package edu.zhuoxin.feicui.news.fragment;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.feicui.news.R;
import edu.zhuoxin.feicui.news.adapter.NewsAdapter;
import edu.zhuoxin.feicui.news.api.HttpClientListener;
import edu.zhuoxin.feicui.news.app.App;
import edu.zhuoxin.feicui.news.db.NewsDao;
import edu.zhuoxin.feicui.news.entity.NewsInfo;
import edu.zhuoxin.feicui.news.entity.NewstoJuhe;
import edu.zhuoxin.feicui.news.ui.NewsActivity;
import edu.zhuoxin.feicui.news.utils.HttpClientUtil;

/**
 * Created by Administrator on 2017/1/9.
 */

public class NewsFragment extends Fragment {
    public static final String TYPE_TOUTIAO = "top";
    public static final String TYPE_KEJI = "keji";
    public static final String TYPE_GUOJI = "guoji";
    public static final String TYPE_SHEHUI = "shehui";

    private NewsDao newsDao;
    private String type = "top";
    @BindView(R.id.fragment_news_lv)
    ListView listView;
    @BindView(R.id.fragment_news_flush)
    SwipeRefreshLayout refrush;
    List<NewsInfo> newsInfoList = new ArrayList<>();
    NewsAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (refrush.isRefreshing()) {  //如果progressbar当前正在显示
                refrush.setRefreshing(false);//设置progressbar隐藏
                Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
            }
            switch (msg.what) {
                case App.SUCCEED:
                    adapter.notifyDataSetChanged();
                    break;
                case App.FALILER:
                    Toast.makeText(getContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
                    break;
                case App.EXCEPTION:
                    Toast.makeText(getContext(), ((Exception) msg.obj).getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    /**
     * 网络访问的回调接口
     */
    HttpClientListener listener = new HttpClientListener() {
        @Override
        public void getResultSucceed(String result) {
            //解析数据
            Gson gson = new Gson();
            NewstoJuhe newstoJuhe = gson.fromJson(result, NewstoJuhe.class);
            //获取解析结果
            List<NewstoJuhe.NewsData> datas = newstoJuhe.getResult().getData();
            for (NewstoJuhe.NewsData info : datas) {
                //将解析好的数据添加到数据源中
                newsInfoList.add(new NewsInfo(info.getTitle(), info.getDate(), info.getAuthor_name(), info.getUrl(), info.getThumbnail_pic_s()));
            }
            Message message = handler.obtainMessage();
            message.what = App.SUCCEED;
            handler.sendMessage(message);
//            handler.sendEmptyMessage(App.SUCCEED);
        }

        @Override
        public void getResultFailer(String result) {
            Message message = handler.obtainMessage();
            message.what = App.FALILER;
            message.obj = result;
            handler.sendMessage(message);
        }

        @Override
        public void getResultExctption(Exception e) {
            Message message = Message.obtain();
            message.what = App.EXCEPTION;
            message.obj = e;
            handler.sendMessage(message);
        }
    };

    public NewsFragment(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        //设置下拉刷新
        refrush.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refrush.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initListDatas();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsDao = new NewsDao(getContext());

        adapter = new NewsAdapter(getContext());
        adapter.setData(newsInfoList);
        listView.setAdapter(adapter);
        initListDatas();
        //给listview设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View iv = view.findViewById(R.id.adapter_newsfragment_item_iv);
                //跳转到下个页面(带值）
                String title = adapter.getItem(position).getTitle();
                String imageUrl = adapter.getItem(position).getImageurl();
                String url = adapter.getItem(position).getUrl();

                Intent intent = new Intent(getContext(), NewsActivity.class);

                intent.putExtra("title", title);
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("url", url);

                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), iv, "newsImage").toBundle());

            }
        });
        //给listview设置长按事件  弹出对话框  将长按的该条数据存入数据库
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //显示弹出对话框
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("是否收藏");
//                builder.setMessage("---")
//                builder.setIcon();
                 //设置积极按钮      参数一：按钮的名字   参数二：按钮的监听事件
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击弹出对话框的确定按钮 ，将该条数据插入数据库中
                        boolean b = newsDao.insert(adapter.getItem(position));
                        if (b) {
                            Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "已收藏该内容，不能重复收藏", Toast.LENGTH_LONG).show();
                        }
                        //让对话框消失
                        dialog.dismiss();
                    }
                });
                //设置消极按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //创建对话框并显示
                builder.create().show();
                //true 中断点击事件继续向下传递    只响应长按事件
                //false  不中断                    响应了长按事件之后进而响应点击事件
                return true;
            }
        });
    }

    /**
     * 初始化数据源
     */
    private void initListDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClientUtil.getResult(new URL(App.BASE_URL + type + App.APP_KRY), listener);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
