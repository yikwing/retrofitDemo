package com.rongyi.myapplication.utils;

import com.rongyi.myapplication.bean.HttpResult;
import com.rongyi.myapplication.bean.ListResult;
import com.rongyi.myapplication.service.LoginService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rongyi on 2017/7/5.
 */

public class HttpMethods {

    private static final String BASE_URL = "http://120.24.241.143/api/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private final LoginService loginService;

    private HttpMethods() {

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        loginService = retrofit.create(LoginService.class);

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Function<HttpResult<T>, T> {


        @Override
        public T apply(HttpResult<T> tHttpResult) throws Exception {
            if (tHttpResult.getCode() >= 500) {
                throw new IllegalArgumentException("服务器异常");
            } else if (tHttpResult.getCode() >= 400) {
                throw new IllegalArgumentException("参数传递异常");
            }
            return tHttpResult.getList();
        }
    }


    /**
     * 登录获取userID和token
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param pageNo     账号
     * @param seaName    密码
     */
    public void getLogin(Observer<List<ListResult>> subscriber, String pageNo, String seaName) {
        loginService.getLogin(pageNo, seaName)
                .map(new HttpResultFunc<List<ListResult>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
