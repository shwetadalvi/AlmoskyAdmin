package admin.com.almoskyadmin.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.adapter.CollectedListAdapter;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollectedFragments extends Fragment implements HomeActivity.FragmentCollectedResultInterface{
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    HomeActivity tabHostActivity;
    RecyclerView rvOrders;
    CollectedFragments collectedfragment;
    public static int i=0;
    private boolean isVisible;
    private boolean isStarted;

    private static final String ARG_PAGE_NUMBER = "page_number";

    public CollectedFragments() {
        // Required empty public constructor
    }

   /* public static ActionOrdersFragments newInstance(int page) {
        ActionOrdersFragments fragment = new ActionOrdersFragments();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_orderslist, container, false);
       tabHostActivity=(HomeActivity) getActivity();
       tabHostActivity.setCollectedListener(this);
        collectedfragment=new CollectedFragments();


        rvOrders=(RecyclerView)view.findViewById(R.id.rvorderList);
        apiCalls=new ApiCalls();
        appPrefes=new AppPrefes(tabHostActivity);
        dialog=new SimpleArcDialog(tabHostActivity);


//        getOrders();
        i=1;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible) {
            Utility.clearTempData();
            getOrders();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
       /* if (isVisibleToUser) {


           // if(i!=0) {
                getOrders();
           // }
            // load data here
        }else{
            // fragment is no longer visible
        } */
        isVisible = isVisibleToUser;
        if (isVisible && isStarted){
            Utility.clearTempData();
            getOrders();
        }
    }

    private void getOrders() {

        try{
            if(null!=appPrefes.getData(PrefConstants.email)){
                // if (!validate(view.getResources())) return;
                RequestParams params = new RequestParams();

                params.put("email",  appPrefes.getData(PrefConstants.email));
                params.put("status",  2);



                String url = ApiConstants.orderListUrl;
                apiCalls.callApiPost(tabHostActivity, params, dialog, url, 6);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void updateOrder1(int id){

        //  Toast.makeText(tabHostActivity, "heloo", Toast.LENGTH_SHORT).show();
        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email","admin@gmail.com");
        params.put("status",3);
        params.put("orderId",id);
        String url = "http://148.72.64.138:3006/order/update";
        //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
       // requestParams.put("s", queryTerm);
        asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

              ///  String object= new String(response);
              //  JSONObject jsonObject = new JSONObject(object);

                try{
                    String object= new String(response.toString());
                    JSONObject jsonObject = new JSONObject(object);
                    String result = jsonObject.getString("status");



                    if(result.equals("true"))
                    {

                       Toast.makeText(tabHostActivity,"Status Updated",Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(tabHostActivity.getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText("Updated")
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
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("", "onFailure: " + errorResponse);
            }
        });



    }

    @Override
    public void fragmentcollectedResultInterface(String response, int requestId) {


        if(requestId==2){

            try{

                String object= new String(response);
                JSONObject jsonObject = new JSONObject(object);
                String result = jsonObject.getString("status");

                if(result.equals("true"))
                {
                    new SweetAlertDialog(tabHostActivity, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Success")
                            .setContentText("Success")
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
            } catch (JSONException e) {
                Toast.makeText(tabHostActivity, "msg", Toast.LENGTH_LONG).show();
            }

        }else {
            //Toast.makeText(getActivity(),"action",Toast.LENGTH_LONG).show();

            try{
                rvOrders.setAdapter(null);
                Gson gson = new Gson();
                final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);

                AlmoskyAdmin.getInst().setCurrentOrders(orderList.getResult());

                CollectedListAdapter mAdapter = new CollectedListAdapter(tabHostActivity, orderList.getResult(),collectedfragment,tabHostActivity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
                rvOrders.setLayoutManager(mLayoutManager);
                rvOrders.setItemAnimator(new DefaultItemAnimator());
                rvOrders.setAdapter(mAdapter);


            }catch (Exception e){

            }


        }


    }


}
