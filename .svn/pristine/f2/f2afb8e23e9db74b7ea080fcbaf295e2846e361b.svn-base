package com.qysd.lawtree.lawtreefragment.ShengChanPlan;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeactivity.ScPlanDetailActivity;
import com.qysd.lawtree.lawtreeadapter.ScPlanAdapter;
import com.qysd.lawtree.lawtreebase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产计划已经完成
 */
public class CompletFragment extends BaseFragment {
    private LinearLayoutManager manager;
    private RecyclerView orderRecyclerView;
    private ScPlanAdapter scPlanAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scplan_complent;
    }

    @Override
    protected void initView() {
        orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {
        setAdapter(list);
    }

    @Override
    protected void initNav() {

    }

    @Override
    protected void loadData() {

    }

    private ArrayList<String> list = new ArrayList<>();

    private void setAdapter(final List<String> list) {
        manager = new LinearLayoutManager(getActivity());
        orderRecyclerView.setLayoutManager(manager);
        scPlanAdapter = new ScPlanAdapter(list);
        orderRecyclerView.setAdapter(scPlanAdapter);
        //条目点击接口
        scPlanAdapter.setOnItemClickListener(new ScPlanAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), ScPlanDetailActivity.class));
            }
        });

        /**
         * 滚动事件接口
         */
        orderRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
}
