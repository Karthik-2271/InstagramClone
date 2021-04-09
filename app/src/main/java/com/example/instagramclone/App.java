package com.example.instagramclone;

import com.parse.Parse;
import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("U2i8eLfzqkqMX3C4ELFsAC2ar94ZK47SkeIZ03uF")
                // if defined
                .clientKey("U2ZJ7MvLlFNGSrRWWOlWy10gPKJ7zizh1zSWKbqS")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
