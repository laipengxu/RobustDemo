package com.roc.robustdemo.retrofit.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/6/5.
 * 版本更新api
 */
public interface VersionUpgradeService {

    String BASE_URL = "http://192.168.0.128:8080";

    @GET
    Observable<byte[]> downloadVersion(@Url String downloadUrl);
}
