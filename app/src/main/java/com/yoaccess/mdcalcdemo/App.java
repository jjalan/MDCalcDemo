package com.yoaccess.mdcalcdemo;

import android.app.Application;

/**
 * Created by jjalan on 1/22/17.
 * Copyright (C) 2016 Binary Meter Technologies Pvt. Ltd. - All Rights Reserved
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.ENABLE_LOG = true;
        User.setContext(this);
    }
}
