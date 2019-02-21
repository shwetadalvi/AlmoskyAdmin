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
import admin.com.almoskyadmin.adapter.OrderListAdapter;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActionOrdersFragments extends Fragment implements HomeActivity.FragmentActionResultInterface{
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    HomeActivity tabHostActivity;
    RecyclerView rvOrders;
    ActionOrdersFragments actionfragment;
    public static int i=0;

    private static final String ARG_PAGE_NUMBER = "page_number";

    public ActionOrdersFragments() {
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
       tabHostActivity.setActionListener(this);
       actionfragment=new ActionOrdersFragments();
        rvOrders=(RecyclerView)view.findViewById(R.id.rvorderList);
        apiCalls=new ApiCalls();
        appPrefes=new AppPrefes(tabHostActivity);
        dialog=new SimpleArcDialog(tabHostActivity);


//        getOrders();
        i=1;

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {


           // if(i!=0) {
                getOrders();
           // }
            // load data here
        }else{
            // fragment is no longer visible
        }
    }

    private void getOrders() {

        try{
            if(null!=appPrefes.getData(PrefConstants.email)){
                // if (!validate(view.getResources())) return;
                RequestParams params = new RequestParams();

                params.put("email",  appPrefes.getData(PrefConstants.email));
                params.put("status",  1);



                String url = ApiConstants.orderListUrl;
                apiCalls.callApiPost(tabHostActivity, params, dialog, url, 1);
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
        params.put("status",1);
        params.put("orderId",id);
        String url = "https://abrlaundryapp.herokuapp.com/order/update";
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
    public void fragmentActionResultInterface(String response, int requestId) {


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

            try{
                rvOrders.setAdapter(null);
                Gson gson = new Gson();
                final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);

                AlmoskyAdmin.getInst().setCurrentOrders(orderList.getResult());

                OrderListAdapter mAdapter = new OrderListAdapter(tabHostActivity, orderList.getResult(),actionfragment,tabHostActivity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
                rvOrders.setLayoutManager(mLayoutManager);
                rvOrders.setItemAnimator(new DefaultItemAnimator());
                rvOrders.setAdapter(mAdapter);


            }catch (Exception e){

            }


        }


    }


}
