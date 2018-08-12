package com.nj.zddemo.api;

import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.OnlineInfo;
import com.nj.zddemo.bean.PartCategory;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.bean.SalesInfoByBill;
import com.nj.zddemo.bean.StockInfo;
import com.nj.zddemo.bean.TodayBill;
import com.nj.zddemo.bean.TypeCategory;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
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

    /**
     * 获取今日开单的信息
     * @param czyid 操作员id
     * @return
     */
    @GET("/android/Handler1.ashx?method=getTodayXiaosh")
    Observable<TodayBill> getTodayXiaosh(@Query("czyid") String czyid);

    /**
     * 按单号查询销售单
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/android/Handler1.ashx")
    Observable<SalesInfoByBill> getSalesInfoByBill(@FieldMap Map<String, String> map);

    /**
     * 库存商品查询
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/android/Handler1.ashx")
    Observable<PartInfoOfStock> getPartInfoOfStock(@FieldMap Map<String, String> map);

    /**
     * 获取全部配件分类信息
     * @return
     */
    @GET("/android/Handler1.ashx?method=getPeijLb")
    Observable<PartCategory> getAllPartCategory();

    /**
     * 获取全部车型分类信息
     * @return
     */
    @GET("/android/Handler1.ashx?method=getPeijCx")
    Observable<TypeCategory> getAllTypeCategory();

    /**
     * 获取仓库的信息
     * @param czyid
     * @param type
     * @return
     */
    @GET("/android/Handler1.ashx?method=getCk")
    Observable<StockInfo> getStockInfo(@Query("czyid") String czyid, @Query("type") int type);
}
