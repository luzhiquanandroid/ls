package com.qysd.lawtree.lawtreefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qysd.lawtree.DemoCache;
import com.qysd.lawtree.R;
import com.qysd.lawtree.contact.activity.UserProfileSettingActivity;
import com.qysd.lawtree.lawtreeactivity.MyBusinessActivity;
import com.qysd.lawtree.lawtreeactivity.ShenChanDateActivity;
import com.qysd.lawtree.lawtreeactivity.ShenChanPlanActivity;
import com.qysd.lawtree.lawtreeactivity.TaskManagerActivity;
import com.qysd.lawtree.lawtreeactivity.OrderActivity;
import com.qysd.lawtree.lawtreeactivity.PinListActivity;
import com.qysd.lawtree.lawtreeactivity.RepertoryActivity;
import com.qysd.lawtree.lawtreebase.BaseFragment;


/**
 * Created by zhouwei on 17/4/23.
 */

public class ApplicationFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_kucun, ll_dingdan, ll_xiaodan;
    private String mFrom;
    private LinearLayout ll_shenchan_plan, ll_task_manager, ll_shenchan_date;//生产计划 任务管理 生产排期

    public static ApplicationFragment newInstance(String from) {
        ApplicationFragment fragment = new ApplicationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_application_layout;
    }

    @Override
    protected void initView() {
        initTitle("企业应用");
        ll_shenchan_plan = (LinearLayout) findViewById(R.id.ll_shenchan_plan);
        ll_task_manager = (LinearLayout) findViewById(R.id.ll_task_manager);
        ll_shenchan_date = (LinearLayout) findViewById(R.id.ll_shenchan_date);

        ll_kucun = (LinearLayout) findViewById(R.id.ll_kucun);
        ll_dingdan = (LinearLayout) findViewById(R.id.ll_dingdan);
        ll_xiaodan = (LinearLayout) findViewById(R.id.ll_xiaodan);
    }

    @Override
    protected void bindListener() {
        ll_shenchan_plan.setOnClickListener(this);
        ll_task_manager.setOnClickListener(this);
        ll_shenchan_date.setOnClickListener(this);
        ll_kucun.setOnClickListener(this);
        ll_dingdan.setOnClickListener(this);
        ll_xiaodan.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNav() {

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.activity_account_and_security.fragment_application_layout,null);
//        TextView textView = (TextView) view.findViewById(R.id.title_from);
//        TextView content = (TextView) view.findViewById(R.id.fragment_content);
//        textView.setText(mFrom);
//        content.setText("ApplicationFragment");
//        return view;
//    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shenchan_plan:
                startActivity(new Intent(getActivity(), ShenChanPlanActivity.class));
                break;
            case R.id.ll_task_manager:
                startActivity(new Intent(getActivity(), TaskManagerActivity.class));
                break;
            case R.id.ll_shenchan_date:
                startActivity(new Intent(getActivity(), ShenChanDateActivity.class));
                break;
//            case R.id.rl_personinfo:
//                UserProfileSettingActivity.start(getActivity(), DemoCache.getAccount());
//                break;
//            case R.id.rl_mybusiness:
//                startActivity(new Intent(getActivity(), MyBusinessActivity.class));
//                break;
            case R.id.ll_kucun:
                startActivity(new Intent(getActivity(), RepertoryActivity.class));
                break;
            case R.id.ll_dingdan:
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.ll_xiaodan:
                startActivity(new Intent(getActivity(), PinListActivity.class));
                break;
        }
    }
}
