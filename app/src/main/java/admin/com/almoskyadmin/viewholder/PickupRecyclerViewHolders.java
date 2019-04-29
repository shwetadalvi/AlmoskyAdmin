package admin.com.almoskyadmin.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.fragments.PickupOrdersFragments;
import admin.com.almoskyadmin.model.OrderListdto;


public class PickupRecyclerViewHolders extends RecyclerView.ViewHolder {

    private TextView textTitle;
    //    UserActionCountItemBinding binding;
    public TextView orderdate,orderno,status,custname,pickdate,deldate,area;
private TextView stat,textOrderType;

    public final Context ctx;
    LinearLayout lyt;
    PickupOrdersFragments _frag;
    OrderListdto.Result data;
    HomeActivity _activity;

    public PickupRecyclerViewHolders(View itemView, Context context,PickupOrdersFragments frag,HomeActivity activity) {
        super(itemView);
        ctx = context;
        _frag=frag;
        _activity=activity;

       // orderdate = itemView.findViewById(R.id.textOrderDate);
        orderno = itemView.findViewById(R.id.textOrderNo);
       // status=itemView.findViewById(R.id.textStatus);
        custname=itemView.findViewById(R.id.custname);
        area=itemView.findViewById(R.id.orderarea);
        pickdate=itemView.findViewById(R.id.pickDate);
        deldate=itemView.findViewById(R.id.delDate);
        textOrderType= itemView.findViewById(R.id.textOrderType);

        lyt=itemView.findViewById(R.id.lytOrderList);
        stat=itemView.findViewById(R.id.status);


        lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   AlmoskyAdmin.getInst().setCurentOrderId();

            //    Toast.makeText(ctx,"clicked",Toast.LENGTH_LONG).show();
                AlmoskyAdmin.getInst().setSelectedOrder(data);
                _activity.updateOrder(data.getOrderID(),"pick");
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
            stat.setTextColor(itemView.getResources().getColor(R.color.white));
            stat.setText("Unpaid");
        }else {
            stat.setTextColor(itemView.getResources().getColor(R.color.white));
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
