package admin.com.almoskyadmin.di.component;




import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.di.scope.ActivityScope;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.UtilsPref;
import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {
    void inject(BaseActivity baseActivity);

    void inject(UtilsPref utilsPref);

    void inject(AppPrefes appPrefes);
}