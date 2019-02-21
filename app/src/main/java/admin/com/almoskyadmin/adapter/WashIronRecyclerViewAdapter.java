package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.OrderDetailsActivity;
import admin.com.almoskyadmin.model.data;
import admin.com.almoskyadmin.viewholder.WashIronRecyclerViewHolders;


public class WashIronRecyclerViewAdapter extends RecyclerView.Adapter<WashIronRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<data.Result> items;
    OrderDetailsActivity _activity;


    public WashIronRecyclerViewAdapter(Context context, ArrayList<data.Result> data, OrderDetailsActivity activity) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
        this._activity=activity;
    }
    @Override
    public WashIronRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dry_clean_item, null);
        WashIronRecyclerViewHolders rcv = new WashIronRecyclerViewHolders(layoutView, context,_activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(WashIronRecyclerViewHolders holder, int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
//        holder.bind(status);
        data.Result item=items.get(position);
        holder.bind(item);


        holder.dryitem.setText(items.get(position).getItemName());
        holder.drycount.setText(String.valueOf(items.get(position).getQty()));
        // holder.drycount.setText(String.valueOf(items.get(position).getQty()));
        holder.dryamount.setText(String.valueOf((items.get(position).getQty())*(items.get(position).getPrice())));

        //  holder.dryamount.setText(String.valueOf(items.get(position).getTotal()));

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
