package edu.zhuoxin.feicui.news.ui;

import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.zhuoxin.feicui.news.R;

/**
 * Created by Administrator on 2017/1/9.
 */

public class NewsActivity extends AppCompatActivity {
    @BindView(R.id.activity_news_iv)
    ImageView image;
    @BindView(R.id.acticity_news_wv)
    WebView wv;
    @BindView(R.id.activity_news_toolbar)
    Toolbar toolbar;
    private String title;
    private String url;
    private String imageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        //获取上个页面传递的值
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("imageUrl");
        //设置Actionbar
        setSupportActionBar(toolbar);   //设置actionbar为toolbar
        getSupportActionBar().setTitle(title);//给toolbar赋值
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置回退按钮可用

        //设置图片
        Picasso.with(this)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(image);

        //设置webview显示网页
        wv.loadUrl(url);

    }

    /**
     * 设置浮动按钮的监听事件
     *
     * @param view
     */
    @OnClick(R.id.fab)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
//                Snackbar.make(view, "Replace your own action", Snackbar.LENGTH_LONG)
//                        .setAction("action", null).show();
                showShare();
                break;
        }
    }
    /**
     * shareSDK 一键分享的方法
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("哈哈");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(imageUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("这是一个不错的应用，我很喜欢");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(this);
    }
}
