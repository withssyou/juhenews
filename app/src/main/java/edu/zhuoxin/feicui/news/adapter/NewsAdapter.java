package edu.zhuoxin.feicui.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.zhuoxin.feicui.news.R;
import edu.zhuoxin.feicui.news.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.news.entity.NewsInfo;

/**
 * Created by Administrator on 2017/1/9.
 */
public class NewsAdapter extends BaseBaseAdapter<NewsInfo>{
    public NewsAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.adapter_newsfragment_item,null);
            holder = new NewsHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.adapter_newsfragment_item_iv);
            holder.title = (TextView) convertView.findViewById(R.id.adapter_newsfragment_item_title_tv);
            holder.authorName = (TextView) convertView.findViewById(R.id.adapter_newsfragment_item_name_tv);
            holder.date = (TextView) convertView.findViewById(R.id.adapter_newsfragment_item_date_tv);

            convertView.setTag(holder);
        }else {
            holder = (NewsHolder) convertView.getTag();
        }
        NewsInfo info = getItem(position);
        //给控件赋值
        holder.title.setText(info.getTitle());
        holder.authorName.setText(info.getAuthor_name());
        holder.date.setText(info.getDate());
        //给图片赋值
        Picasso.with(context)
                .load(info.getImageurl()) //去哪里加载图片
                .error(R.mipmap.ic_launcher)//图片加载失败的默认图片
                .into(holder.image);    //加载出来图片之后赋值的控件
        //  ImageLoader
        /**
         *  存在问题:
         *      1.异步加载图片，可能造成内存溢出(OOM)     ----->对图片进行压缩，使用三级缓存
         *      2.listview图片错位的问题                  ------> 通过设置标记，判断标记是否属于该条内容，是才加载
         *
         *      3.view的频繁创建，导致内存溢出            ----->使用convertView
         *      4.频繁的findviewbyid造成资源浪费          ------>使用viewHolder 减少findViewById的次数
         *
         */
//        HttpClientUtil.setImage(info.getImageurl(),holder.image);

        return convertView;

    }
    class  NewsHolder{
        ImageView image;
        TextView title;
        TextView authorName;
        TextView date;
    }
}
