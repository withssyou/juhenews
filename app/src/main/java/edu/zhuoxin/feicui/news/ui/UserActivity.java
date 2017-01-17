package edu.zhuoxin.feicui.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import edu.zhuoxin.feicui.news.R;
import edu.zhuoxin.feicui.news.api.ShowWhatFragmentListener;
import edu.zhuoxin.feicui.news.fragment.LoginFragment;
import edu.zhuoxin.feicui.news.fragment.RegisterFragment;

/**
 * Created by Administrator on 2017/1/16.
 */
public class UserActivity extends AppCompatActivity implements ShowWhatFragmentListener {
    //    Fragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //默认显示登录的fragment
        showLoginFragment();

    }


    @Override
    public void setTargetFragment(String targetFragment) {
        switch (targetFragment) {
            case "RegisterFragment":
                showRegisterFragment();
                break;
            case "ForgotFragment":
                break;
        }
    }

    /**
     * 显示注册的fragment
     */
    private void showRegisterFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginfragment, registerFragment);
        ft.commit();
    }

    /**
     * 登录fragment
     */
    private void showLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginfragment, loginFragment);
        ft.commit();
    }

}
