package edu.zhuoxin.feicui.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.zhuoxin.feicui.news.R;

/**
 * Created by Administrator on 2017/1/7.
 */

public class ActionbarView extends LinearLayout {
    @BindView(R.id.actionbar_back)
    ImageView back;
    @BindView(R.id.actionbar_title)
    TextView title;
    @BindView(R.id.actionbar_menu)
    ImageView menu;
    public ActionbarView(Context context) {
        this(context,null);
    }

    public ActionbarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ActionbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view  = LayoutInflater.from(context).inflate(R.layout.actionbarview,this,true);
        ButterKnife.bind(this,view);
    }
    public void setOnClickListener(OnClickListener listener){
        back.setOnClickListener(listener);
        title.setOnClickListener(listener);
        menu.setOnClickListener(listener);
    }
}
