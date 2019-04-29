package admin.com.almoskyadmin.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.adapter.DeliveredListAdapter;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveredOrdersFragments extends Fragment implements HomeActivity.FragmentDeliveredResultInterface{
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    HomeActivity tabHostActivity;
    RecyclerView rvOrders;
    DeliveredOrdersFragments frag;
    private boolean isVisible;
    private boolean isStarted;
    private static final String ARG_PAGE_NUMBER = "page_number";

    public DeliveredOrdersFragments() {
        // Required empty public constructor
    }

  /*  public static CompletedOrdersFragments newInstance(int page) {
        CompletedOrdersFragments fragment = new CompletedOrdersFragments();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_orderslist3, container, false);
       tabHostActivity=(HomeActivity) getActivity();
       tabHostActivity.setDeliveredListener(this);
     //  tabHostActivity.setCompletedListener(this);
       frag=new DeliveredOrdersFragments();
        rvOrders=(RecyclerView)view.findViewById(R.id.rvorderList3);
        apiCalls=new ApiCalls();
        appPrefes=new AppPrefes(tabHostActivity);
        dialog=new SimpleArcDialog(tabHostActivity);
        System.out.println("inside complete");
      //  getOrders();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible) {
            Utility.clearTempData();
            getOrders();
        }//your request method
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted){
            Utility.clearTempData();
            getOrders();
        }
           //your request method
    }

    private void getOrders() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email",  appPrefes.getData(PrefConstants.email));
        params.put("status", 5);

       

        String url = ApiConstants.orderListUrl;
        apiCalls.callApiPost(tabHostActivity, params, dialog, url, 5);


    }





    @Override
    public void fragmentdeliveredResultInterface(String response, int requestId) {
        try{

          //  System.out.println("ctshowing Delivered");
            rvOrders.setAdapter(null);
            Gson gson = new Gson();
            final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);
            DeliveredListAdapter mAdapter = new DeliveredListAdapter(tabHostActivity, orderList.getResult(),frag);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
            rvOrders.setLayoutManager(mLayoutManager);
            rvOrders.setItemAnimator(new DefaultItemAnimator());
            rvOrders.setAdapter(mAdapter);


        }catch (Exception e){

        }

    }


}
