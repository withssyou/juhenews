package edu.zhuoxin.feicui.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.zhuoxin.feicui.news.R;
import edu.zhuoxin.feicui.news.app.App;
import edu.zhuoxin.feicui.news.fragment.CollectionFragment;
import edu.zhuoxin.feicui.news.fragment.ImageFragment;
import edu.zhuoxin.feicui.news.fragment.NewsFragment;
import edu.zhuoxin.feicui.news.fragment.ViewPagerFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ImageView user_image;
    TextView user_name;
    /**
     * 当前显示的fragment
     */
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取头部控件
//        bindView();
        //初始化系统控件
        initSystemViews();
        //默认显示新闻页面
        showNewsFragment();
    }


    /**
     * 系统控件初始化
     */
    private void initSystemViews() {
        //初始化toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //添加悬浮动作按钮的监听事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //设置侧滑的开关
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        ButterKnife.bind(this,drawer);
        View headView = navigationView.getHeaderView(0);
        //用户登录和头像
        user_image = (ImageView) headView.findViewById(R.id.imageView);
        user_name = (TextView) headView.findViewById(R.id.textView);

        user_name.setOnClickListener(this);
        user_image.setOnClickListener(this);
    }

    //重写按下返回键的方法
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //引入菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //菜单的监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Navigation每一项的监听事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //添加ViewPagerFragment
            //判断当前显示的fragment是否跟用户点击的fragment相同，不相同，添加，相同，吐司提示
            if (!(mCurrentFragment instanceof ViewPagerFragment)) {
                showNewsFragment();
            } else {
                Toast.makeText(this, "无需更新", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_gallery) {
            //添加图片fragment
            if (!(mCurrentFragment instanceof ImageFragment)) {
                showImageFragment();
            } else {
                Toast.makeText(this, "无需更新", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_slideshow) {
            //添加收藏fragment
            if (!(mCurrentFragment instanceof CollectionFragment)) {
                showCollectionFragment();
            } else {
                Toast.makeText(this, "无需更新", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            showShare();

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 显示新闻的fragment
     */
    private void showNewsFragment() {
        String[] type = new String[]{NewsFragment.TYPE_TOUTIAO, NewsFragment.TYPE_KEJI, NewsFragment.TYPE_GUOJI, NewsFragment.TYPE_SHEHUI};
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment(type);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.activity_home_fl, viewPagerFragment);
        mCurrentFragment = viewPagerFragment;
        ft.commit();
    }

    /**
     * 显示图片fragment
     */
    private void showImageFragment() {
        ImageFragment imageFragment = new ImageFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.activity_home_fl, imageFragment);
        mCurrentFragment = imageFragment;
        ft.commit();
    }

    /**
     * 显示收藏的fragment
     */
    private void showCollectionFragment() {
        CollectionFragment collectionFragment = new CollectionFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.activity_home_fl, collectionFragment);
        mCurrentFragment = collectionFragment;
        ft.commit();
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
        oks.setTitle("这是我的第一次分享");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://baidu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("------------------");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("这是一个不错的应用，我很喜欢");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                break;
            case R.id.textView:
                //判断是否是登录状态，登录状态不能点击，未登录状态跳转到登录页面
                if (user_name.getText().equals("请先登录")) {
                    startActivityForResult(new Intent(this, UserActivity.class), App.REQUESTCODE);
                } else {
                    user_name.setClickable(false);
                }
                Toast.makeText(this, "-----", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 用来处理返回值的方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String name = data.getStringExtra("name");
//            String token = data.getStringExtra("token");
            user_name.setText(name);
        }
    }
}
