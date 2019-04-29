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
import admin.com.almoskyadmin.fragments.DeliveredOrdersFragments;
import admin.com.almoskyadmin.model.OrderListdto;


public class DeliveredRecyclerViewHolders extends RecyclerView.ViewHolder {

    private TextView textTitle;
    TextView stat,textOrderType;
    //    UserActionCountItemBinding binding;
    public TextView orderdate,orderno,status,custname,pickdate,deldate,area;


    public final Context ctx;
    LinearLayout lyt;
    DeliveredOrdersFragments _frag;
    OrderListdto.Result data;
    HomeActivity _activity;

    public DeliveredRecyclerViewHolders(View itemView, HomeActivity context, DeliveredOrdersFragments frag) {
        super(itemView);
        ctx = context;
        this._frag=frag;
        this._activity=context;

       // orderdate = itemView.findViewById(R.id.textOrderDate);
        orderno = itemView.findViewById(R.id.textOrderNo);
       // status=itemView.findViewById(R.id.textStatus);
        custname=itemView.findViewById(R.id.custname);
        area=itemView.findViewById(R.id.orderarea);
        pickdate=itemView.findViewById(R.id.pickDate);
        deldate=itemView.findViewById(R.id.delDate);
        stat=itemView.findViewById(R.id.status);
        textOrderType=itemView.findViewById(R.id.textOrderType);

        lyt=itemView.findViewById(R.id.lytOrderList);
        lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlmoskyAdmin.getInst().setSelectedOrder(data);
                _activity.updateOrder(data.getOrderID(),"delivered");
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

        /*if(item.getOrderStatus()==5){

            stat.setVisibility(View.VISIBLE);
            stat.setText("To be Delivered");
            stat.setBackgroundColor(itemView.getResources().getColor(R.color.main_green_color));
        }
        if(item.getOrderStatus()==6){

            stat.setVisibility(View.VISIBLE);
            stat.setText("Delivered");
            stat.setBackgroundColor(itemView.getResources().getColor(R.color.colorRed));

        }*/



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
