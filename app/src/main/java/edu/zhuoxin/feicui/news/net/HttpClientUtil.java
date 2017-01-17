package edu.zhuoxin.feicui.news.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.zhuoxin.feicui.news.api.HttpClientListener;
import edu.zhuoxin.feicui.news.app.App;
import edu.zhuoxin.feicui.news.entity.ImagetoGank;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/5.
 * 网络连接获取结果的工具类
 * HttpUrlConnection:
 * url:
 */
public class HttpClientUtil {
    /**
     * 获取服务器端数据
     *
     * @param targetUrl 请求的url
     * @param listener  执行完下载操作之后的回调接口
     */
    public static void getResult(URL targetUrl, HttpClientListener listener) {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            conn = (HttpURLConnection) targetUrl.openConnection();
            conn.setRequestMethod("GET");//设置请求方式
            conn.setReadTimeout(15000);//设置读取超时
            conn.setConnectTimeout(8000);//设置连接超时
//            conn.setRequestProperty(); //设置请求头信息
            //网络连接
            conn.connect();
            if (conn.getResponseCode() == 200) {//如果状态码等于200，连接成功
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str = "";
                StringBuffer sb = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                //最终结果就是sb.toString();
                listener.getResultSucceed(sb.toString());
            } else {
                listener.getResultFailer("网络连接失败，请检查网络设置");
            }
        } catch (IOException e) {
            listener.getResultExctption(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();//关闭连接
            }
        }
    }

    /**
     * 给图片控件设置图片
     */
    public static void setImage(final String imageUrl, final ImageView targetView /*listener 图片加载成功之后的回调*/) {
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                HttpURLConnection conn = null;
                try {
                    Log.i("^Tag", params[0]);
                    conn = (HttpURLConnection) new URL(params[0]).openConnection();
                    conn.setRequestMethod("GET");//设置请求方式
                    conn.setReadTimeout(15000);//设置读取超时
                    conn.setConnectTimeout(8000);//设置连接超时
                    conn.connect();
                    if (conn.getResponseCode() == 200) {//如果状态码等于200，连接成功
                        Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                        return bitmap;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();//关闭连接
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    targetView.setImageBitmap(bitmap);
                }
            }
        }.execute(imageUrl);
    }

    /**
     * OkHttp 进行网络访问
     */
    public static void getResult(String url , final Handler handler) {
        //创建OkHttp对象
        OkHttpClient client = new OkHttpClient();
        //创建请求对象
        final Request request = new Request.Builder()
                .url(url)  //设置请求的路径
                .build();
        //new call   //创建请求
        Call call = client.newCall(request);
        //将请求放进请求队列中
        call.enqueue(new Callback() {
            //失败返回的结果
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x18);
            }
            //成功返回的结果
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
//                InputStream is = response.body().byteStream();  //支持大文件的下载
                //进行json解析
                System.out.println(result);
                Gson gson = new Gson();
                ImagetoGank data = gson.fromJson(result,ImagetoGank.class);
                Message msg = handler.obtainMessage();
                msg.what = App.IMAGR_LOAG;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        });
    }
}
