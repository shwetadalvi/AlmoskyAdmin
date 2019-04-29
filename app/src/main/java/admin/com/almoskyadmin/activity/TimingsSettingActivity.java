package admin.com.almoskyadmin.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.adapter.DaysRecyclerViewAdapter;
import admin.com.almoskyadmin.adapter.TimeRecyclerViewAdapter;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.interfaces.ClickListeners.ItemClick;
import admin.com.almoskyadmin.interfaces.ClickListeners.getTimeList;
import admin.com.almoskyadmin.model.day;
import admin.com.almoskyadmin.model.dayList;
import admin.com.almoskyadmin.model.time;
import admin.com.almoskyadmin.model.timeList;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.Constants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class TimingsSettingActivity extends BaseActivity implements ItemClick<day>, getTimeList {
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    private RecyclerView recyclerViewDates, recyclerViewTimes;
    private Button btnSubmit;
    private List<time> checkedTimeArraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timings_setting);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        recyclerViewDates = findViewById(R.id.dates);
        recyclerViewTimes = findViewById(R.id.times);
        btnSubmit = findViewById(R.id.btnSubmit);

        if (Utility.isNetworkOnline(TimingsSettingActivity.this)) {
            getDays();
        } else {

            new SweetAlertDialog(TimingsSettingActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Check Internet Connection")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                        }
                    }).show();


        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
updateTimeList(checkedTimeArraylist);
            }
        });
    }

    private void updateTimeList(List<time> timelist) {
        Log.d("Success-", "JSON:" + "Inside updateTimeList");
        try {

        JSONObject object = new JSONObject();
        object.put("email", appPrefes.getData(PrefConstants.email));
        JSONArray jsonArray = new JSONArray();
        for (time time : timelist) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("timeId", time.getSlNo());
                obj.put("enableValue", time.getValue());
                jsonArray.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
            object.put("timeSlot", jsonArray);
        String Data = object.toString();
        Log.d("Success-", "JSON:" + "Inside updateTimeList :" + object.toString());
        //     apiCalls.callApiPost(TimingsSettingActivity.this, params, dialog, url, 3);

        StringEntity entity = null;
        final SimpleArcDialog dialog = new SimpleArcDialog(TimingsSettingActivity.this);

            entity = new StringEntity(Data.toString());
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            dialog.show();

        String url = ApiConstants.BaseUrl + ApiConstants.UpdateTimings;

        new AsyncHttpClient().post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    dialog.dismiss();
                    String object = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(object);
                    String result = jsonObject.getString("result");
                    Log.d("Success-", "JSON:" + "Inside updateTimeList result :" + object.toString());
                    // if (result.equals("Data Inserted")) {
                    new SweetAlertDialog(TimingsSettingActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Success")
                            .setContentText(result)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                    // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                    // }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Success-", "JSON:" + "Inside updateTimeList error :" + error.toString());
            }
        });
        } catch (Exception e) {
//Exception
        }

    }


    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        if (requestId == 1) {
            try {
                final dayList userData;
                Gson gson = new Gson();
                userData = gson.fromJson(response, dayList.class);
                try {

                    DaysRecyclerViewAdapter mAdapterDate = new DaysRecyclerViewAdapter(TimingsSettingActivity.this, this, userData.getDay());
                    RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
                    recyclerViewDates.setLayoutManager(mLayoutManagerDate);
                    recyclerViewDates.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewDates.setAdapter(mAdapterDate);

                    getTimings(userData.getDay().get(0).getTimeId());
                } catch (Exception e) {

                    new SweetAlertDialog(TimingsSettingActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                final timeList userData;
                Gson gson = new Gson();

                userData = gson.fromJson(response, timeList.class);
                try {
                    checkedTimeArraylist = userData.getResult();
                    TimeRecyclerViewAdapter mAdapterDate = new TimeRecyclerViewAdapter(TimingsSettingActivity.this, this, userData.getResult());
                    RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
                    recyclerViewTimes.setLayoutManager(mLayoutManagerDate);
                    recyclerViewTimes.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewTimes.setAdapter(mAdapterDate);


                } catch (Exception e) {

                    new SweetAlertDialog(TimingsSettingActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    private void getTimings(int timeId) {
        Log.d("Success-", "JSON:" + "Inside Timings");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("timeId", timeId);
        params.put("pickuptime", "");
        String url = ApiConstants.getTimings;
        Log.d("Success-", "JSON:" + "Inside Timings :" + params.toString());
        apiCalls.callApiPost(TimingsSettingActivity.this, params, dialog, url, 2);

    }

    private void getDays() {
        Log.d("Success-", "JSON:" + "Inside Days");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("pdate","");
        params.put("deltype", "");
        String url = ApiConstants.getDays;
        apiCalls.callApiPost(TimingsSettingActivity.this, params, dialog, url, 1);
    }

    @Override
    public void onClickedItem(day item) {
        getTimings(item.getTimeId());
    }

    @Override
    public void onClickedTimeItem(List<time> timelist) {
      //  checkedTimeArraylist = new ArrayList<>();
        Log.d("Success-", "JSON:" + "Inside onClickedItem :" + timelist.toString());
        checkedTimeArraylist = timelist;
//updateTimeList(timelist);
    }

   /* @Override
    public void onClickedTimeItem(time item) {
        Log.d("Success-", "JSON:" + "Inside onClickedItem :" + item.getValue().toString());
        // checkedTimeArraylist = timelist;
        // updateTimeList();
        for (time time : checkedTimeArraylist) {
          if(time.getSlNo().equals(item.getSlNo()))
              time.setValue(item.getValue());


        }
        updateTimeList();
    }*/
}
