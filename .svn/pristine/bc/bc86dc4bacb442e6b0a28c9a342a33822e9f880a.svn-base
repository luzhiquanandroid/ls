package com.qysd.lawtree.lawtreeactivity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeadapter.OrdFgtPagAdapter;
import com.qysd.lawtree.lawtreebase.BaseActivity;
import com.qysd.lawtree.lawtreefragment.MeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderActivity extends BaseActivity {

    private String[] orderType = {"未完成订单", "已完成订单"};
    private List<Fragment> orderFragment = new ArrayList<>();//fragment的集合
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Gson gson = new Gson();

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_order);
        initTitle(R.drawable.ic_left_jt,"订单管理","筛选");
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
    }

    @Override
    protected void onClickTitleRight(View v) {
        super.onClickTitleRight(v);
        startActivity(new Intent(OrderActivity.this,OrderDetailActivity.class));
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void initNav() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        orderFragment.add(new MeFragment());
        orderFragment.add(new MeFragment());
        // ViewPager设置适配器
        OrdFgtPagAdapter adapter =
                new OrdFgtPagAdapter(getSupportFragmentManager(), Arrays.asList(orderType),
                        orderFragment);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(adapter);
        // 将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
