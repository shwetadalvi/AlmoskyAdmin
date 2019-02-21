package admin.com.almoskyadmin;


import android.app.Application;

import admin.com.almoskyadmin.di.component.ActivityComponent;
import admin.com.almoskyadmin.di.component.AppComponent;
import admin.com.almoskyadmin.di.component.DaggerActivityComponent;
import admin.com.almoskyadmin.di.component.DaggerAppComponent;
import admin.com.almoskyadmin.di.module.ApplicationModule;
import admin.com.almoskyadmin.receiver.ConnectionReceiver;


public class App extends Application {

    private AppComponent mAppComponent;
    private ActivityComponent activityComponent;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        Printooth.INSTANCE.init(this);
//        ViewTarget.setTagId(R.id.glide_tag);

        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNext-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());  */
     /*   CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CircularStd-Medium.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()); */
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(mAppComponent).build();
        mInstance = this;

    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
}
