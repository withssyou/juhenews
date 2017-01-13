package edu.zhuoxin.feicui.news.fragment;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.feicui.news.R;
import edu.zhuoxin.feicui.news.adapter.NewsAdapter;
import edu.zhuoxin.feicui.news.db.NewsDao;
import edu.zhuoxin.feicui.news.entity.NewsInfo;
import edu.zhuoxin.feicui.news.ui.NewsActivity;

/**
 * Created by Administrator on 2017/1/9.
 */

public class CollectionFragment extends Fragment {
    private List<NewsInfo> data ; //数据源
    private NewsAdapter adapter;
    private NewsDao dao;
    @BindView(R.id.fragment_collection_lv)
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection,container,false);
        ButterKnife.bind(this,view);
        dao = new NewsDao(getContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //查询数据库，显示数据
        data = dao.select();
        adapter = new NewsAdapter(getContext());
        adapter.setData(data);
        lv.setAdapter(adapter);

        //添加监听事件
               //跳转到newsActicity
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        //添加长按事件
        //弹出对话框，删除数据库中的该条数据
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //显示弹出对话框
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("是否删除");
//                builder.setMessage("---")
//                builder.setIcon();
                //设置积极按钮      参数一：按钮的名字   参数二：按钮的监听事件
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewsInfo info = adapter.getData().get(position);
                        //点击弹出对话框的确定按钮，将该数据从数据库中删除
                        dao.delete(info);
                        data.remove(info);
                        adapter.notifyDataSetChanged();
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
                return true;
            }
        });
    }
}
