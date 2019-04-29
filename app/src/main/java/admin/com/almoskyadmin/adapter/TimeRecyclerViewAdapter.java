package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.graphics.Color;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.interfaces.ClickListeners;
import admin.com.almoskyadmin.model.time;
import admin.com.almoskyadmin.viewholder.TimeRecyclerViewHolders;

public class TimeRecyclerViewAdapter extends RecyclerView.Adapter<TimeRecyclerViewHolders> {

    private List<time> _timeArray;
    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private int row_index;
    private String _activity;
    private ClickListeners.getTimeList itemClick;

    public TimeRecyclerViewAdapter(Context context,ClickListeners.getTimeList itemClick, List<time> timeArray) {
//        this.itemList = itemList;
        this.context = context;
        _timeArray = timeArray;
        //_activity=activity;
        this.itemClick = itemClick;
    }

    @Override
    public TimeRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pickup_time_item, null);
        TimeRecyclerViewHolders rcv = new TimeRecyclerViewHolders(layoutView, context);


        return rcv;
    }

    @Override
    public void onBindViewHolder(TimeRecyclerViewHolders holder, final int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
//        holder.bind(status);
        ConstraintLayout rowTime = holder.itemView.findViewById(R.id.rowTime);
        TextView time = holder.itemView.findViewById(R.id.time);
        ImageView imageCheck = holder.itemView.findViewById(R.id.imageCheck);
        time.setText(_timeArray.get(position).getFromTime()+" - "+_timeArray.get(position).getToTime());
        if(_timeArray.get(position).getValue() == 0)
            imageCheck.setImageResource(R.drawable.check_icon);
        else
            imageCheck.setImageResource(R.drawable.uncheck_icon);
        if(position==0) {

           /* if (_activity.equals("pickup")) {
                Almosky.getInst().setPickuptime(_timeArray.get(position));
            }
            if (_activity.equals("delivery")) {
                Almosky.getInst().setDeliverytime(_timeArray.get(position));
            }*/
        }
        rowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                Log.d("Success-", "JSON:" + "Inside rowTime :" + _timeArray.get(position).getFromTime());
                if(_timeArray.get(position).getValue() == 0){
                    imageCheck.setImageResource(R.drawable.uncheck_icon);
                    _timeArray.get(position).setValue(1);
                }else {
                    imageCheck.setImageResource(R.drawable.check_icon);
                    _timeArray.get(position).setValue(0);
                }
                itemClick.onClickedTimeItem(_timeArray);
              /*  if(_activity.equals("pickup")){
                    Almosky.getInst().setPickuptime(_timeArray.get(position));
                }
                if(_activity.equals("delivery")){
                    Almosky.getInst().setDeliverytime(_timeArray.get(position));
                }*/

                notifyDataSetChanged();
            }
        });
       /* if (row_index == position) {
            rowTime.setBackgroundColor(Color.parseColor("#ff3d5afe"));
            time.setTextColor(Color.parseColor("#ffffff"));
        } else {
            rowTime.setBackgroundColor(Color.parseColor("#ffffff"));
            time.setTextColor(Color.parseColor("#000000"));
        }*/
    }


    @Override
    public int getItemCount() {
//        if (null == itemList)
//            return 0;
//        else
//            return this.itemList.size();
        return _timeArray.size();
    }
}
