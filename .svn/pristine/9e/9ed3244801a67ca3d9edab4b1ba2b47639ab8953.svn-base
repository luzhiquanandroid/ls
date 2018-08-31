package com.qysd.lawtree.lawtreeactivity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreebase.BaseActivity;

public class ScTaskManagerDetailActivity extends BaseActivity {
    private TextView tv_complete_query;

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_sctask_manager_detail);
    }

    @Override
    protected void initView() {
        initTitle(R.drawable.ic_left_jt, "计划详情");
        tv_complete_query = findViewById(R.id.tv_complete_query);
    }

    @Override
    protected void bindListener() {
        tv_complete_query.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNav() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_complete_query:
                startActivity(new Intent(this,CompleteQueryActivity.class));
                break;
        }
    }
}
