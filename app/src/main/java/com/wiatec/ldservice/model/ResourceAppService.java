package com.wiatec.ldservice.model;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by patrick on 2018/6/1.
 * create time : 1:29 PM
 */

public interface ResourceAppService {

    @GET("resource/{userLevel}")
    void getResourceApps(@Path("userLevel") int userLevel);
}
