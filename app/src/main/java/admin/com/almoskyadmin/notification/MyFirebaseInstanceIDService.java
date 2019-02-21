package admin.com.almoskyadmin.notification;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.loopj.android.http.RequestParams;

import admin.com.almoskyadmin.activity.LoginActivity;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private ApiCalls apiCalls;
    onFirebaseIdReceivedListener mOnFirebaseIdReceivedListener;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        updateFirebaseToken(refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
      //  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       // preferences.edit().putString(Constants.FIREBASE_TOKEN, refreshedToken).apply();
    }

    private void updateFirebaseToken(String refreshedToken) {
        if (mOnFirebaseIdReceivedListener!=null) {
            mOnFirebaseIdReceivedListener.onFirebaseIdRecived(refreshedToken);
        }
    }

    public interface onFirebaseIdReceivedListener {
        void onFirebaseIdRecived(String token);
    }

    public void setOnFirebaseIdReceived(onFirebaseIdReceivedListener listener) {
        mOnFirebaseIdReceivedListener = listener;
    }

}