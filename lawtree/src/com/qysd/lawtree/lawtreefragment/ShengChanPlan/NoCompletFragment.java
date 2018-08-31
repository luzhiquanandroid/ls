package com.qysd.lawtree.lawtreefragment.ShengChanPlan;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeactivity.ScPlanDetailActivity;
import com.qysd.lawtree.lawtreeadapter.ScPlanAdapter;
import com.qysd.lawtree.lawtreebase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产计划未完成
 */
public class NoCompletFragment extends BaseFragment {
    private SwipeRefreshLayout orderRefreshLayout;
    private RecyclerView orderRecyclerView;
    private ScPlanAdapter scPlanAdapter;
    private TextView noDataTv;
    private LinearLayoutManager manager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scplan_nocomplent;
    }

    @Override
    protected void initView() {
        noDataTv = (TextView) getView().findViewById(R.id.noDataTv);
        orderRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.orderRefreshLayout);
        orderRecyclerView = (RecyclerView) getView().findViewById(R.id.orderRecyclerView);

        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        orderRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
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
                startActivity(new Intent(getActivity(),ScPlanDetailActivity.class));
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
