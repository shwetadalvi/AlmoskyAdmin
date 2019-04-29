package admin.com.almoskyadmin.activity.editOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


//import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import admin.com.almoskyadmin.AlmoskyAdmin;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.model.data1;
import admin.com.almoskyadmin.model.priceListdto;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;

public class ItemDetailsAddActivity extends BaseActivity {

    private TextView title;
    String catName,itemName;
    int itemId,catId;


    private ArrayList<data1.Detail.Item> drytemp;
    private ArrayList<data1.Detail.Item> washtemp;
    private ArrayList<data1.Detail.Item> irontemp;
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    SimpleArcDialog dialog;
    ArrayList<priceListdto.Detail> priceDetails;
    TextView dryprice,washprice,ironprice;
    private TextView dryCleanCount;
    private TextView ironingCount;
    private TextView washIronCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_add);



        appPrefes=new AppPrefes(this);
        apiCalls=new ApiCalls();
        dialog=new SimpleArcDialog(this);
        getPriceList();


        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView dryCleanMinus = (ImageView) findViewById(R.id.dryCleanMinus);
        ImageView dryCleanPlus = (ImageView) findViewById(R.id.dryCleanPlus);
         dryCleanCount = (TextView) findViewById(R.id.dryCleanCount);



        ImageView washIronMinus = (ImageView) findViewById(R.id.washIronMinus);
        ImageView washIronPlus = (ImageView) findViewById(R.id.washIronPlus);
        washIronCount = (TextView) findViewById(R.id.washIronCount);

        ImageView ironingPlus = (ImageView) findViewById(R.id.ironingPlus);
        ImageView ironingMinus = (ImageView) findViewById(R.id.ironingMinus);
        ironingCount = (TextView) findViewById(R.id.ironingCount);

         dryprice=(TextView) findViewById(R.id.tv_dry_price);
         washprice=(TextView) findViewById(R.id.tv_wash_price);
         ironprice=(TextView) findViewById(R.id.tv_iron_price);







        ImageView iv_detail = (ImageView) findViewById(R.id.iv_detail);
        TextView addToBasketId = (TextView) findViewById(R.id.addToBasketId);
        String url = getIntent().getExtras().getString("url");
        catId=getIntent().getExtras().getInt("catId");
        catName=getIntent().getExtras().getString("catname");
        itemId=getIntent().getExtras().getInt("itemId");
        itemName=getIntent().getExtras().getString("itemname");


       // int amt=getAmount(itemId,1);

   //     dryprice.setText(String.valueOf(getAmount(itemId,1)));


        drytemp= AlmoskyAdmin.getInst().getDrycleanList1temp();
        washtemp=AlmoskyAdmin.getInst().getWashList1temp();
        irontemp=AlmoskyAdmin.getInst().getIronList1temp();







        if(null!=drytemp){

            for(int i=0;i<drytemp.size();i++){

                if(drytemp.get(i).getItemId().equals(itemId)){
                    dryCleanCount.setText(drytemp.get(i).getItemcount().toString());
                  //  dryprice.setText(String.valueOf(getAmount(dry.get(i).getItemId(),1)));
                }

            }
        }
        if(null!=washtemp){

            for(int i=0;i<washtemp.size();i++){

                if(washtemp.get(i).getItemId().equals(itemId)){
                    washIronCount.setText(washtemp.get(i).getItemcount().toString());
                }

            }
        }

        if(null!=irontemp){

            for(int i=0;i<irontemp.size();i++){

                if(irontemp.get(i).getItemId().equals(itemId)){
                    ironingCount.setText(irontemp.get(i).getItemcount().toString());
                }

            }
        }


        Picasso.get().load(url).into(iv_detail);


        //Glide.with(this).load(url).into(iv_detail);
        addToBasketId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ItemDetailsAddActivity.this, CategoryListActivity.class);
                intent.putExtra("className", "ItemDetailsAddActivity");
               // intent.putExtra("count", 1);
               // intent.putExtra("price", "7");
                startActivity(intent);
            }
        });

        dryCleanMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dryprice.getText().toString().equals(null) || dryprice.getText().toString().equals("")){

                }else {
                    int countDryClean = Integer.parseInt(dryCleanCount.getText().toString());
                    if (countDryClean > 0) {
                        countDryClean = countDryClean - 1;
                        dryCleanCount.setText("" + countDryClean);
                        int totcount=AlmoskyAdmin.getInst().getCartcount()-1;
                        AlmoskyAdmin.getInst().setCartcount(totcount);
                        updateData(itemId,itemName,countDryClean,"dryclean");
                    }
                }

            }
        });
        dryCleanPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(dryprice.getText().toString().equals(null) || dryprice.getText().toString().equals(""))
                    {

                    }else {
                    int countDryClean = Integer.parseInt(dryCleanCount.getText().toString());
                    countDryClean = countDryClean + 1;
                    dryCleanCount.setText("" + countDryClean);

                    updateData(itemId, itemName, countDryClean, "dryclean");
                }
                }
        });

        washIronMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(washprice.getText().toString().equals(null) || washprice.getText().toString().equals("")){


            }
            else {
                    int countWashIron = Integer.parseInt(washIronCount.getText().toString());
                    if (countWashIron > 0) {
                        countWashIron = countWashIron - 1;
                        washIronCount.setText("" + countWashIron);
                        updateData(itemId,itemName,countWashIron,"washiron");
                    }

                }
            }
        });
        washIronPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(washprice.getText().toString().equals(null) || washprice.getText().toString().equals("")){


                }else {
                    int countWashIron = Integer.parseInt(washIronCount.getText().toString());
                    countWashIron = countWashIron + 1;
                    washIronCount.setText("" + countWashIron);
                    updateData(itemId,itemName,countWashIron,"washiron");
                }
            }
        });

        ironingMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ironprice.getText().toString().equals(null) || ironprice.getText().toString().equals("")){




            }else {
                    int countIroning = Integer.parseInt(ironingCount.getText().toString());
                    if (countIroning > 0) {
                        countIroning = countIroning - 1;
                        ironingCount.setText("" + countIroning);
                        updateData(itemId,itemName,countIroning,"iron");
                    }
                }

            }
        });
        ironingPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String iro=ironprice.getText().toString();
              //  Toast.makeText(getApplicationContext(),""+iro,Toast.LENGTH_LONG).show();

                if(ironprice.getText().toString().equals(null) || ironprice.getText().toString().equals("")){


                }else{

                    int countIroning = Integer.parseInt(ironingCount.getText().toString());
                    countIroning = countIroning + 1;
                    ironingCount.setText("" + countIroning);
                    updateData(itemId,itemName,countIroning,"iron");
                }
            }
        });

    }

    private void init() {
    }

    @Override
    public void getResponse(String response, int requestId) {

        try {
            Gson gson = new Gson();

            final priceListdto priceList = gson.fromJson(response, priceListdto.class);
            priceListdto dto=new priceListdto();
            priceListdto.Detail detaildto=dto. new Detail();

            priceDetails=priceList.getDetail();

            AlmoskyAdmin.getInst().setItempriceList(priceDetails);


            if(getAmount(itemId,1)!=0){
                dryprice.setText(String.valueOf("AED"+getAmount(itemId,1)));
            }
            if(getAmount(itemId,2)!=0){
                washprice.setText(String.valueOf("AED"+getAmount(itemId,2)));
            }

            if(getAmount(itemId,3)!=0){
                ironprice.setText(String.valueOf("AED"+getAmount(itemId,3)));
            }



        }catch (Exception e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  getPriceList();
    }

    private void getPriceList() {
        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.getPriceListUrl;
        apiCalls.callApiPost(ItemDetailsAddActivity.this, params, dialog, url, 13);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
/*
        int dryCount=Integer.parseInt(dryCleanCount.getText().toString());
        int washCount=Integer.parseInt(washIronCount.getText().toString());
        int ironCount=Integer.parseInt(ironingCount.getText().toString());

        if(dryCount!=0){

            updateData(itemId,itemName,dryCount,"dryclean");

        }
        if(washCount!=0){

            updateData(itemId,itemName,dryCount,"washiron");

        }
        if(ironCount!=0){

            updateData(itemId,itemName,ironCount,"iron");

        }
        */

    }

    private void updateData(int itemId, String itemName, int count, String type) {



        if(type.equals("dryclean")){
            int isPresent=0;
            ArrayList<data1.Detail.Item> drycleanList= AlmoskyAdmin.getInst().getDrycleanList1temp();
            if(null!=drycleanList){


                for(int i=0;i<drycleanList.size();i++){
                    if(drycleanList.get(i).getItemId().equals(itemId)){
                       // drycleanList.get(i).setItemcount(count);
                       // AlmoskyAdmin.getInst().getDrycleanList1().get(i).setItemcount(count);
                        AlmoskyAdmin.getInst().getDrycleanList1temp().get(i).setItemcount(count);
                        //int amount= Integer.parseInt(Almosky.getInst().getDrycleanList().get(i).getAmount());
                        double amount= getAmount(itemId,1);
                        AlmoskyAdmin.getInst().getDrycleanList1temp().get(i).setTotal(String.valueOf(amount*count));
                       // drytemp.get(i).setTotal(String.valueOf(amount*count));
                        isPresent=1;
                    }


                }
                if(isPresent==0){

                    ArrayList<data1.Detail.Item> drylst=new ArrayList<>();

                    data1 obj=new data1();
                    data1.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data1.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,1)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);



                    itemObj.setTotal(String.valueOf(Double.parseDouble(String.valueOf(getAmount(itemId,1)))*count));
                    drylst.add(itemObj);

                    if(null!=drycleanList){
                        drycleanList.add(itemObj);
                    }
                    //AlmoskyAdmin.getInst().setDrycleanList1(drycleanList);
                    AlmoskyAdmin.getInst().setDrycleanList1temp(drycleanList);

                }

            }else {


                data1 obj=new data1();
                ArrayList<data1.Detail.Item> drylst=new ArrayList<>();
            //    ArrayList<data.Detail.Item> items=new ArrayList<>();

                try{

                    //  JSONArray mainArray=new JSONArray();
                    //  JSONObject mainobj=new JSONObject();
                    data1.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data1.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,1)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);

                    itemObj.setTotal(String.valueOf(Double.parseDouble(String.valueOf(getAmount(itemId,1)))*count));
                    drylst.add(itemObj);

                   // AlmoskyAdmin.getInst().setDrycleanList1(drylst);
                    AlmoskyAdmin.getInst().setDrycleanList1temp(drylst);

                }catch (Exception e){

                    e.printStackTrace();
                }

            }
        }

        //washiron list
        else if(type.equals("washiron")){
            int isPresent=0;
            ArrayList<data1.Detail.Item> washironList=AlmoskyAdmin.getInst().getWashList1temp();
            if(null!=washironList){


                for(int i=0;i<washironList.size();i++){
                    if(washironList.get(i).getItemId().equals(itemId)){
                        washironList.get(i).setItemcount(count);
                       // int amount= Integer.parseInt(Almosky.getInst().getWashList().get(i).getAmount());
                        double amount= getAmount(itemId,2);
                        AlmoskyAdmin.getInst().getWashList1temp().get(i).setTotal(String.valueOf(amount*count));
                        isPresent=1;
                    }


                }
                if(isPresent==0){

                    ArrayList<data1.Detail.Item> wlst=new ArrayList<>();
                    data1 obj=new data1();
                    data1.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data1.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,2)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);
                    itemObj.setTotal(String.valueOf(getAmount(itemId,2)*count));

                    wlst.add(itemObj);

                    if(null!=washironList){
                        washironList.add(itemObj);

                    }
                   // AlmoskyAdmin.getInst().setWashList1(washironList);
                    AlmoskyAdmin.getInst().setWashList1temp(washironList);



                }

            }else {

                ArrayList<data1.Detail.Item> wlst=new ArrayList<>();

                data1 obj=new data1();
                //    ArrayList<data.Detail.Item> items=new ArrayList<>();

                try{

                    //  JSONArray mainArray=new JSONArray();
                    //  JSONObject mainobj=new JSONObject();
                    data1.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data1.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,2)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);

                    itemObj.setTotal(String.valueOf((getAmount(itemId,2))*count));
                    wlst.add(itemObj);

                   // AlmoskyAdmin.getInst().setWashList1(wlst);
                    AlmoskyAdmin.getInst().setWashList1temp(wlst);
                }catch (Exception e){

                    e.printStackTrace();
                }

            }
        }

        //iron

        else if(type.equals("iron")){
            int isPresent=0;
            ArrayList<data1.Detail.Item> ironList=AlmoskyAdmin.getInst().getIronList1temp();
            if(null!=ironList){


                for(int i=0;i<ironList.size();i++){
                    if(ironList.get(i).getItemId().equals(itemId)){
                        ironList.get(i).setItemcount(count);
                        //int amount= Integer.parseInt(Almosky.getInst().getIronList().get(i).getAmount());
                        double amount= getAmount(itemId,3);
                        AlmoskyAdmin.getInst().getIronList1temp().get(i).setTotal(String.valueOf(amount*count));
                        isPresent=1;
                    }


                }
                if(isPresent==0){
                    ArrayList<data1.Detail.Item> ilst=new ArrayList<>();

                    data1 obj=new data1();
                    data1.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data1.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,3)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);
                    itemObj.setTotal(String.valueOf(getAmount(itemId,3)*count));
                    ilst.add(itemObj);

                    if(null!=ironList){
                        ironList.add(itemObj);

                    }
                    AlmoskyAdmin.getInst().setIronList1temp(ironList);



                    //Almosky.getInst().setIronList(ilst);

                }

            }else {

                ArrayList<data1.Detail.Item> ilst=new ArrayList<>();

                data1 obj=new data1();
                //    ArrayList<data.Detail.Item> items=new ArrayList<>();

                try{


                    //  JSONArray mainArray=new JSONArray();
                    //  JSONObject mainobj=new JSONObject();
                    data1.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data1.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,3)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);
                    itemObj.setTotal(String.valueOf(getAmount(itemId,3)*count));

                    ilst.add(itemObj);

                    AlmoskyAdmin.getInst().setIronList1temp(ilst);
                }catch (Exception e){

                    e.printStackTrace();
                }

            }
        }


    }

    private double getAmount(int itemId,int serviceId) {

        System.out.println("Item"+itemId+"Service"+serviceId);


        double amount=0;
        for(int i=0;i<AlmoskyAdmin.getInst().getItempriceList().size();i++){
            if(serviceId==AlmoskyAdmin.getInst().getItempriceList().get(i).getServiceId()){
                ArrayList<priceListdto.Detail.Item> curntlist=AlmoskyAdmin.getInst().getItempriceList().get(i).getItems();

                int type=AlmoskyAdmin.getInst().getDeliveryType();
                System.out.println("find type"+type);

                for(int j=0; j<curntlist.size(); j++){
                   // if(Almosky.getInst().getDeliveryType().equals("normal")) {
                    if(AlmoskyAdmin.getInst().getDeliveryType()==1) {

                        String ctype = curntlist.get(j).getDeliveryType();
                      //  System.out.println("find curent" + ctype);
                        if (itemId == curntlist.get(j).getItemId() && curntlist.get(j).getDeliveryType().equals("NORMAL")) {
                            amount = Double.parseDouble(curntlist.get(j).getPrice());
                            System.out.println("normalamounc"+amount);

                            return amount;
                        }
                    }

                      //  if(Almosky.getInst().getDeliveryType().equals("fast")){
                        if(AlmoskyAdmin.getInst().getDeliveryType()==2){
                            String cctype=curntlist.get(j).getDeliveryType();
                          //  System.out.println("find curentfast"+cctype);
                            if(itemId == curntlist.get(j).getItemId() && !curntlist.get(j).getDeliveryType().equals("NORMAL")){
                                amount= Double.parseDouble(curntlist.get(j).getPrice());
                                System.out.println("fastamounc"+amount);

                                return amount;
                            }

                        }





                }

        /*        for(int j=0; j<curntlist.size(); j++){
                    if(Almosky.getInst().getDeliveryType().equals("normal")){
                        String ctype=curntlist.get(j).getDeliveryType();
                        System.out.println("find curent"+ctype);
                        if(itemId==curntlist.get(j).getItemId() && curntlist.get(j).getDeliveryType().equals("NORMAL")){
                            amount=curntlist.get(j).getPrice();

                            return amount;
                        }

                        if(Almosky.getInst().getDeliveryType().equals("fast")){
                            String cctype=curntlist.get(j).getDeliveryType();
                            System.out.println("find curentfast"+cctype);
                            if(itemId==curntlist.get(j).getItemId() && curntlist.get(j).getDeliveryType().equals("FAST SERVICE")){
                                amount=curntlist.get(j).getPrice();

                                return amount;
                            }

                        }


                    }


                }  */

            }



        }

        return amount;
    }
}
