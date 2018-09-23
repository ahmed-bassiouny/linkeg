package net.link.linkapp;

import android.app.Application;

import bassiouny.ahmed.genericmanager.SharedPrefManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefManager.init(this,"link");
        Config.initRetrofitConfig();
    }
}
