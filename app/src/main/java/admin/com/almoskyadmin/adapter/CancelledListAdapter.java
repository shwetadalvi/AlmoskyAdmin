package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.fragments.CancelledOrdersFragment;
import admin.com.almoskyadmin.fragments.CollectedFragments;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.viewholder.CancelledRecyclerViewHolders;
import admin.com.almoskyadmin.viewholder.CollectedRecyclerViewHolders;


public class CancelledListAdapter extends RecyclerView.Adapter<CancelledRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<OrderListdto.Result> items;
    CancelledOrdersFragment _fragment;
    HomeActivity _activity;

   // OrderConfirmationActivity _activity;


    public CancelledListAdapter(Context context, ArrayList<OrderListdto.Result> data, CancelledOrdersFragment fragment, HomeActivity activity) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
        this._fragment=fragment;
       this._activity=activity;
    }

    @Override
    public CancelledRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_active, null);
        CancelledRecyclerViewHolders rcv = new CancelledRecyclerViewHolders(layoutView, context,_fragment,_activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CancelledRecyclerViewHolders holder, int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
        OrderListdto.Result status = items.get(position);
        holder.bind(status);

     //   data.Detail.Item item=items.get(position);
     //   holder.bind(item);



    }


    @Override
    public int getItemCount() {
//        if (null == itemList)
//            return 0;
//        else
//            return this.itemList.size();
        return items.size();
    }
}
