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

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.adapter.InProgressOrderListAdapter;
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
public class InProgressOrdersFragments extends Fragment implements HomeActivity.FragmentInProgressResultInterface {
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    HomeActivity tabHostActivity;
    RecyclerView rvOrders;
    InProgressOrdersFragments fragments;

    private static final String ARG_PAGE_NUMBER = "page_number";

    public InProgressOrdersFragments() {
        // Required empty public constructor
    }

   /* public static InProgressOrdersFragments newInstance(int page) {
        InProgressOrdersFragments fragment = new InProgressOrdersFragments();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orderslist2, container, false);
        tabHostActivity = (HomeActivity) getActivity();
        tabHostActivity.setProgressListener(this);
        fragments = new InProgressOrdersFragments();
        rvOrders = (RecyclerView) view.findViewById(R.id.rvorderList2);
        apiCalls = new ApiCalls();
        appPrefes = new AppPrefes(tabHostActivity);
        dialog = new SimpleArcDialog(tabHostActivity);
        System.out.println("inside progress");
        // getOrders();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getOrders();
            // load data here
        } else {
            // fragment is no longer visible
        }
    }

    public void updateOrder(int id) {


        //  Toast.makeText(tabHostActivity, "heloo", Toast.LENGTH_SHORT).show();
        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        params.put("status", 3);
        params.put("orderId", id);
        String url = "https://abrlaundryapp.herokuapp.com/order/update";
        //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // requestParams.put("s", queryTerm);
        asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ///  String object= new String(response);
                //  JSONObject jsonObject = new JSONObject(object);

                try {
                    String object = new String(response.toString());
                    JSONObject jsonObject = new JSONObject(object);
                    String result = jsonObject.getString("status");


                    if (result.equals("true")) {
                        new SweetAlertDialog(tabHostActivity, SweetAlertDialog.NORMAL_TYPE)
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
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("", "onFailure: " + errorResponse);
            }
        });


    }


    private void getOrders() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        params.put("status", 2);


        String url = ApiConstants.orderListUrl;
        apiCalls.callApiPost(tabHostActivity, params, dialog, url, 3);


    }

    @Override
    public void fragmentinprogressResultInterface(String response, int requestId) {
        try {
            rvOrders.setAdapter(null);
            Gson gson = new Gson();
            final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);

            AlmoskyAdmin.getInst().setCurrentOrders(orderList.getResult());


            InProgressOrderListAdapter mAdapter = new InProgressOrderListAdapter(tabHostActivity, orderList.getResult(), fragments, tabHostActivity);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
            rvOrders.setLayoutManager(mLayoutManager);
            rvOrders.setItemAnimator(new DefaultItemAnimator());
            rvOrders.setAdapter(mAdapter);


        } catch (Exception e) {

        }
    }


}
