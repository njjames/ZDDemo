package com.nj.zddemo.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.nj.zddemo.R;
import com.nj.zddemo.bean.LoginServer;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;

import java.util.List;

public class LoginActivity extends BaseMVPActivity {
    private static final String TAG = "LoginActivity";
    public static final String TEST_SERVER_NAME = "zd17.bsd126.com:8";

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
    private TextView mServerTest;
    private Button mBtnSave;
    private Button mBtnCancel;

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
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
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
        mServerTest = view.findViewById(R.id.tv_server_test);
        mBtnCancel = view.findViewById(R.id.btn_cancel);
        mBtnSave = view.findViewById(R.id.btn_save);

        LoginServer loginServer = EasySharedPreferences.load(LoginServer.class);
        mLoginServer.setText(loginServer.getServer());
        mLoginServerNo.setText(loginServer.getNumber());
        mLoginServerPass.setText(loginServer.getPass());
        mLoginServerPort.setText(loginServer.getPort());
        mLoginServerSuffix.setText(loginServer.getSuffix());
        if (loginServer.getKind() == 2) {
            mCbSetip2.setChecked(true);
        } else { //这样就默认显示第一种方式了
            mCbSetip1.setChecked(true);
        }
        //需要调用一下显示，否则进入dialog不会调用checkedchange的回调
        if (mCbSetip1.isChecked()) {
            useSetIp1();
        } else {
            useSetIp2();
        }

        mCbSetip1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //isChecked就是点击之后的状态
                //点击之后是选中的状态，需要做的事
                if (isChecked) {
                    useSetIp1();
                }
            }
        });
        mCbSetip2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    useSetIp2();
                }
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LoginServer loginServer = new LoginServer();
                // 需要先从sp中load一个对象，而不能直接new，否则会报错
                LoginServer loginServer = EasySharedPreferences.load(LoginServer.class);
                loginServer.setServer(mLoginServer.getText().toString());
                loginServer.setNumber(mLoginServerNo.getText().toString());
                loginServer.setPass(mLoginServerPass.getText().toString());
                loginServer.setPort(mLoginServerPort.getText().toString());
                loginServer.setSuffix(mLoginServerSuffix.getText().toString());
                if (mCbSetip1.isChecked()) {
                    loginServer.setKind(1);
                } else {
                    loginServer.setKind(2);
                }
                loginServer.apply();
                dialog.dismiss();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mServerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginServer.setText(TEST_SERVER_NAME);
            }
        });
    }

    private void useSetIp2() {
        mCbSetip2.setClickable(false);
        mLlServerNo.setVisibility(View.VISIBLE);
        mLlServerPass.setVisibility(View.VISIBLE);
        mLlServerPort.setVisibility(View.VISIBLE);
        mLlServerSuffix.setVisibility(View.VISIBLE);
        mCbSetip1.setChecked(false);
        mCbSetip1.setClickable(true);
        mLoginServer.setEnabled(false);
        mServerTest.setClickable(false);
        mServerTest.setTextColor(Color.parseColor("#c2c2c2"));
    }

    private void useSetIp1() {
        mCbSetip1.setClickable(false);   //将这个checkbox设置为不可点击，来达到选中之后点击状态不变的效果
        mLoginServer.setEnabled(true);   //将输入服务的edittext设置为可用
        mCbSetip2.setClickable(true);    //把第二个chexbox设置为可点击
        mCbSetip2.setChecked(false);     //把第二个chexbox设置为未选中
        mLlServerNo.setVisibility(View.GONE);   //将第二种输入方式隐藏
        mLlServerPass.setVisibility(View.GONE);
        mLlServerPort.setVisibility(View.GONE);
        mLlServerSuffix.setVisibility(View.GONE);
        mServerTest.setClickable(true);
        mServerTest.setTextColor(Color.parseColor("#1bd6e5"));
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
