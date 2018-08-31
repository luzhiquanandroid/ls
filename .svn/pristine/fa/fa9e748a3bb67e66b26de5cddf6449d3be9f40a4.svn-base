package com.qysd.lawtree.lawtreeactivity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeadapter.MyQiyeAdapter;
import com.qysd.lawtree.lawtreebase.BaseActivity;
import com.qysd.lawtree.lawtreebean.MyQiyeBean;
import com.qysd.lawtree.lawtreeutil.httputils.UserCallback;
import com.qysd.lawtree.lawtreeutils.LoadDialog;
import com.qysd.lawtree.lawtreeutils.NullStringToEmptyAdapterFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by QYSD_AD on 2018/3/26.
 * 我的企业点击进入  二级页面
 */

public class MyQiYeTwoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView myqiye_recyclerView;
    private Gson gson = new GsonBuilder().registerTypeAdapterFactory
            (new NullStringToEmptyAdapterFactory())
            .create();
    private String deptname;//部门名称
    private String deptid;//部门id
    private TextView tv_add_person, tv_add_department, tv_add_yuangong;
    private LinearLayout ll_show_two, ll_show_one;
    private Intent intent = null;

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_my_qiye);
        deptname = getIntent().getStringExtra("deptname");
        deptid = getIntent().getStringExtra("deptid");
        initTitle(R.drawable.ic_left_jt, deptname);
    }

    @Override
    protected void initView() {
        tv_add_person = findViewById(R.id.tv_add_person);
        tv_add_department = findViewById(R.id.tv_add_department);
        tv_add_yuangong = findViewById(R.id.tv_add_yuangong);
        ll_show_one = findViewById(R.id.ll_show_one);
        ll_show_two = findViewById(R.id.ll_show_two);
        refreshLayout = findViewById(R.id.refresh);
        myqiye_recyclerView = findViewById(R.id.myqiye_recyclerView);
        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        ll_show_one.setVisibility(View.GONE);
        ll_show_two.setVisibility(View.VISIBLE);
    }

    @Override
    protected void bindListener() {
        refreshLayout.setOnRefreshListener(this);
        tv_add_person.setOnClickListener(this);
        tv_add_department.setOnClickListener(this);
        tv_add_yuangong.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadData();
    }

    /**
     * 加载我的企业数据
     */
    private MyQiyeBean myQiyeBean;

    private void loadData() {
        LoadDialog.show(this);
        OkHttpUtils.get().url("http://192.168.1.152/company/selectDepartAndUser")
                .addParams("userId", "7dbc673447ff4fbeb2b8232ce8d7e3d8")
                .addParams("departId", deptid)
                .build().execute(new UserCallback() {
            @Override
            public void onResponse(String response, int id) {
                LoadDialog.dismiss(MyQiYeTwoActivity.this);
                Log.e("lzq", response.toString());
                myQiyeBean = gson.fromJson(response.toString(), MyQiyeBean.class);
                setAdapter(myQiyeBean.getDepartmentInfoExt(), myQiyeBean.getDepartInfoAndUserInfo());
            }
        });
    }

    @Override
    protected void initNav() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_person://添加员工
                intent = new Intent(this, AddPersonActivity.class);
                intent.putExtra("department", deptname);
                startActivity(intent);
                break;
            case R.id.tv_add_department://添加子部门
                intent = new Intent(this, AddDepartmentActivity.class);
                intent.putExtra("department", deptname);
                intent.putExtra("parentid", deptid);
                intent.putExtra("level", "3");
                startActivity(intent);
                break;
            case R.id.tv_add_yuangong:
                startActivity(new Intent(this, AddPersonActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    /**
     * 设置适配
     */
    private LinearLayoutManager manager;
    private MyQiyeAdapter myQiyeAdapter;

    private void setAdapter(final List<MyQiyeBean.DepartmentInfoExt> departmentInfoExtList,
                            List<MyQiyeBean.DepartInfoAndUserInfo> departInfoAndUserInfoList) {
        manager = new LinearLayoutManager(this);
        myqiye_recyclerView.setLayoutManager(manager);
        myQiyeAdapter = new MyQiyeAdapter(departmentInfoExtList, departInfoAndUserInfoList);
        myqiye_recyclerView.setAdapter(myQiyeAdapter);

        myQiyeAdapter.setOnItemClickListener(new MyQiyeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String type) {
                Log.e("lzq+position", position + "TYPE---" + type);
                Intent intent = null;
                if ("HEAD".equals(type)) {//头跳转下级企业页面
                    intent = new Intent(MyQiYeTwoActivity.this, MyQiYeThreeActivity.class);
                    intent.putExtra("deptname", departmentInfoExtList.get(position).getDeptname());
                    intent.putExtra("deptid", departmentInfoExtList.get(position).getDeptid());
                } else if ("BOTTOM".equals(type)) {
                    intent = new Intent(MyQiYeTwoActivity.this, LianXiRenDetailActivity.class);
                }
                startActivity(intent);
            }
        });
    }
}
