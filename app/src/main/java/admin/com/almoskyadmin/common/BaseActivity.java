package admin.com.almoskyadmin.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import javax.inject.Inject;

import admin.com.almoskyadmin.App;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.helper.MyCustomDialog;
import admin.com.almoskyadmin.notification.MyFirebaseInstanceIDService;
import admin.com.almoskyadmin.receiver.ConnectionReceiver;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.UtilsPref;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static admin.com.almoskyadmin.utils.constants.PrefConstants.isLogin;


public class BaseActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener,MyFirebaseInstanceIDService.onFirebaseIdReceivedListener  {

    public SimpleArcDialog mDialog;
    @Inject
    public UtilsPref utilsPref;
    @Inject
    public AppPrefes appPrefes;

    private Dialog noNwAlert;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getInstance().setConnectionListener(this);
    }

    protected App getApp() {
        return (App) getApplicationContext();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = getDialog(this);
        getApp().getActivityComponent().inject(this);
    }

    public void addFragment(int fragment_container, Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(fragment_container, fragment);
        transaction.addToBackStack(addBackStack ? fragment.getClass().getName() : null);
        transaction.commit();
    }

    public void replaceFragment(int fragment_container, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragment_container, fragment);
        transaction.commit();
    }

    public SimpleArcDialog getDialog(Context mContext) {
        SimpleArcDialog mDialog = new SimpleArcDialog(mContext);
        mDialog.setConfiguration(new ArcConfiguration(mContext));
        return mDialog;
    }

   /* public void onBackButton() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_camera);
        upArrow.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    } */

    public void onLog(String message) {
        Log.d("message", "-- " + message);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            noNwAlert = openAlertDialogue("Check internet connection");
            //show a No Internet Alert or Dialog

        } else {
            if (null != noNwAlert)
                noNwAlert.dismiss();
            // dismiss the dialog or refresh the activity
        }
    }

    public Dialog openAlertDialogue(String messageText) {
        final MyCustomDialog builder = new MyCustomDialog(BaseActivity.this, messageText);
        final android.app.AlertDialog dialog = builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(BaseActivity.this.getResources().getColor(R.color.green));
            }
        });

        dialog.show();

        dialog.show();
        return dialog;
    }

    public void getResponse(String response, int requestId) {

    }

    @Override
    public void onFirebaseIdRecived(String token) {

        appPrefes.saveData(PrefConstants.token, token);
        if (appPrefes.getBoolData(isLogin)) {
            ApiCalls apiCalls = new ApiCalls();
            RequestParams params = new RequestParams();
            params.put(ApiConstants.registrationToken, token);
            String url = ApiConstants.firebaseTokenId;
            apiCalls.callApiPost((BaseActivity) getApplicationContext(), params, null, url, 1002);
        }

    }
    public String getString(View tv) {

        if (tv instanceof EditText) {
            return ((EditText) tv).getText().toString();
        } else {
            return ((TextView) tv).getText().toString();
        }
    }
}
