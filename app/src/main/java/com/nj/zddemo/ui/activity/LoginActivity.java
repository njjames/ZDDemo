package com.nj.zddemo.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.haoge.easyandroid.easy.EasyPermissions;
import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.haoge.easyandroid.easy.EasyToast;
import com.nj.zddemo.R;
import com.nj.zddemo.api.APIConstants;
import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.LoginServerInfo;
import com.nj.zddemo.bean.OnlineInfo;
import com.nj.zddemo.bean.RememberOperator;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.presenter.impl.LoginPresenter;
import com.nj.zddemo.mvp.view.impl.LoginView;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;
import com.nj.zddemo.ui.adapter.login.OnlineInfoAdapter;
import com.nj.zddemo.utils.NetUtils;
import com.nj.zddemo.utils.PhoneUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseMVPActivity implements LoginView {
    private static final String TAG = "LoginActivity";
    public static final String TEST_SERVER_NAME = "zd18.bsd126.com:8";

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
    private TextView mOnlineCount;
    private TextView mOnlineTotal;
    private TextView mOnlineTime;
    private LoginPresenter mLoginPresenter;
    private ListView mOnlineListView;

    private List<OnlineInfo.RowsBean> mRowsBeanList = new ArrayList<>();
    private OnlineInfoAdapter mOnlineInfoAdapter;
    private CheckBox mRemember;
    private Dialog mSetIpDialog;

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
        mRemember = findViewById(R.id.cb_remember);

        RememberOperator rememberOperator = EasySharedPreferences.load(RememberOperator.class);
        if (rememberOperator.isChecked) {
            mRemember.setChecked(true);
            mLoginName.setText(rememberOperator.name);
        }
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
        // 运行时权限的申请
        EasyPermissions.create(Manifest.permission.READ_PHONE_STATE).request(this);
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {
        mLoginPresenter = new LoginPresenter(this);
        presenters.add(mLoginPresenter);
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
            case R.id.btn_login: //点击登录
                login();
                break;
            case R.id.tv_login_setip: //点击设置IP
                showSetIpDialog();
                break;
            case R.id.tv_login_online: //点击在线人数
                showOnlineDialog();
                break;
            case R.id.tv_login_imei:
                showImei();
                break;
            case R.id.tv_login_help:
                showHelpDialog();
                break;
        }
    }

    /**
     * 帮助
     */
    private void showHelpDialog() {
        Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.help_dialog_layout);
        dialog.show();
    }

    /**
     * 显示本机串号
     */
    private void showImei() {
        EasyToast.newBuilder().build().show(PhoneUtils.getImei(this));
    }

    /**
     * 登录
     */
    private void login() {
        if (NetUtils.getNetType(this) == NetUtils.NETWORK_NONE) {
            EasyToast.newBuilder().build().show("没有网络连接，请连接网络");
            return;
        }
        // 如果是第一种方式并且服务名为空，或者第二种方式并且ip地址为空，就提示
        LoginServerInfo serverInfo = EasySharedPreferences.load(LoginServerInfo.class);
        if ((serverInfo.getKind() == 1 && TextUtils.isEmpty(serverInfo.getServer()))
                || (serverInfo.getKind() == 2 && TextUtils.isEmpty(serverInfo.getIp()))) {
            EasyToast.newBuilder().build().show("请设置好IP地址之后再登录");
            return;
        }
        String name = mLoginName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            EasyToast.newBuilder().build().show("操作员不能为空");
            return;
        }
        // 处理是否记住操作员的逻辑
        RememberOperator rememberOperator = EasySharedPreferences.load(RememberOperator.class);
        rememberOperator.isChecked = mRemember.isChecked();
        rememberOperator.name = mLoginName.getText().toString();
        rememberOperator.apply();
        String pass = mLoginPass.getText().toString();
        String imei = PhoneUtils.getImei(this);
        showLoadingDialog();
        mLoginPresenter.getLogin(APIConstants.METHOD_GETLOGIN, name, pass, imei);
    }

    /**
     * 显示在线人数对话框
     */
    private void showOnlineDialog() {
        showLoadingDialog();
        View view = LayoutInflater.from(this).inflate(R.layout.online_dialog_layout, null);
        Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(view);
        dialog.show();

        mOnlineCount = view.findViewById(R.id.tv_count);
        mOnlineTotal = view.findViewById(R.id.tv_total);
        mOnlineTime = view.findViewById(R.id.tv_time);
        mOnlineListView = view.findViewById(R.id.lv_online);
        mOnlineInfoAdapter = new OnlineInfoAdapter(mRowsBeanList);
        mOnlineListView.setAdapter(mOnlineInfoAdapter);
        mLoginPresenter.getMobileOnlineInfo();
    }

    /**
     * 显示设置IP的对话框
     */
    private void showSetIpDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.setip_dialog_layout, null);
        mSetIpDialog = new Dialog(this, R.style.MyDialog);
        mSetIpDialog.setContentView(view);
        mSetIpDialog.show();

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
        // 读取SP，设置初始的内容
        LoginServerInfo serverInfo = EasySharedPreferences.load(LoginServerInfo.class);
        mLoginServer.setText(serverInfo.getServer());
        mLoginServerNo.setText(serverInfo.getNumber());
        mLoginServerPass.setText(serverInfo.getPass());
        mLoginServerPort.setText(serverInfo.getPort());
        mLoginServerSuffix.setText(serverInfo.getSuffix());
        if (serverInfo.getKind() == 2) {
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
//                LoginServer serverInfo = new LoginServer();
                // 需要先从sp中load一个对象，而不能直接new，否则会报错
                if (mCbSetip1.isChecked()) {
                    LoginServerInfo serverInfo = EasySharedPreferences.load(LoginServerInfo.class);
                    serverInfo.setServer(mLoginServer.getText().toString());
                    serverInfo.setKind(1);
                    serverInfo.apply();
                    mSetIpDialog.dismiss();
                } else {
                    showLoadingDialog();
                    // 异步请求网络，保存工作，放在请求成功的回调中
                    getWZZLIP(mLoginServerNo.getText().toString(), mLoginServerPass.getText().toString());
                }
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetIpDialog.dismiss();
            }
        });

        mServerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginServer.setText(TEST_SERVER_NAME);
            }
        });
    }

    private void getWZZLIP(String number, String pass) {
        mLoginPresenter.getWZZLIP(number, pass);
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

    @Override
    public void onRequestError(String msg) {
        EasyToast.newBuilder().build().show(msg);
        hideLoadingDialog();
    }

    @Override
    public void loadMobileOnlineInfo(OnlineInfo onlineInfo) {
        mOnlineCount.setText(onlineInfo.count);
        mOnlineTotal.setText("(可用点数:" + onlineInfo.totalCout + ")");
        mOnlineTime.setText(onlineInfo.msg);
        // 不能用等号赋值，这样这里的list和adpter中的就不是同一个数据源了
//        mRowsBeanList = onlineInfo.rows;
        // 应该获取的集合数据添加进来，记得先clear
        mRowsBeanList.clear();
        mRowsBeanList.addAll(onlineInfo.rows);
        mOnlineInfoAdapter.notifyDataSetChanged();
        hideLoadingDialog();
    }

    @Override
    public void loadLoginResult(LoginResult loginResult) {
        if ("ok".equals(loginResult.code)) {
            LoginResult result = EasySharedPreferences.load(LoginResult.class);
            result.Id = loginResult.Id;
            result.caozuoyuan_xm = loginResult.caozuoyuan_xm;
            result.caozuoyuan_Gndm = loginResult.caozuoyuan_Gndm;
            result.GongSiNo = loginResult.GongSiNo;
            result.GongSiMc = loginResult.GongSiMc;
            result.GongSi_Gndm = loginResult.GongSi_Gndm;
            result.CangKu_Gndm = loginResult.CangKu_Gndm;
            result.CangKuDo_Gndm = loginResult.CangKuDo_Gndm;
            result.apply();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            EasyToast.newBuilder().build().show(loginResult.msg);
        }
        hideLoadingDialog();
    }

    @Override
    public void loadWZZLIP(String data) {
        if (TextUtils.isEmpty(data)) {
            EasyToast.newBuilder().build().show("王者之路账号或密码错误。");
            return;
        }
        String ip;
        // 如果不存在端口，就直接作为ip，否则需要连接上端口和后缀
        if (TextUtils.isEmpty(mLoginServerPort.getText().toString())) {
            ip = data;
        } else {
            ip = data + mLoginServerPort.getText().toString() + mLoginServerSuffix.getText().toString();
        }
        LoginServerInfo serverInfo = EasySharedPreferences.load(LoginServerInfo.class);
        serverInfo.setNumber(mLoginServerNo.getText().toString());
        serverInfo.setPass(mLoginServerPass.getText().toString());
        serverInfo.setPort(mLoginServerPort.getText().toString());
        serverInfo.setSuffix(mLoginServerSuffix.getText().toString());
        serverInfo.setIp(ip);
        serverInfo.setKind(2);
        serverInfo.apply();
        hideLoadingDialog();
        mSetIpDialog.dismiss();
    }
}
