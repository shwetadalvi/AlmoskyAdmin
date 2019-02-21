package admin.com.almoskyadmin.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.OrderDetailsActivity;
import admin.com.almoskyadmin.model.data;

public class DryCleanRecyclerViewHolders  extends RecyclerView.ViewHolder {

//    UserActionCountItemBinding binding;
    public TextView dryitem,drycount,dryamount;
    data.Result itm;
    public OrderDetailsActivity _activty;
    public final Context ctx;




    public DryCleanRecyclerViewHolders(View itemView, Context context, OrderDetailsActivity activity) {
        super(itemView);
        ctx = context;
        _activty=activity;


        dryitem=itemView.findViewById(R.id.tv_dryitem);
        dryamount=itemView.findViewById(R.id.tv_damount);
        ImageView minus = itemView.findViewById(R.id.minus);
        ImageView plus = itemView.findViewById(R.id.plus);
        drycount = itemView.findViewById(R.id.count);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countValue = Integer.parseInt(drycount.getText().toString());
                if (countValue > 0) {
                    countValue = countValue - 1;
                    drycount.setText("" + countValue);

                    itm.setQty(countValue);
                  //  updateData(itm,countValue);
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countValue = Integer.parseInt(drycount.getText().toString());
                countValue = countValue + 1;
                drycount.setText("" + countValue);
                itm.setQty(countValue);
              //  updateData(itm,countValue);
            }
        });


//        binding = DataBindingUtil.bind(itemView);
    }


    public void bind(data.Result item) {
         itm=item;
    }


}
