package com.nj.zddemo.bean;

import com.haoge.easyandroid.easy.PreferenceSupport;

/**
 * Created by Administrator on 2018-07-30.
 */

public class LoginResult extends PreferenceSupport {
    public String code;
    public String Id;
    public String caozuoyuan_xm;
    public String caozuoyuan_password;
    public String caozuoyuan_Gndm;
    public String dept_mc;
    public String GongSiNo;
    public String GongSiMc;
    public String GongSi_Gndm;
    public String CangKu_Gndm;
    public String CangKuDo_Gndm;

    public String msg; //这个属性是在登录失败的才会赋值
}
