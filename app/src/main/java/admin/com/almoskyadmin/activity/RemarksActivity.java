package admin.com.almoskyadmin.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.adapter.RemarksAdapter;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.interfaces.ClickListeners;
import admin.com.almoskyadmin.model.AddRemarkResponse;
import admin.com.almoskyadmin.model.RemarkList;
import admin.com.almoskyadmin.model.Remarks;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.Constants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RemarksActivity extends BaseActivity implements ClickListeners.ItemClick<Remarks> {
    private EditText edtRemarks;
    private Button btnSave;
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    private RecyclerView remarksRecycler;
    private int remarkIdOfEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remarks);
        edtRemarks = findViewById(R.id.edtRemarks);
        btnSave = findViewById(R.id.btnSave);
        remarksRecycler = findViewById(R.id.remarksRecycler);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);

        if (Utility.isNetworkOnline(RemarksActivity.this)) {
            getRemarks();
        } else {

            new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Check Internet Connection")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                        }
                    }).show();


        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSave.getText().toString().equalsIgnoreCase("Save")) {

                    addRemark();

                } else {
                    if (!(edtRemarks.getText().toString()).isEmpty())
                        updateRemark();
                }
                resetFields();


            }

        });
    }

    void resetFields() {
        edtRemarks.setText("");
        btnSave.setText("Save");
    }

    private void getRemarks() {
        Log.d("Success-", "JSON:" + "Inside Remarks");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        // params.put(Constants.email, "deepu.tk2@gmail.com");
        String url = ApiConstants.remarkList;
        apiCalls.callApiPost(RemarksActivity.this, params, dialog, url, 1);
    }

    private void addRemark() {
        Log.d("Success-", "JSON:" + "Inside Add Remarks");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("remarks", edtRemarks.getText().toString());
        String url = ApiConstants.addRemark;
        apiCalls.callApiPost(RemarksActivity.this, params, dialog, url, 2);
    }

    private void updateRemark() {
        Log.d("Success-", "JSON:" + "Inside Update Remarks");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("id", remarkIdOfEditItem);
        params.put("remarks", edtRemarks.getText().toString());
        String url = ApiConstants.updateRemark;
        apiCalls.callApiPost(RemarksActivity.this, params, dialog, url, 3);
    }

    private void deleteRemark(Integer id) {
        Log.d("Success-", "JSON:" + "Inside Delete Remarks");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("id", id);
        String url = ApiConstants.deleteRemark;
        apiCalls.callApiPost(RemarksActivity.this, params, dialog, url, 4);
    }

    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        if (requestId == 1) {
            try {
                final RemarkList userData;
                Gson gson = new Gson();
                userData = gson.fromJson(response, RemarkList.class);
                try {

                    RemarksAdapter mAdapterDate = new RemarksAdapter(userData.getResult(), RemarksActivity.this, this);
                    RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
                    remarksRecycler.setLayoutManager(mLayoutManagerDate);
                    remarksRecycler.setItemAnimator(new DefaultItemAnimator());
                    remarksRecycler.setAdapter(mAdapterDate);


                } catch (Exception e) {

                    new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                        getRemarks();
                        new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(userData.getMessage())
                                .show();
                    }

                } catch (Exception e) {

                    new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestId == 3) {
            try {
                final AddRemarkResponse userData;
                Gson gson = new Gson();

                userData = gson.fromJson(response, AddRemarkResponse.class);
                try {

                    if (userData.getStatus().equals("true")) {
                        getRemarks();
                        new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(userData.getMessage())
                                .show();
                    }

                } catch (Exception e) {

                    new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestId == 4) {
            try {
                final AddRemarkResponse userData;
                Gson gson = new Gson();

                userData = gson.fromJson(response, AddRemarkResponse.class);
                try {

                    if (userData.getStatus().equals("true")) {
                        getRemarks();
                        new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(userData.getMessage())
                                .show();
                    }

                } catch (Exception e) {

                    new SweetAlertDialog(RemarksActivity.this, SweetAlertDialog.ERROR_TYPE)
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
    public void onClickedItem(Remarks item) {
        final Dialog dialog = new Dialog(RemarksActivity.this);
        dialog.setContentView(R.layout.dialog_layout_item);
        // dialog.setTitle("Title...");


        TextView textName = (TextView) dialog.findViewById(R.id.textName);
        textName.setText(item.getRemarks());


        Button dialogButtonEdit = (Button) dialog.findViewById(R.id.buttonEdit);
        Button dialogButtonDelete = (Button) dialog.findViewById(R.id.buttonDelete);
        // if button is clicked, close the custom dialog
        dialogButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtRemarks.setText(item.getRemarks());
                remarkIdOfEditItem = item.getId();

                btnSave.setText("Update");


                dialog.dismiss();

            }
        });
        dialogButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRemark(item.getId());
                dialog.dismiss();

            }
        });
        dialog.show();

    }


}
