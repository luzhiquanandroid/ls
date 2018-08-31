package com.qysd.lawtree.lawtreeactivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeadapter.ScPlanDetailProAdapter;
import com.qysd.lawtree.lawtreebase.BaseActivity;
import com.qysd.lawtree.lawtreeutils.wightView.PieProgressView;

import java.util.ArrayList;
import java.util.List;

public class ScPlanDetailActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    private ScPlanDetailProAdapter scPlanDetailProAdapter;
    private List<String> list = new ArrayList<>();
    private PieProgressView pie_progress_view1;

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_scplan_detail);
    }

    @Override
    protected void initView() {
        initTitle(R.drawable.ic_left_jt, "计划详情");
        mRecyclerView = findViewById(R.id.mRecyclerView);
        pie_progress_view1 = findViewById(R.id.pie_progress_view1);
        pie_progress_view1.postInvalidate();
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {
        pie_progress_view1.setInputData(20);
        setAdapter(list);
    }

    @Override
    protected void initNav() {

    }

    @Override
    public void onClick(View v) {

    }

    private void setAdapter(final List<String> list) {
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        scPlanDetailProAdapter = new ScPlanDetailProAdapter(list);
        mRecyclerView.setAdapter(scPlanDetailProAdapter);
        //条目点击接口
        scPlanDetailProAdapter.setOnItemClickListener(new ScPlanDetailProAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });

        /**
         * 滚动事件接口
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
}
