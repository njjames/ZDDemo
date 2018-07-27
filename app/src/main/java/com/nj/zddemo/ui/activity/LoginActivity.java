package com.nj.zddemo.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.zddemo.R;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;

import java.util.List;

public class LoginActivity extends BaseMVPActivity {

    private EditText mLoginPass;
    private ImageView mShowPass;
    private EditText mLoginName;
    private ImageView mClearName;
    private Button mBtnLogin;
    private TextView mSetIP;
    private TextView mOnLine;
    private TextView mIemi;
    private TextView mHelp;
    private CheckBox mCbSetip1;
    private CheckBox mCbSetip2;
    private EditText mLoginServer;
    private EditText mLoginServerNo;
    private EditText mLoginServerPass;
    private EditText mLoginServerPort;
    private EditText mLoginServerSuffix;
    private LinearLayout mLlServerNo;
    private LinearLayout mLlServerPass;
    private LinearLayout mLlServerPort;
    private LinearLayout mLlServerSuffix;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        mLoginName = findViewById(R.id.et_login_name);
        mClearName = findViewById(R.id.iv_clearname);
        mLoginPass = findViewById(R.id.et_login_pass);
        mShowPass = findViewById(R.id.iv_showpass);
        mBtnLogin = findViewById(R.id.btn_login);
        mSetIP = findViewById(R.id.tv_login_setip);
        mOnLine = findViewById(R.id.tv_login_online);
        mIemi = findViewById(R.id.tv_login_imei);
        mHelp = findViewById(R.id.tv_login_help);

        mClearName.setOnClickListener(this);
        mShowPass.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mSetIP.setOnClickListener(this);
        mOnLine.setOnClickListener(this);
        mIemi.setOnClickListener(this);
        mHelp.setOnClickListener(this);

        mLoginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mLoginName.getText().toString())) {
                    mClearName.setVisibility(View.VISIBLE);
                } else {
                    mClearName.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected List<MVPPresenter> createPresenters() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clearname: //点击清除操作员名称
                clearName();
                break;
            case R.id.iv_showpass: //点击是否显示密码
                changePassStatus();
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_login_setip:
                showSetIpDialog();
                break;
            case R.id.tv_login_online:
                break;
            case R.id.tv_login_imei:
                break;
            case R.id.tv_login_help:
                break;
        }
    }

    /**
     * 显示设置IP的对话框
     */
    private void showSetIpDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.setip_dialog_layout, null);
        Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(view);
        dialog.show();

        mCbSetip1 = view.findViewById(R.id.cb_setip1);
        mCbSetip2 = view.findViewById(R.id.cb_setip2);
        mLoginServer = view.findViewById(R.id.et_login_server);
        mLoginServerNo = view.findViewById(R.id.et_login_server_no);
        mLoginServerPass = view.findViewById(R.id.et_login_server_pass);
        mLoginServerPort = view.findViewById(R.id.et_login_server_port);
        mLoginServerSuffix = view.findViewById(R.id.et_login_server_suffix);
        mLlServerNo = view.findViewById(R.id.ll_server_no);
        mLlServerPass = view.findViewById(R.id.ll_server_pass);
        mLlServerPort = view.findViewById(R.id.ll_server_port);
        mLlServerSuffix = view.findViewById(R.id.ll_server_suffix);

    }

    /**
     * 清除操作员名称
     */
    private void clearName() {
        mLoginName.getText().clear();
    }

    /**
     * 修改密码的显示状态，是否加密显示
     */
    private void changePassStatus() {
        if (mLoginPass.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT) {
            mLoginPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mShowPass.setImageResource(R.drawable.eye_on);
        } else {
            mLoginPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            mShowPass.setImageResource(R.drawable.eye_off);
        }
    }
}
