package admin.com.almoskyadmin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class AddNewDriverActivity extends BaseActivity {

    android.support.v7.widget.AppCompatEditText email,password,repaswd;

    Button add;
    private ApiCalls apiCalls;
    SimpleArcDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_driver);

        email=(AppCompatEditText) findViewById(R.id.edt_email);
        password=(AppCompatEditText) findViewById(R.id.edt_password);
        repaswd=(AppCompatEditText) findViewById(R.id.edt_repassword);

        apiCalls=new ApiCalls();
        dialog=new SimpleArcDialog(this);

        add=(Button) findViewById(R.id.btn_add_driver);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields()){
                    if(Utility.isNetworkOnline(AddNewDriverActivity.this)){
                        addDriver();
                    }else {

                        new SweetAlertDialog(AddNewDriverActivity.this,SweetAlertDialog.ERROR_TYPE)
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


            }
        });

    }

    private void addDriver() {

        dialog.show();

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("password",  password.getText().toString());
        params.put("fullname",  email.getText().toString());
        params.put("adminstatus",  2);



        String url = ApiConstants.BaseUrl+ApiConstants.addDriverUrl;
     //   apiCalls.callApiPost(AddNewDriverActivity.this, params, dialog, url, 1);





        //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // requestParams.put("s", queryTerm);
        asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                dialog.dismiss();

                ///  String object= new String(response);
                //  JSONObject jsonObject = new JSONObject(object);

                try {
                    String object = new String(response.toString());
                    JSONObject jsonObject = new JSONObject(object);
                   // JSONObject result = jsonObject.getString("Result");
                    JSONObject result = new JSONObject(jsonObject.getString("Result"));

                   // String status=result.getString("status");


                    if (result.getBoolean("status")) {

                      //  Toast.makeText(AddNewDriverActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(result.getString("Message"))
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {


                                        sDialog.dismissWithAnimation();


                                    }
                                })
                                .show();
                        // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();

                }
            }
        });

    }

    public boolean validateFields(){

        if (email.getText().toString().equals("") || email.getText().toString().equals(null)) {

            email.requestFocus();
            email.setError(("Cannot be blank"));

            return false;
        }

      /*  if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){


            email.requestFocus();
            email.setError("Not a valid E-mail");
            return false;
        } */
        if (password.getText().toString().equals("") || password.getText().toString().equals(null)) {

            password.requestFocus();
            password.setError(("Cannot be blank"));

            return false;
        }
        if (repaswd.getText().toString().equals("") || repaswd.getText().toString().equals(null)) {

            repaswd.requestFocus();
            repaswd.setError(("Cannot be blank"));

            return false;
        }
        return true;

    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        Intent go=new Intent(AddNewDriverActivity.this,HomeActivity.class);
        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(go);

    }

    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);


    }
}
