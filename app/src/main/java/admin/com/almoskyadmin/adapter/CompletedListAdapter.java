package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.fragments.CompletedOrdersFragments;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.viewholder.CompletedRecyclerViewHolders;
import admin.com.almoskyadmin.viewholder.OrderRecyclerViewHolders;


public class CompletedListAdapter extends RecyclerView.Adapter<CompletedRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<OrderListdto.Result> items;
    CompletedOrdersFragments _frag;
    HomeActivity _activity;

   // OrderConfirmationActivity _activity;


    public CompletedListAdapter(HomeActivity context, ArrayList<OrderListdto.Result> data,CompletedOrdersFragments frag) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
        this._frag=frag;
       this._activity=context;
    }

    @Override
    public CompletedRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, null);
        CompletedRecyclerViewHolders rcv = new CompletedRecyclerViewHolders(layoutView, _activity,_frag);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CompletedRecyclerViewHolders holder, int position) {
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
