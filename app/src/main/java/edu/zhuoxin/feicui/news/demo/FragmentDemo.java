package edu.zhuoxin.feicui.news.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.zhuoxin.feicui.news.R;

/**
 * Created by Administrator on 2017/1/7.
 *  fragment生命周期：
 *
 *      onAttach（） ---> onCreate() ---->onCreateView --->Activty的onCreate（）--->onActivityCreated
 *      Activity onStart() --- onStart()----Activtye onResume()  --->onResume();
 *      onPause()---->Activity onPause() ----> onStop()----->Activity onStop()---->onDestroyView()
 *      --->onDestory()---->onDetach()-----Activity onDestory();
 *
 */

public class FragmentDemo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdemo, container, false);
        ButterKnife.bind(this,view);
        Log.i("Tag", "------------------------onCreateView");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Tag", "------------------------onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Tag", "------------------------onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Tag", "------------------------onActivityCreated");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Tag", "------------------------onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Tag", "------------------------onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Tag", "------------------------onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Tag", "------------------------onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Tag", "------------------------onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Tag", "------------------------onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("Tag", "------------------------onResume");
    }
    @OnClick(R.id.fragment_btn)
    public void onClick(View view){
        Toast.makeText(getActivity(),"点击了",Toast.LENGTH_LONG).show();
    }

}
