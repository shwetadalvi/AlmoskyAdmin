package admin.com.almoskyadmin.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.fragments.ActionOrdersFragments;
import admin.com.almoskyadmin.model.OrderListdto;


public class OrderRecyclerViewHolders extends RecyclerView.ViewHolder {

    private TextView textTitle;
    TextView stat,textOrderType,textDriverName;
    //    UserActionCountItemBinding binding;
    public TextView orderdate,orderno,status,custname,pickdate,deldate,area;



    OrderListdto.Result data;

    public final Context ctx;
    LinearLayout lyt;
    ActionOrdersFragments _fragment;

    HomeActivity _activity;

    public OrderRecyclerViewHolders(final View itemView, Context context, ActionOrdersFragments fragments, HomeActivity activity) {
        super(itemView);
        ctx = context;
        _fragment=fragments;
        _activity=activity;

       // orderdate = itemView.findViewById(R.id.textOrderDate);
        orderno = itemView.findViewById(R.id.textOrderNo);
       // status=itemView.findViewById(R.id.textStatus);
        custname=itemView.findViewById(R.id.custname);
        area=itemView.findViewById(R.id.orderarea);
        pickdate=itemView.findViewById(R.id.pickDate);
        deldate=itemView.findViewById(R.id.delDate);
        lyt=itemView.findViewById(R.id.lytOrderList);
        stat=itemView.findViewById(R.id.status);
        textOrderType=itemView.findViewById(R.id.textOrderType);
        textDriverName=itemView.findViewById(R.id.textDriverName);

        lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlmoskyAdmin.getInst().setSelectedOrder(data);
                _activity.updateOrder(data.getOrderID(),"action");
            }
        });

//        binding = DataBindingUtil.bind(itemView);
    }


    public void bind(OrderListdto.Result item) {

        data=item;
      //  orderdate.setText(item.getPickupTime());
        orderno.setText(item.getOrderNo());
        custname.setText(item.getName());
        pickdate.setText(item.getPickupTime());
        deldate.setText(item.getDeliveryTime());
        area.setText(item.getArea());

        textDriverName.setVisibility(View.VISIBLE);
       /* if(item.getOrderStatus()==1){

            stat.setVisibility(View.VISIBLE);
            stat.setText("To be Collected");
            stat.setBackgroundColor(itemView.getResources().getColor(R.color.main_green_color));
        }
        if(item.getOrderStatus()==2){

            stat.setVisibility(View.VISIBLE);
            stat.setText("Collected");
            stat.setBackgroundColor(itemView.getResources().getColor(R.color.colorRed));
        }
*/
       if(item.getPickupDriverName()!= null) {
           textDriverName.setVisibility(View.VISIBLE);
           textDriverName.setText("Driver : " + item.getPickupDriverName());
       }
        if (item.getDelivery_type().equalsIgnoreCase("1"))
            textOrderType.setText("Normal Service");
        else
            textOrderType.setText("Fast Service");
        if (item.getPayment_status() == 0) {
            stat.setTextColor(itemView.getResources().getColor(R.color.colorRed));
            stat.setText("Unpaid");
        }else {
            stat.setTextColor(itemView.getResources().getColor(R.color.main_green_color));
            stat.setText("Paid");
        }

      /*  String address = "";

        if (null != item.getArea())
            if (!item.getArea().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getArea();
                else
                    address = item.getArea();

        if (null != item.getStreet())
            if (!item.getStreet().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getStreet();
                else
                    address = item.getStreet();
        if (null != item.getBlock())
            if (!item.getBlock().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getBlock();
                else
                    address = item.getBlock();


        if (null != item.getHouse())
            if (!item.getHouse().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getHouse();
                else
                    address = item.getHouse();

        if (null != item.getApartment())
            if (!item.getApartment().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getApartment();
                else
                    address = item.getApartment();
        if (null != item.getFloor())
            if (!item.getFloor().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getFloor();
                else
                    address = item.getFloor();

        addressText.setText(address);
        textTitle.setText(item.getAddressName()); */
        //   itm=item;
    }


}
