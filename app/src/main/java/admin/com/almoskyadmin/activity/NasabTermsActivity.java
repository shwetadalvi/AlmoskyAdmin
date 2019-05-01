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
import admin.com.almoskyadmin.model.AddTermsResponse;
import admin.com.almoskyadmin.model.RemarkList;
import admin.com.almoskyadmin.model.Remarks;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.Constants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class NasabTermsActivity extends BaseActivity  {
    private EditText edtTerms;
    private Button btnSave;
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    private RecyclerView remarksRecycler;
    private int remarkIdOfEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasab_terms);
        edtTerms = findViewById(R.id.edtTerms);
        btnSave = findViewById(R.id.btnSave);

        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!(edtTerms.getText().toString()).isEmpty())
                        addRemark();

            }

        });
    }

    void resetFields() {
        edtTerms.setText("");
      //  btnSave.setText("Save");
    }


    private void addRemark() {
        Log.d("Success-", "JSON:" + "Inside Add Terms");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("terms", edtTerms.getText().toString());
        String url = ApiConstants.addNasabTerms;
        apiCalls.callApiPost(NasabTermsActivity.this, params, dialog, url, 1);
    }


    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
       if (requestId == 1) {
           try {
               final AddTermsResponse userData;
               Gson gson = new Gson();

               userData = gson.fromJson(response, AddTermsResponse.class);
               try {

                   if (userData.getResult().equalsIgnoreCase("Data Updated")) {

                       new SweetAlertDialog(NasabTermsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                               .setTitleText("Success")
                               .setContentText(userData.getResult())
                               .show();

                       resetFields();
                   }

               } catch (Exception e) {

                   new SweetAlertDialog(NasabTermsActivity.this, SweetAlertDialog.ERROR_TYPE)
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




}
