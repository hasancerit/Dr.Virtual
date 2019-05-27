package com.yusufalicezik.drvirtual.Utils;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)

                .applicationId("N6bOfU8rVW2pouNnQxVjMEevwYi3O8dqZpxu2mkI")
                .clientKey("slpuG25CgGUCAwKsJ5sb3KeYdambRLF7iShUTT8r")
                .server("https://parseapi.back4app.com/")
                .build()

        );
    }
}
