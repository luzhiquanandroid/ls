package com.qysd.lawtree.lawtreefragment.ShengChanTaskManager;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeactivity.ScTaskManagerDetailActivity;
import com.qysd.lawtree.lawtreeadapter.ScTaskManagerAdapter;
import com.qysd.lawtree.lawtreebase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产管理已完成
 */
public class TaskCompletFragment extends BaseFragment {
    private LinearLayoutManager manager;
    private RecyclerView orderRecyclerView;
    private ScTaskManagerAdapter scTaskManagerAdapter;

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
        scTaskManagerAdapter = new ScTaskManagerAdapter(list);
        orderRecyclerView.setAdapter(scTaskManagerAdapter);
        //条目点击接口
        scTaskManagerAdapter.setOnItemClickListener(new ScTaskManagerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), ScTaskManagerDetailActivity.class));
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
