package admin.com.almoskyadmin.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.adapter.RemarksAdapter;
import admin.com.almoskyadmin.adapter.SimpleArrayListAdapter;
import admin.com.almoskyadmin.adapter.SimpleListAdapter;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.model.AddDiscountResponse;
import admin.com.almoskyadmin.model.AddRemarkResponse;
import admin.com.almoskyadmin.model.Customer;
import admin.com.almoskyadmin.model.CustomerListdto;
import admin.com.almoskyadmin.model.RemarkList;
import admin.com.almoskyadmin.utils.AppPrefes;
import admin.com.almoskyadmin.utils.Utility;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;
import admin.com.almoskyadmin.utils.constants.Constants;
import admin.com.almoskyadmin.utils.constants.PrefConstants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class DiscountActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    private RecyclerView remarksRecycler;
    private Button fromDate, toDate,btnSubmit;
    private EditText edtDiscount;
    private CheckBox checkBox;
    private SearchableSpinner mSearchableSpinner;
    private SimpleListAdapter mSimpleListAdapter;
    private SimpleArrayListAdapter mSimpleArrayListAdapter;
    private final ArrayList<String> mStrings = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    View mSelectedDateView;
    String  mSelectedFromDate = "",mSelectedTomDate = "",strDiscount = "";
    int selectedCustomerId = -1;
    private Float discount;
    private boolean mIsPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        remarksRecycler = findViewById(R.id.remarksRecycler);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtDiscount = findViewById(R.id.edtDiscount);
        checkBox = findViewById(R.id.checkbox);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);


        mSearchableSpinner = (SearchableSpinner) findViewById(R.id.SearchableSpinner);

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDateView = fromDate;
                showDatePicker();
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedDateView = toDate;
                showDatePicker();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields())
                    postDiscount();
            }
        });
        if (Utility.isNetworkOnline(DiscountActivity.this)) {
            getCustomerList();
        } else {

            new SweetAlertDialog(DiscountActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Check Internet Connection")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                        }
                    }).show();


        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                            mIsPercentage = isChecked;


                                                        }
                                                    }
        );
    }
    public boolean validateFields() {
        calculateDiscount();
        if (selectedCustomerId < 0) {
            Toast.makeText(getApplicationContext(), "Select Customer !", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (mSelectedFromDate.equals("") || mSelectedFromDate.equals(null)) {
            Toast.makeText(getApplicationContext(), "Select From Date !", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (mSelectedTomDate.equals("") || mSelectedTomDate.equals(null)) {
            Toast.makeText(getApplicationContext(), "Select To Date !", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (edtDiscount.getText().toString().equals("") || edtDiscount.getText().toString().equals(null)) {

            Toast.makeText(getApplicationContext(), "Enter Discount", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;

    }
    float calculateDiscount() {



            strDiscount = String.format("%.2f", Float.valueOf((getString(edtDiscount).isEmpty() ? "0" : getString(edtDiscount))));

        if (!mIsPercentage) {
            discount = Float.parseFloat(strDiscount);


        } else {
            discount = Float.parseFloat(strDiscount);
            discount = (1 * discount) / 100;


        }

        return discount;
    }

    private void getCustomerList() {
        Log.d("Success-", "JSON:" + "Inside CustomerList");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));

        String url = ApiConstants.customerList;
        apiCalls.callApiPost(DiscountActivity.this, params, dialog, url, 1);
    }

    private void postDiscount() {

        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("customerId", selectedCustomerId);
        params.put("discount", strDiscount);
        params.put("expfromdate", mSelectedFromDate);
        params.put("exptodate", mSelectedTomDate);
        String url = ApiConstants.postCustomerDiscount;
        Log.d("Success-", "JSON:" + "Inside postDiscount params "+params.toString());
        apiCalls.callApiPost(DiscountActivity.this, params, dialog, url, 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mSearchableSpinner.isInsideSearchEditText(event)) {
            mSearchableSpinner.hideEdit();
        }

        return super.onTouchEvent(event);
    }

    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            selectedCustomerId = customerList.get(position-1).getID();
            // Toast.makeText(MainActivity.this, "Item on position " + position + " : " + mSimpleListAdapter.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected() {
            // Toast.makeText(MainActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            selectedCustomerId = -1;
        }
    };

    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        if (requestId == 1) {
            try {
                final CustomerListdto userData;
                Gson gson = new Gson();
                userData = gson.fromJson(response, CustomerListdto.class);
                customerList = userData.getResult();
                for (Customer customer : userData.getResult())
                    mStrings.add(customer.getFullName());
                try {
                    mSimpleListAdapter = new SimpleListAdapter(this, userData.getResult());
                    mSimpleArrayListAdapter = new SimpleArrayListAdapter(this, userData.getResult(), mStrings);
                    mSearchableSpinner.setAdapter(mSimpleListAdapter);
                    mSearchableSpinner.setOnItemSelectedListener(mOnItemSelectedListener);


                } catch (Exception e) {

                    new SweetAlertDialog(DiscountActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestId == 2) {
            try {
                final AddDiscountResponse userData;
                Gson gson = new Gson();

                userData = gson.fromJson(response, AddDiscountResponse.class);
                try {

                    if (userData.getResult().equals("Discount Updated")) {
                      finish();
                        new SweetAlertDialog(DiscountActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Success")
                                .setContentText(userData.getResult())
                                .show();
                    }

                } catch (Exception e) {

                    new SweetAlertDialog(DiscountActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(DiscountActivity.this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      String  mSelectedDate = year + "-" + getProperDate(++month) + "-" + getProperDate(dayOfMonth);
        switch (mSelectedDateView.getId()) {

            case R.id.fromDate:
                fromDate.setText(mSelectedDate);
                mSelectedFromDate = mSelectedDate;
                //  sortedItem( getString(toDate));
                break;
            case R.id.toDate:
                toDate.setText(mSelectedDate);
                mSelectedTomDate = mSelectedDate;
                //  sortedItem( getString(toDate));
                break;
        }
    }

    private String getProperDate(int dayOrMonth) {
        if (dayOrMonth < 10) {
            return "0" + dayOrMonth;
        }
        return String.valueOf(dayOrMonth);
    }
}
