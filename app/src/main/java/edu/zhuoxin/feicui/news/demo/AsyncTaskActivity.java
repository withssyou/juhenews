package edu.zhuoxin.feicui.news.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.zhuoxin.feicui.news.R;

/**
 * Created by Administrator on 2017/1/5.
 */

public class AsyncTaskActivity extends AppCompatActivity {

    @BindView(R.id.asynctask_pb)
    ProgressBar pb;
//    @BindView(R.id.asynctask_load)
//    Button download;
    @BindView(R.id.asynctask_iv)
    ImageView show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynctask_activity);
        ButterKnife.bind(this);
//        pb = (ProgressBar) findViewById(R.id.asynctask_pb);
        //创建异步任务对象，并启动异步任务，让异步任务帮我们更新UI
//        AsyncTaskDemo task = new AsyncTaskDemo(show,this);
//        task.execute("");
    }
    @OnClick(R.id.asynctask_load)
    public void onClick(View v){
        AsyncTaskDemo task = new AsyncTaskDemo(show,this);
        task.execute("http://i.meizitu.net/2013/08/131I0H42-3.jpg");
    }
}
