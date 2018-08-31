package com.qysd.lawtree.lawtreeactivity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeadapter.FgtPagAdapter;
import com.qysd.lawtree.lawtreebase.BaseActivity;
import com.qysd.lawtree.lawtreefragment.ShengChanPlan.CompletFragment;
import com.qysd.lawtree.lawtreefragment.ShengChanPlan.NoCompletFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 生产计划
 */
public class ShenChanPlanActivity extends BaseActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String[] orderType = {"待完成", "已完成"};
    private List<Fragment> scPlanFragments = new ArrayList<>();//fragment的集合

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_shen_chan_plan);
    }

    @Override
    protected void initView() {
        initTitle(R.drawable.ic_left_jt, "生产计划", "筛选");
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
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
        scPlanFragments.add(new NoCompletFragment());
        scPlanFragments.add(new CompletFragment());
        // ViewPager设置适配器
        FgtPagAdapter adapter =
                new FgtPagAdapter(getSupportFragmentManager(), Arrays.asList(orderType),
                        scPlanFragments);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(adapter);
        // 将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout, 60, 60);
            }
        });
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    protected void onClickTitleRight(View v) {
        super.onClickTitleRight(v);
        int currentItem = mViewPager.getCurrentItem();
        Toast.makeText(this, "筛选" + currentItem, Toast.LENGTH_SHORT).show();
    }
}
