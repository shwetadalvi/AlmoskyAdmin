package admin.com.almoskyadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.model.Logindto;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity {

    private AppPrefes appPrefes;

    android.support.design.widget.TextInputEditText email, password;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_login);

        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        email = (TextInputEditText) findViewById(R.id.edtUserName);
        password = (TextInputEditText) findViewById(R.id.edtPassword);

        if (appPrefes.getBoolData(PrefConstants.isLogin)) {
            Intent go = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(go);
        }

        Button login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(v -> {
            if (validateFields()) {
                if (Utility.isNetworkOnline(LoginActivity.this)) {
                    onLogin();
                } else {

                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("No Internet")
                            .setContentText("Check Internet Connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                }
                            }).show();


                }

            }

        });

        findViewById(R.id.btnForgetPassword).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });
        FirebaseMessaging.getInstance().subscribeToTopic("Laundry");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();
                    appPrefes.saveData(PrefConstants.token, token);
                });
    }

    public boolean validateFields() {

        if (email.getText().toString().equals("") || email.getText().toString().equals(null)) {

            email.requestFocus();
            email.setError(("Cannot be blank"));

            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {


            email.requestFocus();
            email.setError("Not a valid E-mail");
            return false;
        }
        if (password.getText().toString().equals("") || password.getText().toString().equals(null)) {

            password.requestFocus();
            password.setError(("Cannot be blank"));

            return false;
        }
        return true;

    }

    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);

        try {
            final Logindto userData;
            Gson gson = new Gson();
            userData = gson.fromJson(response, Logindto.class);
            try {
                //  JSONObject objectResponse = new JSONObject(response);
                // if(userData.getResult().getStatus()) {

                appPrefes.saveData(PrefConstants.email, userData.getResult().get(0).getEmail());
                appPrefes.saveData(PrefConstants.name, userData.getResult().get(0).getName());
                // appPrefes.saveIntData(PrefConstants.uid,userData.getProfile().get(0).getID());


                appPrefes.saveBoolData(PrefConstants.isLogin, true);
               /* new SweetAlertDialog(SignUpActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success")
                        .setContentText(userData.getMessage())
                        .show();*/

                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                // Intent intent = new Intent(this, Sig);

                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities


                // intent.putExtra("finish", true);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                //   startActivity(intent);
                // finish();

              /*  }else{

                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText(userData.getResult().getMessage())
                            .show();

                }*/
                //  }


            } catch (Exception e) {

                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failed")
                        .setContentText("Login Error")
                        .show();
                e.printStackTrace();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onLogin() {


        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put(ApiConstants.email, email.getText().toString());
        params.put(ApiConstants.password, password.getText().toString());
      //  params.put(ApiConstants.registrationToken, appPrefes.getData(PrefConstants.token));
        params.put(ApiConstants.registrationToken, FirebaseInstanceId.getInstance().getToken());


        String url = ApiConstants.loginUrl;
        apiCalls.callApiPost(LoginActivity.this, params, dialog, url, 1);
    }


}
