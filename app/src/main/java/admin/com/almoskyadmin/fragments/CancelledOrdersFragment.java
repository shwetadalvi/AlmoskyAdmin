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
import admin.com.almoskyadmin.adapter.CancelledListAdapter;
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
public class CancelledOrdersFragment extends Fragment implements HomeActivity.FragmentCancelledResultInterface{
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    HomeActivity tabHostActivity;
    RecyclerView rvOrders;
    CancelledOrdersFragment collectedfragment;
    public static int i=0;
    private boolean isVisible;
    private boolean isStarted;

    private static final String ARG_PAGE_NUMBER = "page_number";

    public CancelledOrdersFragment() {
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
       tabHostActivity.setCancelledlistener(this);
        collectedfragment=new CancelledOrdersFragment();


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
                params.put("status",  8);



                String url = ApiConstants.orderListUrl;
                apiCalls.callApiPost(tabHostActivity, params, dialog, url, 8);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void fragmentCancelledResultInterface(String response, int requestId) {


            //Toast.makeText(getActivity(),"action",Toast.LENGTH_LONG).show();

            try{
                rvOrders.setAdapter(null);
                Gson gson = new Gson();
                final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);

                AlmoskyAdmin.getInst().setCurrentOrders(orderList.getResult());

                CancelledListAdapter mAdapter = new CancelledListAdapter(tabHostActivity, orderList.getResult(),collectedfragment,tabHostActivity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
                rvOrders.setLayoutManager(mLayoutManager);
                rvOrders.setItemAnimator(new DefaultItemAnimator());
                rvOrders.setAdapter(mAdapter);


            }catch (Exception e){

            }


        }






}
