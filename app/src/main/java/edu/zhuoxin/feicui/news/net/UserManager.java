package edu.zhuoxin.feicui.news.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import edu.zhuoxin.feicui.news.app.App;
import edu.zhuoxin.feicui.news.entity.LoginInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/17.
 *  用户管理类
 */

public class UserManager {
    /**
     * 用户登录
     */
    public static void login(String name, String pwd , final Handler handler){
        String url = App.USER_BASE + "user_login?ver=1000000&uid="+name+"&pwd="+pwd+"&device=0";
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)  //设置请求的路径
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(App.FALILER);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("Tag",result);
                //解析
                Gson gson = new Gson();
                LoginInfo info = gson.fromJson(result,LoginInfo.class);

                Message msg = handler.obtainMessage();
                msg.what = App.SUCCEED;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        });

    }
    /**
     * 用户注册
     */
    public static void register(String name, String pwd ,String email, final Handler handler){

    }
    /**
     * 用户密码找回
     */
}
