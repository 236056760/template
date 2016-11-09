package com.lvbo.template;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.lvbo.template.network.OkHttpsFactory;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import java.io.InputStream;

/**
 * Created by lvbo on 16/9/1.
 */
public class MyApplication extends Application {
    private Tracker mTracker;

    private static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());//收集crash信息
        applicationContext=this;
        //内存泄漏检测工具
        LeakCanary.install(this);

        //这句主要是为了支持https的图片
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpsFactory());

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());


    }

    public static Context getInstance(){
        return applicationContext;
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            mTracker = analytics.newTracker(R.xml.global_tracker);
            mTracker = analytics.newTracker("UA-86025748-1");//需要改成相应项目的id
        }
        return mTracker;
    }
}
