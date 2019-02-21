package admin.com.almoskyadmin.di.module;




import admin.com.almoskyadmin.common.BaseActivity;
import dagger.Module;

@Module
public class ActivityModule {
    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

}
