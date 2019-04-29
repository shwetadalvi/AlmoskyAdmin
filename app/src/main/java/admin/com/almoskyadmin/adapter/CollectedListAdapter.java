package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.fragments.CollectedFragments;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.viewholder.CollectedRecyclerViewHolders;


public class CollectedListAdapter extends RecyclerView.Adapter<CollectedRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<OrderListdto.Result> items;
    CollectedFragments _fragment;
    HomeActivity _activity;

   // OrderConfirmationActivity _activity;


    public CollectedListAdapter(Context context, ArrayList<OrderListdto.Result> data, CollectedFragments fragment, HomeActivity activity) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
        this._fragment=fragment;
       this._activity=activity;
    }

    @Override
    public CollectedRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_active, null);
        CollectedRecyclerViewHolders rcv = new CollectedRecyclerViewHolders(layoutView, context,_fragment,_activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CollectedRecyclerViewHolders holder, int position) {
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
