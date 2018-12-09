package com.example.yuan.test;

import android.content.Context;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date:2018/12/09
 * author:yuan(123)
 * function:
 */
public class HttpSingleton {
    private static HttpSingleton mHttpSingleton;
    private final OkHttpClient.Builder builder;

    private HttpSingleton(){
        builder = new OkHttpClient().newBuilder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS);

    }
    public static HttpSingleton getInstance(){
        if(mHttpSingleton==null){
            synchronized (HttpSingleton.class){
                if(mHttpSingleton==null){
                    mHttpSingleton=new HttpSingleton();
                }
            }
        }
        return mHttpSingleton;
    }

    public void doGet(final UtilListener utilListener){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.getList)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);
        Call<Bean> call = myInterface.getData("http://www.wanandroid.com/banner/json");
        call.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                List<Bean.DataBean> json = response.body().getData();
                if(utilListener!=null){
                    utilListener.succeed(json);
                }
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });

    }

    public interface UtilListener{
        void succeed(List<Bean.DataBean> json);
        void fail(Exception e);
    }
    private UtilListener mUtilListener;

    public void setmUtilListener(UtilListener mUtilListener) {
        this.mUtilListener = mUtilListener;
    }
}
