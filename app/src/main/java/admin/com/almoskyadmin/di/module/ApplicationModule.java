package admin.com.almoskyadmin.di.module;

import android.content.Context;



import javax.inject.Singleton;

import admin.com.almoskyadmin.App;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.UtilsPref;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final App mApp;

    public ApplicationModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Context appContext() {
        return mApp;
    }

    @Provides
    @Singleton
    public UtilsPref appUtilsPref() {
        return new UtilsPref(mApp.getActivityComponent());
    }

    @Provides
    @Singleton
    public AppPrefes appAppPrefes() {
        return new AppPrefes(appContext().getApplicationContext());
    }

    @Provides
    @Singleton
    public ApiCalls appApiCalls() {
        return new ApiCalls();
    }
}
