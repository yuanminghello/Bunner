package com.example.yuan.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banner bunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();


    }

    private void initData() {
        HttpSingleton.getInstance().doGet(new HttpSingleton.UtilListener() {
            @Override
            public void succeed(List<Bean.DataBean> json) {
                bunner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                bunner.setImageLoader(new BeaaerApp());
                List<String> Imagelist=new ArrayList();
                List<String> Titlelist=new ArrayList();

                for (int i = 0; i <json.size() ; i++) {
                    Imagelist.add(json.get(i).getImagePath());
                    Titlelist.add(json.get(i).getTitle());
                }
                bunner.setImages(Imagelist);
                bunner.setBannerAnimation(Transformer.DepthPage);
                bunner.setBannerTitles(Titlelist);
                bunner.isAutoPlay(true);

                bunner.setIndicatorGravity(BannerConfig.CENTER);

                bunner.start();
            }

            @Override
            public void fail(Exception e) {

            }


        });


    }

    private void initView() {
        bunner = (Banner) findViewById(R.id.bunner);
    }
}
