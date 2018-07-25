package com.nj.zddemo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.haoge.easyandroid.easy.EasyToast;
import com.nj.zddemo.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mLoginPass;
    private ImageView mShowPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mLoginPass = findViewById(R.id.et_login_pass);
        mShowPass = findViewById(R.id.iv_showpass);
        mShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginPass.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    mLoginPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mLoginPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}
