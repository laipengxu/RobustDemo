package com.roc.robustdemo.retrofit;


import com.roc.robustdemo.retrofit.api.VersionUpgradeService;
import com.roc.robustdemo.retrofit.converter.bytes.ByteArrayConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Administrator on 2017/6/5.
 */
public class RetrofitManager {

    public static Retrofit getDownloadClient(){
        return new Retrofit.Builder()
                .baseUrl(VersionUpgradeService.BASE_URL)
                .client(MyOkHttpClient.getOkHttpClient())
                .addConverterFactory(ByteArrayConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static <T> T createApi(Class<T> clazz, Retrofit retrofit) {
        return retrofit.create(clazz);
    }
}
