package com.nj.zddemo.api;

import com.nj.zddemo.bean.OnlineInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018-07-28.
 */

public interface CommonApi {
    @GET("/android/Handler1.ashx?method=GetMobileOnlineInfo")
    Observable<OnlineInfo> getMobileOnlineInfo();
}
