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
import admin.com.almoskyadmin.adapter.CompletedListAdapter;
import admin.com.almoskyadmin.adapter.OrderListAdapter;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedOrdersFragments extends Fragment implements HomeActivity.FragmentCompletedResultInterface{
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    HomeActivity tabHostActivity;
    RecyclerView rvOrders;
CompletedOrdersFragments frag;
    private static final String ARG_PAGE_NUMBER = "page_number";

    public CompletedOrdersFragments() {
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
       tabHostActivity.setCompletedListener(this);
     //  tabHostActivity.setCompletedListener(this);
       frag=new CompletedOrdersFragments();
        rvOrders=(RecyclerView)view.findViewById(R.id.rvorderList3);
        apiCalls=new ApiCalls();
        appPrefes=new AppPrefes(tabHostActivity);
        dialog=new SimpleArcDialog(tabHostActivity);
        System.out.println("inside complete");
      //  getOrders();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getOrders();
            // load data here
        }else{
            // fragment is no longer visible
        }
    }

    private void getOrders() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email",  appPrefes.getData(PrefConstants.email));
        params.put("status", 3 );

       

        String url = ApiConstants.orderListUrl;
        apiCalls.callApiPost(tabHostActivity, params, dialog, url, 4);


    }

    @Override
    public void fragmentcompletedResultInterface(String response, int requestId) {
        try{
            rvOrders.setAdapter(null);
            Gson gson = new Gson();
            final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);
            CompletedListAdapter mAdapter = new CompletedListAdapter(tabHostActivity, orderList.getResult(),frag);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
            rvOrders.setLayoutManager(mLayoutManager);
            rvOrders.setItemAnimator(new DefaultItemAnimator());
            rvOrders.setAdapter(mAdapter);


        }catch (Exception e){

        }

    }


}
