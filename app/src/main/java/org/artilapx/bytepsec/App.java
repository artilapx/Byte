package org.artilapx.bytepsec;

import android.app.Application;

import org.artilapx.bytepsec.common.CrashHandler;
import org.artilapx.bytepsec.utils.ImageUtils;
import org.artilapx.bytepsec.utils.LogUtils;
import org.artilapx.bytepsec.utils.NetworkUtils;

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler mCrashHandler = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(mCrashHandler);
        ImageUtils.init(this);
        LogUtils.init(this);
        NetworkUtils.init(this, false);
        instance = this;
    }
}
