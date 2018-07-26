package com.nj.zddemo.mvp.view.base;

import android.app.Activity;

/**
 *  基础通信协议接口定义
 *  其中定义一些基础的协议方法。这些方法是所有V层都需要的功能。
 * Created by Administrator on 2018-07-26.
 */

public interface MVPView {
    /**
     * 获取Activity对象
     * presenter中有可能用到context对象，通过这个方法获取
     * @return
     */
    Activity getHostActivity();
}
