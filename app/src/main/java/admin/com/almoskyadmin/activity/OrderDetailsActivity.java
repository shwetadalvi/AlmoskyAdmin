package admin.com.almoskyadmin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.DefaultPrinter;
import com.mazenrashed.printooth.data.Printable;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.PrintingCallback;
import com.vistrav.pop.Pop;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.editOrder.CategoryListActivity;
import admin.com.almoskyadmin.adapter.DryCleanRecyclerViewAdapter;
import admin.com.almoskyadmin.adapter.IroningRecyclerViewAdapter;
import admin.com.almoskyadmin.adapter.RemarksAdapter;
import admin.com.almoskyadmin.adapter.UnpaidRemarksAdapter;
import admin.com.almoskyadmin.adapter.WashIronRecyclerViewAdapter;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.databinding.ActivityOrderDetailsBinding;
import admin.com.almoskyadmin.fragments.PickupOrdersFragments;
import admin.com.almoskyadmin.model.Driverdto;
import admin.com.almoskyadmin.model.OrderDetailsModel;
import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.model.UnpaidRemarkList;
import admin.com.almoskyadmin.model.UnpaidRemarks;
import admin.com.almoskyadmin.model.data;
import admin.com.almoskyadmin.model.data1;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import admin.com.almoskyadmin.utils.print.PrintBill;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ADDRESS3;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_BILL_GREETING1;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_BILL_GREETING2;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_DATE;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_GROSS;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_AMOUNT;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_DESCRIPTION;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_QUANTITY;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_TOTAL;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_NAME;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ORDER_NO;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TAX;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TELE;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TELE2;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TRN;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_VAT;
import static admin.com.almoskyadmin.utils.constants.Constants.DISCOUNT;
import static admin.com.almoskyadmin.utils.constants.Constants.NASAB_OFFER;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS1;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS2;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS3;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS4;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS5;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS6;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS7;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS_CONDITIONS;


public class OrderDetailsActivity extends BaseActivity implements PrintingCallback {

    private static final int SCANNING_FOR_PRINTER = 123;
    String orderId;
    ArrayList<Driverdto.Result> driverdtoList = new ArrayList<>();
    ArrayList<String> drivers = new ArrayList<>();
    private ActivityOrderDetailsBinding binding;
    private OrderDetailsModel model;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    private DryCleanRecyclerViewAdapter dryCleanRecyclerViewAdapter;
    private WashIronRecyclerViewAdapter washIronRecyclerViewAdapter;
    private IroningRecyclerViewAdapter ironingRecyclerViewAdapter;
    Spinner sp_drivers;

    String driverId;
    private EditText remarks;


