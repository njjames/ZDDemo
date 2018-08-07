package com.nj.zddemo.mvp.view.impl;

import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.OnlineInfo;
import com.nj.zddemo.mvp.view.base.MVPView;

/**
 * 登录功能相关的通信协议接口
 * 作用是由V层提供给P层进行P-V绑定，
 * 也就是在P层中调用此接口中的方法来通知V层进行界面更新，类似于一个Callback供P层使用
 * Created by Administrator on 2018-07-26.
 */

public interface LoginView extends MVPView {
     void onRequestError(String msg);

     void loadMobileOnlineInfo(OnlineInfo onlineInfo);

     void loadLoginResult(LoginResult loginResult);

     void loadWZZLIP(String data);

}
