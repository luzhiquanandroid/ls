package com.qysd.lawtree.lawtreeactivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreebase.BaseActivity;
import com.qysd.lawtree.lawtreeutil.httputils.UserCallback;
import com.qysd.lawtree.lawtreeutils.Constants;
import com.qysd.lawtree.lawtreeutils.LoadDialog;
import com.qysd.lawtree.lawtreeutils.VerificationUtil;
import com.qysd.uikit.common.util.string.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 添加员工
 */

public class AddPersonActivity extends BaseActivity {
    private TextView tv_add_dept;//部门是回显
    private TextView tv_add_gongxu, tv_add_jiaose;//工序和角色是选择
    private EditText et_add_zhiwei, et_add_name, et_add_phone, et_add_number;//职位 姓名 手机号 工号

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_add_person);
        initTitle("取消", "添加员工", "完成");
    }

    @Override
    protected void initView() {
        tv_add_dept = findViewById(R.id.tv_add_dept);
        tv_add_gongxu = findViewById(R.id.tv_add_gongxu);
        tv_add_jiaose = findViewById(R.id.tv_add_jiaose);
        et_add_name = findViewById(R.id.et_add_name);
        et_add_phone = findViewById(R.id.et_add_phone);
        et_add_zhiwei = findViewById(R.id.et_add_zhiwei);
        et_add_number = findViewById(R.id.et_add_number);
        tv_add_dept.setText(getIntent().getStringExtra("department"));
    }

    @Override
    protected void bindListener() {
        tv_add_gongxu.setOnClickListener(this);
        tv_add_jiaose.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNav() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onClickTitleRight(View v) {
        //请求完成的接口 核对数据完整性和正确性
        if (StringUtil.isEmpty(et_add_name.getText().toString().trim())) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (StringUtil.isEmpty(et_add_phone.getText().toString().trim())) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (VerificationUtil.isValidTelNumber(et_add_name.getText().toString().trim())) {
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (StringUtil.isEmpty(et_add_zhiwei.getText().toString().trim())) {
            Toast.makeText(this, "职位不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        commitData(et_add_number.getText().toString().trim(), et_add_zhiwei.getText().toString().trim(),
                et_add_phone.getText().toString().trim(), et_add_name.getText().toString().trim()
                , "112");
    }

    private void commitData(String jobnum, String position, String mobileNum, String userName, String roleId) {
        /**
         * {
         "compid": 0,
         "deptid": 0,
         "flag": 0,
         "id": 0,
         "jobnum": "string",工号
         "position": "string",职位
         "procedureid": 0,工序
         "userid": "string" 用户id
         }
         */
        LoadDialog.show(this);
        OkHttpUtils.post().url(Constants.baseUrl + "company/addCompanyUser")
                .addParams("compid", "0")
                .addParams("deptid", "0")
                .addParams("jobnum", jobnum)
                .addParams("procedureid", "110")
                .addParams("userid", "7dbc673447ff4fbeb2b8232ce8d7e3d8")
                .addParams("position", position)
                .addParams("mobileNum", mobileNum)
                .addParams("userName", userName)
                .addParams("roleId", roleId)
                .build()
                .execute(new UserCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        LoadDialog.dismiss(AddPersonActivity.this);
                        Log.e("lzq add", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if ("1".equals(jsonObject.optString("code"))) {
                                Toast.makeText(AddPersonActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddPersonActivity.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
