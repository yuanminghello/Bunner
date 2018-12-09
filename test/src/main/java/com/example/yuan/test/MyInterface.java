package com.example.yuan.test;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * date:2018/12/09
 * author:yuan(123)
 * function:
 */
public interface MyInterface {
    @GET
    Call<Bean> getData(@Url String urlString);
}
