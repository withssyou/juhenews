package edu.zhuoxin.feicui.news.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.feicui.news.R;

/**
 * Created by Administrator on 2017/1/7.
 * fragment:碎片
 * 使用方法：
 * 1.静态添加
 * 1）创建Fragment
 * 2) 在activity管理的布局中，添加fragment
 * 3) Activity管理布局
 * <p>
 * 2.动态添加
 */

public class FragmentDemoActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.shouye_rb)
    RadioButton shouye;
    @BindView(R.id.tuangou_rb)
    RadioButton tuangou;
    @BindView(R.id.wode_rb)
    RadioButton wode;
    @BindView(R.id.sousuo_rb)
    RadioButton sousuo;
    @BindColor(R.color.colorAccent)
    int press;
    @BindColor(R.color.colorPrimary)
    int nomal;
    @BindView(R.id.test_gr)
    RadioGroup rg;
    /**管理fragment的对象*/
    FragmentManager manamer;
    FragmentTransaction ft;
    private ShouyeFragment shouyeF;
    private SousuoFragment sousuoF;
    private WodeFragment wodeF;
    private TuangouFragment tuanfouF;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragmentdemo);
        setContentView(R.layout.activity_dianping);
        ButterKnife.bind(this);

        showFragmentShouye();
        rg.setOnCheckedChangeListener(this);
        Log.i("Tag", "=================onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Tag", "=================onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Tag", "=================onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Tag", "=================onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Tag", "=================onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Tag", "=================onDestroy");
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        tuangou.setTextColor(nomal);
        sousuo.setTextColor(nomal);
        wode.setTextColor(nomal);
        shouye.setTextColor(nomal);

        switch (checkedId) {
            case R.id.shouye_rb:
                shouye.setTextColor(press);
                showFragmentShouye();
                break;
            case R.id.tuangou_rb:
                tuangou.setTextColor(press);
                shouFragmenTuangou();
                break;
            case R.id.wode_rb:
                wode.setTextColor(press);
                shouFragmentWode();
                break;
            case R.id.sousuo_rb:
                sousuo.setTextColor(press);
                showFragmentSousuo();
                break;
        }
    }

    private void showFragmentSousuo() {
        ft = getSupportFragmentManager().beginTransaction();
        if (sousuoF == null){
            sousuoF = new SousuoFragment();
        }
        ft.replace(R.id.fragment_fl,sousuoF);
        hideFragmenyt(ft);
        ft.show(sousuoF);
        ft.commit();
    }

    private void shouFragmentWode() {
        ft = getSupportFragmentManager().beginTransaction();
        if (wodeF == null){
            wodeF = new WodeFragment();
        }
        ft.replace(R.id.fragment_fl,wodeF);
        hideFragmenyt(ft);
        ft.show(wodeF);
        ft.commit();
    }

    private void shouFragmenTuangou() {
        manamer = getSupportFragmentManager();
        ft = manamer.beginTransaction();
        if (tuanfouF == null){
            tuanfouF = new TuangouFragment();
        }
        ft.replace(R.id.fragment_fl,tuanfouF);
        hideFragmenyt(ft);
        ft.show(tuanfouF);
        ft.commit();
    }

    private void showFragmentShouye() {
        //获得fragment管理类对象
        manamer = getSupportFragmentManager();
        //开启事务管理
        ft = manamer.beginTransaction();
        //参数一：将fragment添加到哪个位置
        // 参数二：要添加的fragment
        if (shouyeF == null){
            shouyeF = new ShouyeFragment();
        }
        ft.replace(R.id.fragment_fl,shouyeF);
        //隐藏事务中所有fragment的方法
        hideFragmenyt(ft);
        //控制当前点击的按钮对应的fragment显示
        ft.show(shouyeF);
        ft.commit();
    }

    /**
     *  隐藏事务中的所有fragment
     * @param ft
     */
    private void hideFragmenyt(FragmentTransaction ft) {
        if (shouyeF != null){
            ft.hide(shouyeF);
        }
        if (wodeF != null){
            ft.hide(wodeF);
        }
        if (sousuoF != null){
            ft.hide(sousuoF);
        }
        if (tuanfouF != null){
            ft.hide(tuanfouF);
        }
    }
}
