package edu.zhuoxin.feicui.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.feicui.news.R;
/**
 * Created by Administrator on 2017/1/4.
 *  实现引导页面（ViewPager）
 */

public class GuideActivity extends AppCompatActivity {
    @BindView(R.id.test_tv)
    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
//        HttpClientUtil.getResult(App.IMAGE_BASE+App.IMAGE_PATH );
    }
}
