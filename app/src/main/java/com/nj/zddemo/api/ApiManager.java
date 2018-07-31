package com.nj.zddemo.api;

import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.nj.zddemo.bean.LoginServerInfo;
import com.nj.zddemo.bean.OnlineInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018-07-28.
 */

public class ApiManager {
    private static ApiManager apiManager = null;
    private CommonApi commonApi;
    private static final int DEFAULT_TIMEOUT = 5;

    //这个对象用来作为Commonapi的锁
    private final Object Monitor = new Object();
    //使用自定义的OKHttpClient
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build();

    /**
     * 获取单例对象
     * @return
     */
    public static ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    /**
     * 获取的api对象也是单例的
     * @return
     */
    public CommonApi getCommonApi() {
        if (commonApi == null) {
            synchronized (Monitor) {
                if (commonApi == null) {
                    commonApi = configRetrofit(CommonApi.class);
                }
            }
        }
        return commonApi;
    }

    /**
     * 获取网络请求接口的实例
     * @param service
     * @param <T>
     * @return
     */
    private <T> T configRetrofit(Class<T> service) {
        LoginServerInfo serverInfo = EasySharedPreferences.load(LoginServerInfo.class);
        // 判断登录方式
        String ip;
        if (serverInfo.getKind() == 1) {
            ip = serverInfo.getServer();
        }else {
            ip = serverInfo.getIp();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ip)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    /**
     * 用于重新获取网络请求接口的实例
     */
    public void resetAAPI() {
        commonApi = null;
    }
}
