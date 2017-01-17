package edu.zhuoxin.feicui.news.app;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/1/5.
 *  全局唯一，并且优先于所有人初始化
 */

public class App extends Application {
    //网络访问接口
    public static final String BASE_URL = "http://v.juhe.cn/toutiao/index?type=";
    public static final String APP_KRY = "&key=db3cab4bb2c89b9bb03ad01e5f1c9143";

    public static final String IMAGE_BASE = "http://gank.io/";
    public static final String IMAGE_PATH = "api/data/福利/10/1";
    //用户管理的baseurl
    public static final String USER_BASE = "http://118.244.212.82:9092/newsClient/";

    //带返回值的跳转的请求码和响应码
    public static final int REQUESTCODE = 0x110;
    public static final int RESULTCODE = 0x112;


    //接口回调的状态码
    public static  final  int SUCCEED = 0x10;
    public static  final  int FALILER = 0x12;
    public static  final  int EXCEPTION = 0x14;
    //handler发送消息的what
    public static  final  int IMAGR_LOAG = 0x16;



    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this,"1ac70764b3400");
    }
}
