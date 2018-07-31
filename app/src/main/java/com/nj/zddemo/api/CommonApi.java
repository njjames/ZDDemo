package com.nj.zddemo.api;

import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.OnlineInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-07-28.
 */

public interface CommonApi {
    /**
     * 获取在线人员
     * @return
     */
    @GET("/android/Handler1.ashx?method=GetMobileOnlineInfo")
    Observable<OnlineInfo> getMobileOnlineInfo();

    /**
     * 登录
     * @param method 方法名
     * @param name 登录名
     * @param pwd 密码
     * @param p_number IMEI手机串号
     * @return
     */
    @FormUrlEncoded
    @POST("/android/Handler1.ashx")
    Observable<LoginResult> getLogin(@Field("method") String method, @Field("name") String name, @Field("pwd") String pwd, @Field("p_number") String p_number);

    /**
     * 获取王者之路ip
     * @param username 账号
     * @param pass 密码
     * @return
     */
    @GET("http://bsdip.bsd102.com/ReturnIpM.aspx")
    Observable<String> getWZZLIP(@Query("UserName") String username, @Query("UserPwd") String pass);

}