    @Override
    protected void onResume() {
        super.onResume();
        if (AlmoskyAdmin.getInst().isUpdate()) {
            getOrderSubDetails();
            AlmoskyAdmin.getInst().setUpdate(false);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_details);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        model = new OrderDetailsModel();
        binding.setModel(model);

        sp_drivers = (Spinner) findViewById(R.id.sp_drivers);
        drivers.add("Select a Driver");


        apiCalls = new ApiCalls();
        appPrefes = new AppPrefes(this);
        dialog = new SimpleArcDialog(this);
        // orderId=getIntent().getExtras().getString("id");


        if (null != AlmoskyAdmin.getInst().getSelectedOrder()) {

            if(AlmoskyAdmin.getInst().getSelectedOrder().getArea().equalsIgnoreCase("NASAB"))
                AlmoskyAdmin.getInst().setNisabClub(true);
            else
                AlmoskyAdmin.getInst().setNisabClub(false);
            setOrderData();

            getDriverData();

            getOrderSubDetails();
            showDrivers();
            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("completed"))
                getRemarks();

            sp_drivers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here

                    // if(null!=driverdtoList)
                    if (0 != driverdtoList.size() && position != 0) {
                        driverId = String.valueOf(driverdtoList.get(position - 1).getID());
                    }

//plz doo
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            binding.btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeclinePopUp();

                }
            });
            binding.btnChangeOrderStatusDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptOrder();
                }
            });
            binding.btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder();
                }
            });
            binding.btnNotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDriver();
                }
            });
            binding.btnResend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resendOrder();
                }
            });
            binding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {
                        if (AlmoskyAdmin.getInst().getSelectedOrder().getDelivery_type() != null) {
                            AlmoskyAdmin.getInst().setDeliveryType(Integer.parseInt(AlmoskyAdmin.getInst().getSelectedOrder().getDelivery_type()));
                        } else {
                        }
                        Intent go = new Intent(OrderDetailsActivity.this, CategoryListActivity.class);
                        startActivity(go);
                    } catch (Exception e) {

                    }
                }
            });
        }

        binding.btnPrint.setOnClickListener(v -> {
            if (!Printooth.INSTANCE.hasPairedPrinter()) {
                //SEARCH FOR PRINTER
                startActivityForResult(new Intent(OrderDetailsActivity.this, ScanningActivity.class), SCANNING_FOR_PRINTER);
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("Do you want to print the receipt ?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", (dialog, which) -> {
                    Printooth.INSTANCE.printer().setPrintingCallback(OrderDetailsActivity.this);
                    printInvoice();
                    dialog.dismiss();
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", (dialog, which) -> {
                    dialog.dismiss();
                });
                alertDialog.show();
            }
        });


    }
    private void resendOrder() {

        dialog.show();
        int selDriverId = 0;
        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("action"))
            selDriverId = AlmoskyAdmin.getInst().getSelectedOrder().getDriverPickupID();
        else if(AlmoskyAdmin.getInst().getSelectedOrderType().equals("delivered"))
        selDriverId = AlmoskyAdmin.getInst().getSelectedOrder().getDriverDeliverID();

        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
        params.put("driverId", selDriverId);

        String url = ApiConstants.BaseUrl + ApiConstants.resendOrder;
        //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // requestParams.put("s", queryTerm);
        Log.d("Inside", "Inside resendOrder url:" + url);
        Log.d("Inside", "Inside resendOrder params:" + params.toString());
        asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                dialog.dismiss();
                Log.d("Inside", "Inside resendOrder :" + response.toString());
                ///  String object= new String(response);
                //  JSONObject jsonObject = new JSONObject(object);

                try {
                    String object = new String(response.toString());
                    JSONObject jsonObject = new JSONObject(object);
                    String result = jsonObject.getString("status");


                    if (result.equals("true")) {

                        //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText("Order Resend" +
                                        " to Driver")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {


                                        sDialog.dismissWithAnimation();


                                    }
                                })
                                .show();
                        // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();


                }
            }
        });
    }

    private void notifyDriver() {

        dialog.show();
        int selDriverId = 0;
        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("action"))
            selDriverId = AlmoskyAdmin.getInst().getSelectedOrder().getDriverPickupID();

        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
        params.put("driverId", selDriverId);

        String url = ApiConstants.BaseUrl + ApiConstants.notifyDriver;
        //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // requestParams.put("s", queryTerm);
        Log.d("Inside", "Inside notify url:" + url);
        Log.d("Inside", "Inside notify params:" + params.toString());
        asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                dialog.dismiss();
                Log.d("Inside", "Inside notify :" + response.toString());
                ///  String object= new String(response);
                //  JSONObject jsonObject = new JSONObject(object);

                try {
                    String object = new String(response.toString());
                    JSONObject jsonObject = new JSONObject(object);
                    String result = jsonObject.getString("status");


                    if (result.equals("true")) {

                        //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText("Notify to Driver")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {


                                        sDialog.dismissWithAnimation();


                                    }
                                })
                                .show();
                        // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();


                }
            }
        });
    }


    private void cancelOrder() {
        dialog.show();


        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        params.put("status", 8);
        params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
        params.put("id", driverId);

        String url = ApiConstants.BaseUrl + ApiConstants.orderUpdateUrl;
        //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // requestParams.put("s", queryTerm);
        asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                dialog.dismiss();

                ///  String object= new String(response);
                //  JSONObject jsonObject = new JSONObject(object);

                try {
                    String object = new String(response.toString());
                    JSONObject jsonObject = new JSONObject(object);
                    String result = jsonObject.getString("status");


                    if (result.equals("true")) {

                        //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText("Order Cancelled")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {


                                        sDialog.dismissWithAnimation();
                                        AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                        AlmoskyAdmin.getInst().setSelectedOrder(null);
                                        AlmoskyAdmin.getInst().setIronList(null);
                                        AlmoskyAdmin.getInst().setWashList(null);
                                        AlmoskyAdmin.getInst().setDrycleanList(null);
                                        AlmoskyAdmin.getInst().setCurentOrderId(null);
                                        AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                        AlmoskyAdmin.getInst().setCurrentOrders(null);

                                        PickupOrdersFragments fg = new PickupOrdersFragments();
                                        fg.i = 0;


                                        Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(go);


                                    }
                                })
                                .show();
                        // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    PickupOrdersFragments fg = new PickupOrdersFragments();
                    fg.i = 0;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, getString(R.string.printer_connected), Toast.LENGTH_SHORT).show();
            printInvoice();
        }
    }

    private void getRemarks() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        params.put("orderId", AlmoskyAdmin.getInst().getSelectedOrder().getOrderID());
        String url = ApiConstants.UnpaidRemarkList;

        apiCalls.callApiPost(OrderDetailsActivity.this, params, dialog, url, 15);


    }

    private void printInvoice() {
        //PrintBill printBill = new PrintBill();
        bluetoothInvoicePrinting(1,
                AlmoskyAdmin.getInst().drycleanList,
                AlmoskyAdmin.getInst().ironList,
                AlmoskyAdmin.getInst().washList,
                AlmoskyAdmin.getInst().getSelectedOrder().getOrderID(),
                AlmoskyAdmin.getInst().getSelectedOrder(),
                binding.total.getText().toString(),
                binding.vattotalPrice.getText().toString(),
                binding.offerPrice.getText().toString(),
                binding.DiscountPrice.getText().toString(),
                binding.subtotalPrice.getText().toString(),
        AlmoskyAdmin.getInst().getSelectedOrder().getPickupDriverName(),
                AlmoskyAdmin.getInst().getSelectedOrder().getDeliveryDriverName());
        // printBill = null;
    }

    public void bluetoothInvoicePrinting(int count, ArrayList<data.Result> drycleanList, ArrayList<data.Result> ironList, ArrayList<data.Result> washList, int orderId, OrderListdto.Result userData, String total, String vat,
                                         String offer,String discount, String subtotal,String pickupDriverName,String deliveryDriverName) {
        for (int i = 0; i < count; i++) {
            ArrayList<Printable> printables = new ArrayList<>();
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setImage(R.drawable.ic_logo, getResources())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TAX)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_NAME)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_ADDRESS3)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TRN)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TELE)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TELE2)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_ORDER_NO + orderId)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_DATE + getDate() + " " + getTime())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(3)
                    .build());

            printables.add(new Printable.PrintableBuilder()
                    .setText("Customer Name  :" + userData.getName())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("Mobile Number  :" + userData.getcMobile())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("Address        :" + userData.getArea())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_ITEM_DESCRIPTION + createSpace(true, COMPANY_ITEM_DESCRIPTION, COMPANY_ITEM_DESCRIPTION.length()) +
                            COMPANY_ITEM_QUANTITY + createSpace(true, COMPANY_ITEM_QUANTITY, COMPANY_ITEM_QUANTITY.length()) +
                            COMPANY_ITEM_AMOUNT + createSpace(true, COMPANY_ITEM_AMOUNT, COMPANY_ITEM_AMOUNT.length()))
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            //dryclean
            for (data.Result item : drycleanList) {
                printables.add(new Printable.PrintableBuilder()
                        .setText("Dry Clean")
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
                printables.add(new Printable.PrintableBuilder()
                        .setText(item.getItemName() + createSpace(true, COMPANY_ITEM_DESCRIPTION, item.getItemName().length()) +
                                item.getQty() + createSpace(true, COMPANY_ITEM_QUANTITY, String.valueOf(item.getQty()).length()) +
                                getPrice(item.getQty(), item.getPrice()) + createSpace(true, COMPANY_ITEM_AMOUNT, getPrice(item.getQty(), item.getPrice()).length()))
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
            }
            //washList
            for (data.Result item : washList) {
                printables.add(new Printable.PrintableBuilder()
                        .setText("Wash + Iron")
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
                printables.add(new Printable.PrintableBuilder()
                        .setText(item.getItemName() + createSpace(true, COMPANY_ITEM_DESCRIPTION, item.getItemName().length()) +
                                item.getQty() + createSpace(true, COMPANY_ITEM_QUANTITY, String.valueOf(item.getQty()).length()) +
                                getPrice(item.getQty(), item.getPrice()) + createSpace(true, COMPANY_ITEM_AMOUNT, getPrice(item.getQty(), item.getPrice()).length()))
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
            }
            //iron
            for (data.Result item : ironList) {
                printables.add(new Printable.PrintableBuilder()
                        .setText("Wash + Iron")
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
                printables.add(new Printable.PrintableBuilder()
                        .setText(item.getItemName() + createSpace(true, COMPANY_ITEM_DESCRIPTION, item.getItemName().length()) +
                                item.getQty() + createSpace(true, COMPANY_ITEM_QUANTITY, String.valueOf(item.getQty()).length()) +
                                getPrice(item.getQty(), item.getPrice()) + createSpace(true, COMPANY_ITEM_AMOUNT, getPrice(item.getQty(), item.getPrice()).length()))
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
            }

            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
//            String total = String.valueOf(order.getOrder().getExlusivePrice());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_ITEM_TOTAL + createSpace(true, COMPANY_ITEM_TOTAL.length(), total.length()) + total)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            if (AlmoskyAdmin.getInst().isNisabClub()) {
                printables.add(new Printable.PrintableBuilder()
                        .setText(NASAB_OFFER + createSpace(true, NASAB_OFFER.length(), offer.length()) + offer)
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(4)
                        .build());
            }else{
                if(AlmoskyAdmin.getInst().getSelectedOrder().getCustomer_disc_perc() > 0) {
                    printables.add(new Printable.PrintableBuilder()
                            .setText(DISCOUNT + createSpace(true, DISCOUNT.length(), discount.length()) + discount)
                            .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                            .setNewLinesAfter(4)
                            .build());
                }
            }
//            String vat = String.valueOf(order.getOrder().getVat());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_VAT + createSpace(true, COMPANY_VAT.length(), vat.length()) + vat)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());

//            String gross = String.valueOf(order.getOrder().getPrice());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_GROSS + createSpace(true, COMPANY_GROSS.length(), subtotal.length()) + subtotal)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS_CONDITIONS)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS1)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS2)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS3)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS4)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS5)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS6)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS7)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText("Remarks : "+AlmoskyAdmin.getInst().getSelectedOrder().getRemarks())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText("Pickup Driver Name : "+pickupDriverName)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText("Delivery Driver Name : "+deliveryDriverName)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_BILL_GREETING1)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_BILL_GREETING2)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            Printooth.INSTANCE.printer().print(printables);
        }
    }

    public static String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date().getTime());
    }

    public static String getTime() {
        return new SimpleDateFormat("hh:mm").format(new Date().getTime());
    }

    private String getPrice(int qty, double Price) {
        double price = qty * Price;
        return String.format(Locale.US, "%.2f", price);
    }


    private String createSpace(boolean isBluetooth, String item, int length) {
        int total;
        int num;
        switch (item) {
            case COMPANY_ITEM_DESCRIPTION:
                total = isBluetooth ? 36 : 20;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_QUANTITY:
                total = isBluetooth ? 5 : 5;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_AMOUNT:
                total = isBluetooth ? 8 : 8;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
        }
        return null;
    }

    private String createSpace(boolean isBluetooth, int firstLength, int secondLegth) {
        int num = isBluetooth ? 48 - firstLength : 32 - firstLength;
        num = num - secondLegth;
        return new String(new char[num]).replace('\0', ' ');
    }

    private void showDeclinePopUp() {

        Pop.on(this)
                .with()
                .title("Decline Order")
                // .icon(R.drawable.icon)
                .cancelable(false)
                .layout(R.layout.layout_decline_popup)
                .when(new Pop.Yah() {
                    @Override
                    public void clicked(DialogInterface dialog, View view) {
                        if (remarks.getText().toString().equals("") || remarks.getText().toString().equals(null)) {
                            remarks.setError("Cannot be null");
                        } else {
                            declineOrder(remarks.getText().toString());
                        }
                    }
                })
                .when(new Pop.Nah() { // ignore if dont need negative button
                    @Override
                    public void clicked(DialogInterface dialog, View view) {


                    }
                })
                .show(new Pop.View() { // assign value to view element
                    @Override
                    public void prepare(View view) {

                        remarks = (EditText) view.findViewById(R.id.decline_remark);

                    }
                });
    }

    private void showDrivers() {

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, drivers);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_drivers.setAdapter(dataAdapter);
    }

    private void getDriverData() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));

        String url = ApiConstants.driverListUrl;
        apiCalls.callApiPost(OrderDetailsActivity.this, params, dialog, url, 20);


    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        PickupOrdersFragments fg = new PickupOrdersFragments();
        fg.i = 0;

        Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(go);

    }

    private void setOrderData() {


        String house = "", adname = "", aprtment = "", area = "", block = "", street = "", phoneNumber = "";

        if (null != AlmoskyAdmin.getInst().getSelectedOrder().getHouse()) {
            house = AlmoskyAdmin.getInst().getSelectedOrder().getHouse();
        }

        if (null != AlmoskyAdmin.getInst().getSelectedOrder().getAddressName()) {
            adname = AlmoskyAdmin.getInst().getSelectedOrder().getAddressName();
        }

        if (null != AlmoskyAdmin.getInst().getSelectedOrder().getApartment()) {
            aprtment = AlmoskyAdmin.getInst().getSelectedOrder().getApartment();
        }

        if (null != AlmoskyAdmin.getInst().getSelectedOrder().getArea()) {
            area = AlmoskyAdmin.getInst().getSelectedOrder().getArea();
        }

        if (null != AlmoskyAdmin.getInst().getSelectedOrder().getBlock()) {
            block = AlmoskyAdmin.getInst().getSelectedOrder().getBlock();
        }

        if (null != AlmoskyAdmin.getInst().getSelectedOrder().getStreet()) {
            street = AlmoskyAdmin.getInst().getSelectedOrder().getStreet();
        }
        if (null != AlmoskyAdmin.getInst().getSelectedOrder().getPhoneNumber()) {

            phoneNumber = AlmoskyAdmin.getInst().getSelectedOrder().getPhoneNumber();
        }


        String address = "";
        if (adname != "" || adname != null) {
            address = address + adname;
        }
        if (house != "" || house != null) {
            address = address + "," + house;
        }
        if (aprtment != "" || aprtment != null) {
            address = address + "," + aprtment;
        }
        if (area != "" || area != null) {
            address = address + "," + area;
        }
        if (block != "" || block != null) {
            address = address + "," + block;
        }
        if (street != "" || street != null) {
            address = address + "," + street;
        }
        if (phoneNumber != "" || phoneNumber != null) {
            address = address + "," + phoneNumber;
        }


        binding.textOrderNo.setText(String.valueOf(AlmoskyAdmin.getInst().getSelectedOrder().getOrderNo()));

        /*f (AlmoskyAdmin.getInst().getSelectedOrder().getDelivery_type().equalsIgnoreCase("1"))
            binding.textOrderType.setText("Normal Service");
        else
            binding.textOrderType.setText("Fast Service");*/
        binding.textPickUpDate.setText(AlmoskyAdmin.getInst().getSelectedOrder().getPickupTime());
        //  binding.textPickUpTime.setText(Almosky.getInst().getPickuptime());
        binding.textDeliveryDate.setText(AlmoskyAdmin.getInst().getSelectedOrder().getDeliveryTime());
        //binding.textDeliveryTime.setText(Almosky.getInst().getDeliverytime());
     //   binding.textAddress.setText(address);
        binding.customerArea.setText(area);
        binding.customerStreet.setText(street);
        binding.customerBlock.setText(block);
        binding.customerFloor.setText(AlmoskyAdmin.getInst().getSelectedOrder().getFloor());
        binding.customerApt.setText(aprtment);
        binding.customerHouse.setText(house);
        binding.edtNote.setText(AlmoskyAdmin.getInst().getSelectedOrder().getRemarks());

        try {
            String s = AlmoskyAdmin.getInst().getSelectedOrder().getDelivery_type();

            if (null != AlmoskyAdmin.getInst().getSelectedOrder().getDelivery_type()) {
                if (AlmoskyAdmin.getInst().getSelectedOrder().getDelivery_type().equals("1")) {
                    binding.textDeliveryType.setText("Delivery Type :  Normal");
                }
                if (AlmoskyAdmin.getInst().getSelectedOrder().getDelivery_type().equals("2")) {
                    binding.textDeliveryType.setText("Delivery Type :  Fast");
                }
            }

        } catch (Exception e) {

        }




/*

        if(Almosky.getInst().getDeliveryType().equals("normal")){
            binding.textDeliveryType.setText("Delivery Type :  Normal");
        }
        if(Almosky.getInst().getDeliveryType().equals("fast")){
            binding.textDeliveryType.setText("Delivery Type :  Fast");
        }
*/


    }

    private void setDryCleanAdapter() {

        if (null != AlmoskyAdmin.getInst().getDrycleanList()) {

            dryCleanRecyclerViewAdapter = new DryCleanRecyclerViewAdapter(OrderDetailsActivity.this, AlmoskyAdmin.getInst().getDrycleanList(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
            binding.dryCleanRecyclerView.setNestedScrollingEnabled(false);
            binding.dryCleanRecyclerView.setLayoutManager(mLayoutManager);
            binding.dryCleanRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.dryCleanRecyclerView.setAdapter(dryCleanRecyclerViewAdapter);
        } else {
            binding.dryCleanLayout.setVisibility(View.GONE);

        }

    }

    private void setWashIronAdapter() {

        if (null != AlmoskyAdmin.getInst().getWashList()) {

            washIronRecyclerViewAdapter = new WashIronRecyclerViewAdapter(OrderDetailsActivity.this, AlmoskyAdmin.getInst().getWashList(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
            binding.washIronRecyclerView.setNestedScrollingEnabled(false);
            binding.washIronRecyclerView.setLayoutManager(mLayoutManager);
            binding.washIronRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.washIronRecyclerView.setAdapter(washIronRecyclerViewAdapter);
        } else {

            binding.washIronLayout.setVisibility(View.GONE);
        }

    }

    private void setIroningAdapter() {

        if (null != AlmoskyAdmin.getInst().getIronList()) {

            ironingRecyclerViewAdapter = new IroningRecyclerViewAdapter(OrderDetailsActivity.this, AlmoskyAdmin.getInst().getIronList(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
            binding.ironingRecyclerView.setNestedScrollingEnabled(false);
            binding.ironingRecyclerView.setLayoutManager(mLayoutManager);
            binding.ironingRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.ironingRecyclerView.setAdapter(ironingRecyclerViewAdapter);

        } else {
            binding.ironingLayout.setVisibility(View.GONE);

        }

    }

    private void getOrderSubDetails() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        //params.put("status",1);
        params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());

        String url = ApiConstants.orderSubListUrl;
        apiCalls.callApiPost(OrderDetailsActivity.this, params, dialog, url, 10);
    }

    public void acceptOrder() {

        int status = 0;

        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("action")) {

            status = 2;
        }

        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("pick")) {

            status = 1;
        }
        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("collected")) {

            status = 3;
        }
        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("progress")) {

            status = 4;
        }
        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("out_for_delivery")) {

            status = 5;
        }

        if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("completed")) {

            status = 6;
        }

        if (status != 0) {

            if (driverId == null || driverId == "" || driverId == "0") {

                new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Select Driver")
                        .setContentText("Select Driver")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismissWithAnimation();


                            }
                        })
                        .show();


            } else {

                dialog.show();


                RequestParams params = new RequestParams();

                params.put("email", appPrefes.getData(PrefConstants.email));
                params.put("status", status);
                params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
                params.put("id", driverId);

                String url = ApiConstants.BaseUrl + ApiConstants.orderUpdateUrl;
                //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                // requestParams.put("s", queryTerm);
                asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        dialog.dismiss();

                        ///  String object= new String(response);
                        //  JSONObject jsonObject = new JSONObject(object);

                        try {
                            String object = new String(response.toString());
                            JSONObject jsonObject = new JSONObject(object);
                            String result = jsonObject.getString("status");


                            if (result.equals("true")) {

                                //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                                new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("Success")
                                        .setContentText("Updated")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {


                                                sDialog.dismissWithAnimation();
                                                AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                                AlmoskyAdmin.getInst().setSelectedOrder(null);
                                                AlmoskyAdmin.getInst().setIronList(null);
                                                AlmoskyAdmin.getInst().setWashList(null);
                                                AlmoskyAdmin.getInst().setDrycleanList(null);
                                                AlmoskyAdmin.getInst().setCurentOrderId(null);
                                                AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                                AlmoskyAdmin.getInst().setCurrentOrders(null);

                                                PickupOrdersFragments fg = new PickupOrdersFragments();
                                                fg.i = 0;


                                                Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(go);


                                            }
                                        })
                                        .show();
                                // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            dialog.dismiss();
                            PickupOrdersFragments fg = new PickupOrdersFragments();
                            fg.i = 0;

                        }
                    }
                });

            }
        }
    }

    public void declineOrder(String remarks) {

        dialog.show();

        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));
        params.put("status", "9");
        params.put("remarks", remarks);
        params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
        // params.put("id", "0");

        String url = ApiConstants.BaseUrl + ApiConstants.orderUpdateUrl;
        //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // requestParams.put("s", queryTerm);
        asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                dialog.dismiss();

                ///  String object= new String(response);
                //  JSONObject jsonObject = new JSONObject(object);

                try {
                    String object = new String(response.toString());
                    JSONObject jsonObject = new JSONObject(object);
                    String result = jsonObject.getString("status");


                    if (result.equals("true")) {
                        // dialog.dismiss();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailsActivity.this);
                        alertDialogBuilder.setMessage("Order Declined");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                        AlmoskyAdmin.getInst().setSelectedOrder(null);
                                        AlmoskyAdmin.getInst().setIronList(null);
                                        AlmoskyAdmin.getInst().setWashList(null);
                                        AlmoskyAdmin.getInst().setDrycleanList(null);
                                        AlmoskyAdmin.getInst().setCurentOrderId(null);
                                        AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                        AlmoskyAdmin.getInst().setCurrentOrders(null);

                                        PickupOrdersFragments fg = new PickupOrdersFragments();
                                        fg.i = 0;


                                        Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(go);
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                        /*new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("")

                                .setContentText("Order Declined")
                                .setConfirmText("Ok")

                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {


                                        sDialog.dismissWithAnimation();
                                        AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                        AlmoskyAdmin.getInst().setSelectedOrder(null);
                                        AlmoskyAdmin.getInst().setIronList(null);
                                        AlmoskyAdmin.getInst().setWashList(null);
                                        AlmoskyAdmin.getInst().setDrycleanList(null);
                                        AlmoskyAdmin.getInst().setCurentOrderId(null);
                                        AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                        AlmoskyAdmin.getInst().setCurrentOrders(null);

                                        PickupOrdersFragments fg = new PickupOrdersFragments();
                                        fg.i = 0;


                                        Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(go);


                                    }
                                })
                                .show();*/
                        // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    PickupOrdersFragments fg = new PickupOrdersFragments();
                    fg.i = 0;

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }


    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);

        if (requestId == 20) {

            try {
                Gson gson = new Gson();
                final Driverdto driverdto = gson.fromJson(response, Driverdto.class);


                if (driverdto.getStatus()) {

                    driverdtoList = driverdto.getResult();
                    for (int i = 0; i < driverdtoList.size(); i++) {

                        drivers.add(driverdtoList.get(i).getFullName());

                        System.out.println("driver" + driverdtoList.get(i).getFullName());
                    }

                } else {
                    Toast.makeText(OrderDetailsActivity.this, "Error in getting Drivers List", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestId == 15) {

            try {
                Gson gson = new Gson();
                final UnpaidRemarkList remarkList = gson.fromJson(response, UnpaidRemarkList.class);
                Log.d("Success-", "JSON:" + "Inside Remark result :" + response.toString());

                if (remarkList.getResult().size() != 0) {
                    binding.layoutRemarks.setVisibility(View.VISIBLE);
                    UnpaidRemarksAdapter mAdapterDate = new UnpaidRemarksAdapter(remarkList.getResult(), OrderDetailsActivity.this);
                    RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
                    binding.recyclerRemarks.setLayoutManager(mLayoutManagerDate);
                    binding.recyclerRemarks.setItemAnimator(new DefaultItemAnimator());
                    binding.recyclerRemarks.setAdapter(mAdapterDate);


                } else {
                    binding.layoutRemarks.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            double total = 0.0;
            try {
                Gson gson = new Gson();
                final data details = gson.fromJson(response, data.class);


                if (details.getStatus()) {

                    data dto = new data();
                    data1 dto1 = new data1(); //this is for data to use in edit items
                    data1.Detail dto11 = dto1.new Detail(); //this is for data to use in edit items

                    ArrayList<data.Result> drylist = new ArrayList<>();
                    ArrayList<data.Result> washlist = new ArrayList<>();
                    ArrayList<data.Result> ironlist = new ArrayList<>();
                    ArrayList<data1.Detail.Item> drylisttmp = new ArrayList<>();// for using in edit items
                    ArrayList<data1.Detail.Item> washlisttmp = new ArrayList<>();
                    ArrayList<data1.Detail.Item> ironlisttmp = new ArrayList<>();

                    for (int i = 0; i < details.getResult().size(); i++) {

                        AlmoskyAdmin.getInst().setSelectedOrderServiceId(details.getResult().get(i).getServiceID());

                        if (details.getResult().get(i).getServiceID() == 1) {

                            data.Result subdto = dto.new Result();
                            data1.Detail.Item subdtotmp = dto11.new Item();


                            subdto.setQty(details.getResult().get(i).getQty());
                            subdto.setItemID(details.getResult().get(i).getItemID());
                            subdto.setOrderID(details.getResult().get(i).getOrderID());
                            subdto.setPrice(details.getResult().get(i).getPrice());
                            subdto.setItemName(details.getResult().get(i).getItemName());
                            subdto.setServiceID(details.getResult().get(i).getServiceID());
                            subdto.setOrderSubID(details.getResult().get(i).getOrderSubID());

                            //for edit items

                            subdtotmp.setItemName(details.getResult().get(i).getItemName());
                            subdtotmp.setItemId(details.getResult().get(i).getItemID());
                            subdtotmp.setItemcount(details.getResult().get(i).getQty());
                            subdtotmp.setAmount(String.valueOf(details.getResult().get(i).getPrice()));


                            total = total + (details.getResult().get(i).getPrice() * details.getResult().get(i).getQty());

                            drylist.add(subdto);
                            drylisttmp.add(subdtotmp);


                        }
                        AlmoskyAdmin.getInst().setDrycleanList(drylist);
                        AlmoskyAdmin.getInst().setDrycleanList1temp(drylisttmp);
                    }
                    for (int i = 0; i < details.getResult().size(); i++) {

                        if (details.getResult().get(i).getServiceID() == 2) {

                            data.Result subdto = dto.new Result();
                            data1.Detail.Item subdtotmp = dto11.new Item();

                            subdto.setQty(details.getResult().get(i).getQty());
                            subdto.setItemID(details.getResult().get(i).getItemID());
                            subdto.setOrderID(details.getResult().get(i).getOrderID());
                            subdto.setPrice(details.getResult().get(i).getPrice());
                            subdto.setItemName(details.getResult().get(i).getItemName());
                            subdto.setServiceID(details.getResult().get(i).getServiceID());
                            subdto.setOrderSubID(details.getResult().get(i).getOrderSubID());

                            //for edit items

                            subdtotmp.setItemName(details.getResult().get(i).getItemName());
                            subdtotmp.setItemId(details.getResult().get(i).getItemID());
                            subdtotmp.setItemcount(details.getResult().get(i).getQty());
                            subdtotmp.setAmount(String.valueOf(details.getResult().get(i).getPrice()));


                            total = total + (details.getResult().get(i).getPrice() * details.getResult().get(i).getQty());


                            washlist.add(subdto);
                            washlisttmp.add(subdtotmp);


                        }
                        AlmoskyAdmin.getInst().setWashList(washlist);
                        AlmoskyAdmin.getInst().setWashList1temp(washlisttmp);
                    }

                    for (int i = 0; i < details.getResult().size(); i++) {

                        if (details.getResult().get(i).getServiceID() == 3) {

                            data.Result subdto = dto.new Result();

                            data1.Detail.Item subdtotmp = dto11.new Item();

                            subdto.setQty(details.getResult().get(i).getQty());
                            subdto.setItemID(details.getResult().get(i).getItemID());
                            subdto.setOrderID(details.getResult().get(i).getOrderID());
                            subdto.setPrice(details.getResult().get(i).getPrice());
                            subdto.setItemName(details.getResult().get(i).getItemName());
                            subdto.setServiceID(details.getResult().get(i).getServiceID());
                            subdto.setOrderSubID(details.getResult().get(i).getOrderSubID());

                            //for edit items

                            subdtotmp.setItemName(details.getResult().get(i).getItemName());
                            subdtotmp.setItemId(details.getResult().get(i).getItemID());
                            subdtotmp.setItemcount(details.getResult().get(i).getQty());
                            subdtotmp.setAmount(String.valueOf(details.getResult().get(i).getPrice()));


                            total = total + (details.getResult().get(i).getPrice() * details.getResult().get(i).getQty());


                            ironlist.add(subdto);
                            ironlisttmp.add(subdtotmp);


                        }
                        AlmoskyAdmin.getInst().setIronList(ironlist);
                        AlmoskyAdmin.getInst().setIronList1temp(ironlisttmp);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            if (null != AlmoskyAdmin.getInst().getDrycleanList()) {


                binding.easyOrderDetailsLayout.setVisibility(View.GONE);
                setDryCleanAdapter();
                // setWashIronAdapter();
                //setIroningAdapter();
                //   updateTotal();
            }
            if (null != AlmoskyAdmin.getInst().getIronList()) {


                binding.easyOrderDetailsLayout.setVisibility(View.GONE);
                setIroningAdapter();
                // setWashIronAdapter();
                //setIroningAdapter();
                //   updateTotal();
            }
            if (null != AlmoskyAdmin.getInst().getWashList()) {


                binding.easyOrderDetailsLayout.setVisibility(View.GONE);
                setWashIronAdapter();
                // setWashIronAdapter();
                //setIroningAdapter();
                //   updateTotal();
            } else {
                //Adding edit option in easy order
                binding.detailsLayout.setVisibility(View.VISIBLE);
                binding.lytTotal.setVisibility(View.GONE);
            }

            if (null == AlmoskyAdmin.getInst().getWashList()) {

                binding.washIronHeaderLayout.setVisibility(View.GONE);
            }
            if (null == AlmoskyAdmin.getInst().getIronList()) {

                binding.ironingHeaderLayout.setVisibility(View.GONE);
            }

            if (null == AlmoskyAdmin.getInst().getDrycleanList()) {

                binding.dryCleanHeaderLayout.setVisibility(View.GONE);
            }


            binding.total.setText("AED" + String.valueOf(total));



            if (AlmoskyAdmin.getInst().isNisabClub()) {

                double discount = (total * AlmoskyAdmin.getInst().getSelectedOrder().getNasab_disc_perc()*0.01);
                total = total - discount;
                double discountAmount = total + (total * 0.05);
               // double discountAmount = total - (total * 0.3);
                double vat = total * 0.05;
                binding.vattotalPrice.setText("AED" + String.format("%.2f",vat));
                binding.subtotalPrice.setText("AED" + String.format("%.2f",discountAmount));
                binding.offerTitle.setText(getResources().getString(R.string.text_offer)+"("+AlmoskyAdmin.getInst().getSelectedOrder().getNasab_disc_perc() +")");
                binding.nasabOffer.setVisibility(View.VISIBLE);
                binding.offerPrice.setText(String.valueOf(discount));

                if (AlmoskyAdmin.getInst().getSelectedOrder().getPayment_status() == 0) {
                    binding.textBalance.setText("Balance : AED " + String.format("%.2f",discountAmount));
                    binding.textPaid.setText("Paid : AED 0");
                }
                if (AlmoskyAdmin.getInst().getSelectedOrder().getPayment_status() == 1) {
                    binding.textPaid.setText("Paid : AED " + String.format("%.2f",discountAmount));
                    binding.textBalance.setText("Balance : AED 0");
                    binding.textPaymentMode.setVisibility(View.VISIBLE);
                    if(AlmoskyAdmin.getInst().getSelectedOrder().getPayment_mode().equalsIgnoreCase("Online")){
                        binding.textPaymentMode.setText("Credit Card : AED "+ String.format("%.2f",discountAmount));
                    }else if(AlmoskyAdmin.getInst().getSelectedOrder().getPayment_mode().equalsIgnoreCase("CASH ON DELIVERY"))
                        binding.textPaymentMode.setText("Cash On Delivery : AED "+ String.format("%.2f",discountAmount));
                }
            } else {
                if(AlmoskyAdmin.getInst().getSelectedOrder().getCustomer_disc_perc() > 0){

                    double customerDiscount = total * (AlmoskyAdmin.getInst().getSelectedOrder().getCustomer_disc_perc() * 0.01);
                    total = total - customerDiscount;
                    binding.customerDiscount.setVisibility(View.VISIBLE);
                    binding.DiscountPrice.setText(String.format("%.2f",customerDiscount));
                }
                double discountAmount = total + (total * 0.05);
                binding.nasabOffer.setVisibility(View.GONE);
                double vat = total * 0.05;
                binding.vattotalPrice.setText("AED" + String.format("%.2f",vat));
                binding.subtotalPrice.setText("AED" +String.format("%.2f",discountAmount));

                if (AlmoskyAdmin.getInst().getSelectedOrder().getPayment_status() == 0) {
                    binding.textBalance.setText("Balance : AED " + String.format("%.2f",discountAmount));
                    binding.textPaid.setText("Paid : AED 0");
                }
                if (AlmoskyAdmin.getInst().getSelectedOrder().getPayment_status() == 1) {
                    binding.textPaid.setText("Paid : AED " + String.format("%.2f",discountAmount));
                    binding.textBalance.setText("Balance : AED 0");
                    binding.textPaymentMode.setVisibility(View.VISIBLE);
                    if(AlmoskyAdmin.getInst().getSelectedOrder().getPayment_mode().equalsIgnoreCase("Online")){
                        binding.textPaymentMode.setText("Credit Card : AED "+ String.format("%.2f",discountAmount));
                    }else if(AlmoskyAdmin.getInst().getSelectedOrder().getPayment_mode().equalsIgnoreCase("CASH ON DELIVERY"))
                        binding.textPaymentMode.setText("Cash On Delivery : AED "+  String.format("%.2f",discountAmount));
                    }
                }
            }


            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("action")) {

              /*  binding.btnChangeOrderStatusDecline.setText("Accept Order");
                binding.btnDecline.setText("Decline");
                binding.lytOtherAccept.setVisibility(View.GONE);
                binding.lytAcceptBtn.setVisibility(View.VISIBLE);*/
                binding.btnResend.setVisibility(View.VISIBLE);
                binding.btnNotify.setVisibility(View.VISIBLE);
                binding.btnChangeOrderStatus.setVisibility(View.GONE);
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.VISIBLE);
                binding.lytAcceptBtn.setVisibility(View.GONE);
                binding.lytPrintConfirmBtn.setVisibility(View.GONE);
                binding.spDrivers.setVisibility(View.GONE);
                binding.btnEdit.setVisibility(View.GONE);
            }
            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("collected")) {
                binding.btnCancelOrder.setVisibility(View.VISIBLE);
              /*  binding.btnChangeOrderStatusDecline.setText("Accept Order");
                binding.btnDecline.setText("Decline");
                binding.lytOtherAccept.setVisibility(View.GONE);
                binding.lytAcceptBtn.setVisibility(View.VISIBLE);*/
                binding.btnChangeOrderStatus.setVisibility(View.GONE);
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.VISIBLE);
                binding.lytAcceptBtn.setVisibility(View.GONE);
                binding.lytPrintConfirmBtn.setVisibility(View.VISIBLE);
                binding.spDrivers.setVisibility(View.GONE);
                binding.btnEdit.setVisibility(View.VISIBLE);
            }
            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("out_for_delivery")) {

                binding.btnChangeOrderStatus.setText("Deliver Order");
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.VISIBLE);
                binding.lytAcceptBtn.setVisibility(View.GONE);
                binding.lytPrintConfirmBtn.setVisibility(View.GONE);
                binding.spDrivers.setVisibility(View.VISIBLE);

            }
            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("pick")) {

             /*   binding.btnChangeOrderStatus.setText("PickUp Order");
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.VISIBLE);
                binding.lytAcceptBtn.setVisibility(View.GONE);*/

                binding.btnCancelOrder.setVisibility(View.VISIBLE);
                binding.btnChangeOrderStatusDecline.setText("Accept Order");
                binding.btnDecline.setText("Decline");
                binding.lytOtherAccept.setVisibility(View.GONE);
                binding.lytAcceptBtn.setVisibility(View.VISIBLE);
                binding.lytPrintConfirmBtn.setVisibility(View.GONE);
            }
            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("progress")) {
                binding.btnChangeOrderStatus.setText("Complete Order");
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.VISIBLE);
                binding.lytAcceptBtn.setVisibility(View.GONE);
                binding.lytPrintConfirmBtn.setVisibility(View.GONE);
                binding.spDrivers.setVisibility(View.GONE);
            }
            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("completed")) {
               /* binding.btnChangeOrderStatus.setText("Deliver Order");
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.VISIBLE);
                binding.lytAcceptBtn.setVisibility(View.GONE);
                binding.lytPrintConfirmBtn.setVisibility(View.GONE);*/
                // binding.spDrivers.setVisibility(View.GONE);
                //binding.spDrivers.setVisibility(View.VISIBLE);
                binding.btnChangeOrderStatus.setVisibility(View.GONE);
                binding.spDrivers.setVisibility(View.GONE);
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.GONE);
                binding.lytAcceptBtn.setVisibility(View.GONE);
                binding.spDrivers.setVisibility(View.GONE);
                binding.dividerDriver.setVisibility(View.GONE);
                binding.lytPrintConfirmBtn.setVisibility(View.GONE);


            }
            if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("delivered")) {
                // binding.btnChangeOrderStatus.setText("Deliver Order");
                binding.btnResend.setVisibility(View.VISIBLE);
                binding.btnChangeOrderStatus.setVisibility(View.GONE);
                binding.spDrivers.setVisibility(View.GONE);
                binding.btnDecline.setVisibility(View.GONE);
                binding.lytOtherAccept.setVisibility(View.GONE);
                binding.lytAcceptBtn.setVisibility(View.GONE);
                binding.spDrivers.setVisibility(View.GONE);
                binding.dividerDriver.setVisibility(View.GONE);
                binding.lytPrintConfirmBtn.setVisibility(View.GONE);
            }


            binding.btnChangeOrderStatusConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int status = 0;

                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("action")) {

                        status = 2;
                    }

                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("pick")) {

                        status = 1;
                    }
                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("collected")) {

                        status = 3;
                    }
                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("progress")) {

                        status = 4;
                    }
                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("out_for_delivery")) {

                        status = 5;
                    }

                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("completed")) {

                        status = 6;
                    }


                    if (status != 0) {
                        if (driverId == null || driverId == "" || driverId == "0") {
                            if (status == 2 || status == 5) {
                                new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Select Driver")
                                        .setContentText("Select Driver")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {

                                                sDialog.dismissWithAnimation();


                                            }
                                        })
                                        .show();
                            } else {

                                dialog.show();


                                RequestParams params = new RequestParams();

                                params.put("email", appPrefes.getData(PrefConstants.email));
                                params.put("status", status);
                                params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
                                params.put("id", driverId);

                                String url = ApiConstants.BaseUrl + ApiConstants.orderUpdateUrl;
                                //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                // requestParams.put("s", queryTerm);
                                asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        dialog.dismiss();

                                        ///  String object= new String(response);
                                        //  JSONObject jsonObject = new JSONObject(object);

                                        try {
                                            String object = new String(response.toString());
                                            JSONObject jsonObject = new JSONObject(object);
                                            String result = jsonObject.getString("status");


                                            if (result.equals("true")) {
                                                //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                                                new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                                        .setTitleText("Success")
                                                        .setContentText("Updated")
                                                        .setConfirmText("Ok")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {


                                                                sDialog.dismissWithAnimation();
                                                                AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                                                AlmoskyAdmin.getInst().setSelectedOrder(null);
                                                                AlmoskyAdmin.getInst().setIronList(null);
                                                                AlmoskyAdmin.getInst().setWashList(null);
                                                                AlmoskyAdmin.getInst().setDrycleanList(null);
                                                                AlmoskyAdmin.getInst().setCurentOrderId(null);
                                                                AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                                                AlmoskyAdmin.getInst().setCurrentOrders(null);

                                                                PickupOrdersFragments fg = new PickupOrdersFragments();
                                                                fg.i = 0;


                                                                Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(go);


                                                            }
                                                        })
                                                        .show();
                                                // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                                            }
                                        } catch (Exception e) {
                                            dialog.dismiss();
                                            PickupOrdersFragments fg = new PickupOrdersFragments();
                                            fg.i = 0;

                                        }
                                    }
                                });

                            }
                        } else {

                            dialog.show();


                            RequestParams params = new RequestParams();

                            params.put("email", appPrefes.getData(PrefConstants.email));
                            params.put("status", status);
                            params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
                            params.put("id", driverId);

                            String url = ApiConstants.BaseUrl + ApiConstants.orderUpdateUrl;
                            //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
                            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                            // requestParams.put("s", queryTerm);
                            asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    dialog.dismiss();

                                    ///  String object= new String(response);
                                    //  JSONObject jsonObject = new JSONObject(object);

                                    try {
                                        String object = new String(response.toString());
                                        JSONObject jsonObject = new JSONObject(object);
                                        String result = jsonObject.getString("status");


                                        if (result.equals("true")) {
                                            //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                                            new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                                    .setTitleText("Success")
                                                    .setContentText("Updated")
                                                    .setConfirmText("Ok")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {


                                                            sDialog.dismissWithAnimation();
                                                            AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                                            AlmoskyAdmin.getInst().setSelectedOrder(null);
                                                            AlmoskyAdmin.getInst().setIronList(null);
                                                            AlmoskyAdmin.getInst().setWashList(null);
                                                            AlmoskyAdmin.getInst().setDrycleanList(null);
                                                            AlmoskyAdmin.getInst().setCurentOrderId(null);
                                                            AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                                            AlmoskyAdmin.getInst().setCurrentOrders(null);

                                                            PickupOrdersFragments fg = new PickupOrdersFragments();
                                                            fg.i = 0;


                                                            Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                                            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(go);


                                                        }
                                                    })
                                                    .show();
                                            // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        dialog.dismiss();
                                        PickupOrdersFragments fg = new PickupOrdersFragments();
                                        fg.i = 0;

                                    }
                                }
                            });

                        }
                    }

                }

            });


            binding.btnChangeOrderStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int status = 0;

                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("action")) {

                        status = 2;
                    }

                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("pick")) {

                        status = 1;
                    }
                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("collected")) {

                        status = 3;
                    }
                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("progress")) {

                        status = 4;
                    }
                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("out_for_delivery")) {

                        status = 5;
                    }

                    if (AlmoskyAdmin.getInst().getSelectedOrderType().equals("completed")) {

                        status = 6;
                    }


                    if (status != 0) {

                        if (driverId == null || driverId == "" || driverId == "0") {
                            if (status == 2 || status == 5) {
                                new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Select Driver")
                                        .setContentText("Select Driver")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {

                                                sDialog.dismissWithAnimation();


                                            }
                                        })
                                        .show();
                            } else {

                                dialog.show();


                                RequestParams params = new RequestParams();

                                params.put("email", appPrefes.getData(PrefConstants.email));
                                params.put("status", status);
                                params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
                                params.put("id", driverId);

                                String url = ApiConstants.BaseUrl + ApiConstants.orderUpdateUrl;
                                //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                // requestParams.put("s", queryTerm);
                                asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        dialog.dismiss();

                                        ///  String object= new String(response);
                                        //  JSONObject jsonObject = new JSONObject(object);

                                        try {
                                            String object = new String(response.toString());
                                            JSONObject jsonObject = new JSONObject(object);
                                            String result = jsonObject.getString("status");


                                            if (result.equals("true")) {

                                                //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                                                new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                                        .setTitleText("Success")
                                                        .setContentText("Updated")
                                                        .setConfirmText("Ok")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {


                                                                sDialog.dismissWithAnimation();
                                                                AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                                                AlmoskyAdmin.getInst().setSelectedOrder(null);
                                                                AlmoskyAdmin.getInst().setIronList(null);
                                                                AlmoskyAdmin.getInst().setWashList(null);
                                                                AlmoskyAdmin.getInst().setDrycleanList(null);
                                                                AlmoskyAdmin.getInst().setCurentOrderId(null);
                                                                AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                                                AlmoskyAdmin.getInst().setCurrentOrders(null);

                                                                PickupOrdersFragments fg = new PickupOrdersFragments();
                                                                fg.i = 0;


                                                                Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(go);


                                                            }
                                                        })
                                                        .show();
                                                // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                                            }
                                        } catch (Exception e) {
                                            dialog.dismiss();
                                            PickupOrdersFragments fg = new PickupOrdersFragments();
                                            fg.i = 0;

                                        }
                                    }
                                });

                            }

                        } else {

                            dialog.show();


                            RequestParams params = new RequestParams();

                            params.put("email", appPrefes.getData(PrefConstants.email));
                            params.put("status", status);
                            params.put("orderId", AlmoskyAdmin.getInst().getSelecterOrderId());
                            params.put("id", driverId);

                            String url = ApiConstants.BaseUrl + ApiConstants.orderUpdateUrl;
                            //apiCalls.callApiPost(tabHostActivity, params, dialog, url, 2);
                            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                            // requestParams.put("s", queryTerm);
                            asyncHttpClient.post(url, params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    dialog.dismiss();

                                    ///  String object= new String(response);
                                    //  JSONObject jsonObject = new JSONObject(object);

                                    try {
                                        String object = new String(response.toString());
                                        JSONObject jsonObject = new JSONObject(object);
                                        String result = jsonObject.getString("status");


                                        if (result.equals("true")) {

                                            //  Toast.makeText(OrderDetailsActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                                            new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                                    .setTitleText("Success")
                                                    .setContentText("Updated")
                                                    .setConfirmText("Ok")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {


                                                            sDialog.dismissWithAnimation();
                                                            AlmoskyAdmin.getInst().setSelectedOrderType(null);
                                                            AlmoskyAdmin.getInst().setSelectedOrder(null);
                                                            AlmoskyAdmin.getInst().setIronList(null);
                                                            AlmoskyAdmin.getInst().setWashList(null);
                                                            AlmoskyAdmin.getInst().setDrycleanList(null);
                                                            AlmoskyAdmin.getInst().setCurentOrderId(null);
                                                            AlmoskyAdmin.getInst().setSelecterOrderId(0);
                                                            AlmoskyAdmin.getInst().setCurrentOrders(null);

                                                            PickupOrdersFragments fg = new PickupOrdersFragments();
                                                            fg.i = 0;


                                                            Intent go = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                                            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(go);


                                                        }
                                                    })
                                                    .show();
                                            // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        dialog.dismiss();
                                        PickupOrdersFragments fg = new PickupOrdersFragments();
                                        fg.i = 0;

                                    }
                                }
                            });

                        }
                    }

                }

            });
        }


    @Override
    public void connectingWithPrinter() {

    }

    @Override
    public void connectionFailed(@NotNull String s) {

    }

    @Override
    public void onError(@NotNull String s) {

    }

    @Override
    public void onMessage(@NotNull String s) {

    }

    @Override
    public void printingOrderSentSuccessfully() {

    }
}
