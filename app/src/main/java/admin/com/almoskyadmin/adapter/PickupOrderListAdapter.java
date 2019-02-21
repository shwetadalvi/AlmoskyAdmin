package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.fragments.PickupOrdersFragments;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.viewholder.OrderRecyclerViewHolders;
import admin.com.almoskyadmin.viewholder.PickupRecyclerViewHolders;


public class PickupOrderListAdapter extends RecyclerView.Adapter<PickupRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<OrderListdto.Result> items;
    PickupOrdersFragments _frag;
    HomeActivity _activity;

   // OrderConfirmationActivity _activity;


    public PickupOrderListAdapter(Context context, ArrayList<OrderListdto.Result> data,PickupOrdersFragments frag,HomeActivity activity) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
        this._frag=frag;
       this._activity=activity;
    }

    @Override
    public PickupRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_pickup, null);
        PickupRecyclerViewHolders rcv = new PickupRecyclerViewHolders(layoutView, context,_frag,_activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(PickupRecyclerViewHolders holder, int position) {
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
