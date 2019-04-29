package admin.com.almoskyadmin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.adapter.DriverListAdapter;
import admin.com.almoskyadmin.adapter.RemarksAdapter;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.interfaces.ClickListeners;
import admin.com.almoskyadmin.model.AddRemarkResponse;
import admin.com.almoskyadmin.model.Driverdto;
import admin.com.almoskyadmin.model.RemarkList;
import admin.com.almoskyadmin.model.Remarks;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.Constants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class AddNewDriverActivity extends BaseActivity implements ClickListeners.ItemClick<Driverdto.Result> {

    android.support.v7.widget.AppCompatEditText email, password, repaswd;
    private AppPrefes appPrefes;
    Button add;
    private ApiCalls apiCalls;
    SimpleArcDialog dialog;
    private RecyclerView driverRecycler;
    private int driverIdOfEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_driver);

        email = (AppCompatEditText) findViewById(R.id.edt_email);
        password = (AppCompatEditText) findViewById(R.id.edt_password);
        repaswd = (AppCompatEditText) findViewById(R.id.edt_repassword);
        driverRecycler = findViewById(R.id.driverRecycler);

        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        appPrefes = new AppPrefes(this);


        add = (Button) findViewById(R.id.btn_add_driver);

        if (Utility.isNetworkOnline(AddNewDriverActivity.this)) {
            getDriverList();
        } else {

            new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Check Internet Connection")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                        }
                    }).show();


        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (add.getText().toString().equalsIgnoreCase("Add")) {
                    if (validateFields()) {
                        if (Utility.isNetworkOnline(AddNewDriverActivity.this)) {
                            addDriver();
                        } else {

                            new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
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


                } else {
                    if (!(email.getText().toString()).isEmpty())
                        updateDriver();
                }
                resetFields();
            }
        });

    }

    private void resetFields() {
        email.setText("");
        password.setText("");
        repaswd.setText("");
        add.setText("Add");
    }

    private void getDriverList() {

        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        String url = ApiConstants.allDriverListUrl;
        Log.d("Success-", "JSON:" + "Inside DriverList"+params.toString());
        apiCalls.callApiPost(AddNewDriverActivity.this, params, dialog, url, 1);
    }
    private void updateDriver() {

        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("driverId", driverIdOfEditItem);
        params.put("driverName", email.getText().toString());
        params.put("password", password.getText().toString());
        String url = ApiConstants.edtDriver;
        Log.d("Success-", "JSON:" + "Inside Update Remarks"+params.toString());
        apiCalls.callApiPost(AddNewDriverActivity.this, params, dialog, url, 3);
    }

    private void deleteDriver(Integer id) {

        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("driverId", id);
        String url = ApiConstants.deleteDriver;
        Log.d("Success-", "JSON:" + "Inside Delete Remarks"+params.toString());
        apiCalls.callApiPost(AddNewDriverActivity.this, params, dialog, url, 4);
    }

    private void addDriver() {

        dialog.show();

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("password", password.getText().toString());
        params.put("fullname", email.getText().toString());
        params.put("adminstatus", 2);


        String url = ApiConstants.BaseUrl + ApiConstants.addDriverUrl;
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

                                        resetFields();
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

    public boolean validateFields() {

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
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        Log.d("Success-", "JSON:" + "Inside response" +response);
        if (requestId == 1) {
            try {
                final Driverdto userData;
                Gson gson = new Gson();
                userData = gson.fromJson(response, Driverdto.class);
                try {

                    DriverListAdapter mAdapterDate = new DriverListAdapter(userData.getResult(), AddNewDriverActivity.this, this);
                    RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
                    driverRecycler.setLayoutManager(mLayoutManagerDate);
                    driverRecycler.setItemAnimator(new DefaultItemAnimator());
                    driverRecycler.setAdapter(mAdapterDate);


                } catch (Exception e) {

                    new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestId == 2) {
            try {
                final AddRemarkResponse userData;
                Gson gson = new Gson();

                userData = gson.fromJson(response, AddRemarkResponse.class);
                try {

                    if (userData.getStatus().equals("true")) {
                        getDriverList();
                        new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(userData.getMessage())
                                .show();
                    }

                } catch (Exception e) {

                    new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestId == 3) {
            try {
                final AddRemarkResponse userData;
                Gson gson = new Gson();

                userData = gson.fromJson(response, AddRemarkResponse.class);
                try {

                    if (userData.getStatus().equals("true")) {
                        getDriverList();
                        new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(userData.getMessage())
                                .show();
                    }

                } catch (Exception e) {

                    new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestId == 4) {
            try {
                final AddRemarkResponse userData;
                Gson gson = new Gson();

                userData = gson.fromJson(response, AddRemarkResponse.class);
                try {

                    if (userData.getStatus().equals("true")) {
                        getDriverList();
                        new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(userData.getMessage())
                                .show();
                    }

                } catch (Exception e) {

                    new SweetAlertDialog(AddNewDriverActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClickedItem(Driverdto.Result item) {
        final Dialog dialog = new Dialog(AddNewDriverActivity.this);
        dialog.setContentView(R.layout.dialog_layout_item);
        // dialog.setTitle("Title...");


        TextView textName = (TextView) dialog.findViewById(R.id.textName);
        textName.setText(item.getFullName());


        Button dialogButtonEdit = (Button) dialog.findViewById(R.id.buttonEdit);
        Button dialogButtonDelete = (Button) dialog.findViewById(R.id.buttonDelete);
        // if button is clicked, close the custom dialog
        dialogButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email.setText(item.getFullName());
                driverIdOfEditItem = item.getID();

                add.setText("Update");


                dialog.dismiss();

            }
        });
        dialogButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDriver(item.getID());
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        Intent go = new Intent(AddNewDriverActivity.this, HomeActivity.class);
        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(go);

    }


}
