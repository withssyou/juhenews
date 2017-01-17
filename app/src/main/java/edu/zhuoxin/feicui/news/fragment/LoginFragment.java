package edu.zhuoxin.feicui.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.zhuoxin.feicui.news.R;
import edu.zhuoxin.feicui.news.app.App;
import edu.zhuoxin.feicui.news.entity.LoginInfo;
import edu.zhuoxin.feicui.news.net.UserManager;
import edu.zhuoxin.feicui.news.ui.UserActivity;

/**
 * Created by Administrator on 2017/1/17.
 */

public class LoginFragment extends Fragment {
    @BindView(R.id.fragment_login_uname)
    EditText name;
    @BindView(R.id.fragment_login_upass)
    EditText pwd;
    @BindView(R.id.fragment_login_comit)
    Button login;
    @BindView(R.id.fragment_login_register)
    Button register;

    private String uname;
    private String upass;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case App.FALILER:
                        Toast.makeText(getContext(), "请检查网络连接设置", Toast.LENGTH_SHORT).show();
                        break;
                    case App.SUCCEED:
                        LoginInfo info = (LoginInfo) msg.obj;
                        LoginInfo.LoginData data = info.getData();
                        switch (data.getResult()) {
                            case 0:
                                Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("token",data.getToken());
                                intent.putExtra("name",uname);
                                getActivity().setResult(App.RESULTCODE,intent);
                                //当前activity消失
                                getActivity().finish();
                                break;
                            case -1:
                                Toast.makeText(getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                break;
                            case -2:
                                Toast.makeText(getContext(), "限制登陆(禁言,封IP)", Toast.LENGTH_SHORT).show();
                                break;
                            case -3:
                                Toast.makeText(getContext(), "限制登陆(异地登陆等异常)", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.fragment_login_register, R.id.fragment_login_comit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_comit:
                //获取用户输入
                uname = name.getText().toString().trim();
                upass = pwd.getText().toString().trim();
                //判断用户输入是否合法
                if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(upass)) {
                    Toast.makeText(getContext(), "请输入完整用户名和密码", Toast.LENGTH_SHORT).show();
                } else {
                    //进行网络请求，服务器验证登录是否成功
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserManager.login(uname, upass, handler);
                        }
                    }).start();
                }
                break;
            case R.id.fragment_login_register: //点击注册按钮
                UserActivity activity = (UserActivity) getActivity();
                activity.setTargetFragment("RegisterFragment");
                break;
//            case R.id.fragment_login_forgot: //点击密码找回
//                UserActivity activity = (UserActivity) getActivity();
//                activity.setTargetFragment("ForgotFragment");
//                break;
        }
    }
}
