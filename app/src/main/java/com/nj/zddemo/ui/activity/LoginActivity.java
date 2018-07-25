package com.nj.zddemo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.nj.zddemo.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mLoginPass;
    private ImageView mShowPass;
    private EditText mLoginName;
    private ImageView mClearName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    private void initView() {
        mLoginName = findViewById(R.id.et_login_name);
        mClearName = findViewById(R.id.iv_clearname);
        mLoginPass = findViewById(R.id.et_login_pass);
        mShowPass = findViewById(R.id.iv_showpass);

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

        mClearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginName.getText().clear();
            }
        });
        mShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginPass.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT) {
                    mLoginPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mShowPass.setImageResource(R.drawable.eye_on);
                } else {
                    mLoginPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    mShowPass.setImageResource(R.drawable.eye_off);
                }
            }
        });
    }
}
