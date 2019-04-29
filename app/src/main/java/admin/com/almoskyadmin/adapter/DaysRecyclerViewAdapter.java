package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.interfaces.ClickListeners;
import admin.com.almoskyadmin.model.day;
import admin.com.almoskyadmin.viewholder.DaysRecyclerViewHolders;


public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewHolders> {

    private final List<day> _dateArray;
 //  private final ArrayList<String> _dayArray;

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private int row_index;
    private String _activity;
private ClickListeners.ItemClick itemClick;

    public DaysRecyclerViewAdapter(Context context, ClickListeners.ItemClick itemClick,List<day> dateArray) {
//        this.itemList = itemList;
        this.context = context;
        this.itemClick = itemClick;
        _dateArray = dateArray;

      //  this._activity=activity;
      //  this._dayArray=days;
    }

    @Override
    public DaysRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pickup_date_item, null);
        DaysRecyclerViewHolders rcv = new DaysRecyclerViewHolders(layoutView, context);


        return rcv;
    }

    @Override
    public void onBindViewHolder(DaysRecyclerViewHolders holder, final int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
        LinearLayout rowDate = holder.itemView.findViewById(R.id.rowDate);
        TextView day = holder.itemView.findViewById(R.id.day);
        TextView date = holder.itemView.findViewById(R.id.date);
        day.setText(_dateArray.get(position).getTimeDay());
      date.setText(_dateArray.get(position).getDate());

        if(position==0) {
/*
            if (_activity.equals("pickup")) {
                Almosky.getInst().setPickupdate(_dateArray.get(position));
            }
            if (_activity.equals("delivery")) {
                Almosky.getInst().setDeliverydate(_dateArray.get(position));
            }*/
        }

        rowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                itemClick.onClickedItem(_dateArray.get(position));
              /*  if(_activity.equals("pickup")){
                    Almosky.getInst().setPickupdate(_dateArray.get(position));
                }
                if(_activity.equals("delivery")){
                    Almosky.getInst().setDeliverydate(_dateArray.get(position));
                }
*/
               notifyDataSetChanged();
            }
        });
        if (row_index == position) {
            rowDate.setBackgroundColor(Color.parseColor("#ff3d5afe"));
            day.setTextColor(Color.parseColor("#ffffff"));
            date.setTextColor(Color.parseColor("#ffffff"));
        } else {
            rowDate.setBackgroundColor(Color.parseColor("#ffffff"));
            day.setTextColor(Color.parseColor("#000000"));
            date.setTextColor(Color.parseColor("#000000"));
        }

//        holder.bind();
    }


    @Override
    public int getItemCount() {
//        if (null == itemList)
//            return 0;
//        else
//            return this.itemList.size();
        return _dateArray.size();
    }
}
