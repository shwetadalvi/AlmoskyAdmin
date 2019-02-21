package admin.com.almoskyadmin.di.component;

import android.content.Context;



import javax.inject.Singleton;

import admin.com.almoskyadmin.di.module.ApplicationModule;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.UtilsPref;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent {
    Context appContext();

    UtilsPref appUtilsPref();

    AppPrefes appAppPrefes();

    ApiCalls appApiCalls();
}
