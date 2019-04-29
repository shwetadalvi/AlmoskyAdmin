package admin.com.almoskyadmin.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

import admin.com.almoskyadmin.AlmoskyAdmin;
import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.databinding.ActivityHomeBinding;
import admin.com.almoskyadmin.fragments.CollectedFragments;
import admin.com.almoskyadmin.fragments.OrderFragments;
import admin.com.almoskyadmin.fragments.OutForDeliveryFragments;
import admin.com.almoskyadmin.fragments.Settingsfragments;



public class HomeActivity extends BaseActivity {

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    ActivityHomeBinding binding;
    private Fragment fragment;
    private FragmentResultInterface listener;
    private FragmentActionResultInterface actionlistener;
    private FragmentPickupResultInterface pickuplistener;
    private FragmentInProgressResultInterface progresslistener;
    private FragmentCompletedResultInterface completedlistener;
    private FragmentDeliveredResultInterface deliveredlistener;
    private FragmentCollectedResultInterface collectedlistener;
    private FragmentOutForDeliveryResultInterface outfordeliverylistener;
    private FragmentCancelledResultInterface cancelledlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        init();
    }

    public void updateOrder(int id,String type){

        Intent go=new Intent(HomeActivity.this, OrderDetailsActivity.class);
//        go.putExtra("id",id);
        AlmoskyAdmin.getInst().setSelecterOrderId(id);
        AlmoskyAdmin.getInst().setSelectedOrderType(type);
        startActivity(go);

    }

    private void init() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Orders", R.drawable.ic_orders);
     //   AHBottomNavigationItem item3 = new AHBottomNavigationItem("Report", R.drawable.ic_orders);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Settings", R.drawable.ic_settings);

        binding.bottomNavigation.setBehaviorTranslationEnabled(false);

        bottomNavigationItems.add(item1);
      //  bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item2);

        binding.bottomNavigation.addItems(bottomNavigationItems);

        binding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        binding.bottomNavigation.setTranslucentNavigationEnabled(true);
        binding.bottomNavigation.setBackgroundResource(R.drawable.tab_item_selector);
        binding.bottomNavigation.setBackground(getResources().getDrawable(R.drawable.tab_item_selector));
        binding.bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        fragment = new OrderFragments();
        replaceFragment(R.id.container, fragment);
        listeners();
    }

    private void listeners() {
        binding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        fragment = new OrderFragments();
                        break;
                    case 1:
                        fragment = new Settingsfragments();
                        break;
                    case 2:
                       // fragment = new Settingsfragments();
                        break;

                }
                replaceFragment(R.id.container, fragment);
                return true;
            }
        });
    }

    public void replaceFragment(int fragment_container, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragment_container, fragment);
        transaction.commitAllowingStateLoss();

    }

    public interface FragmentResultInterface{
        void fragmentResultInterface(String response, int requestId);
    }
    public void setListener(FragmentResultInterface fragmentResultInterface){
        this.listener = fragmentResultInterface;
    }

    public interface FragmentActionResultInterface{
        void fragmentActionResultInterface(String response, int requestId);
    }
    public void setActionListener(FragmentActionResultInterface fragmentActionResultInterface){
        this.actionlistener = fragmentActionResultInterface;
    }


    public interface FragmentPickupResultInterface{
        void fragmentPickupResultInterface(String response, int requestId);
    }
    public void setPickupListener(FragmentPickupResultInterface fragmentPickupResultInterface){
        this.pickuplistener = fragmentPickupResultInterface;
    }

    public interface FragmentInProgressResultInterface{
        void fragmentinprogressResultInterface(String response, int requestId);
    }
    public void setProgressListener(FragmentInProgressResultInterface fragmentinprogressResultInterface){
        this.progresslistener = fragmentinprogressResultInterface;
    }

    public interface FragmentCompletedResultInterface{
        void fragmentcompletedResultInterface(String response, int requestId);
    }
    public void setCompletedListener(FragmentCompletedResultInterface fragmentcompletedResultInterface){
        this.completedlistener = fragmentcompletedResultInterface;
    }

    public interface FragmentDeliveredResultInterface{
        void fragmentdeliveredResultInterface(String response, int requestId);
    }
    public interface FragmentCancelledResultInterface{
        void fragmentCancelledResultInterface(String response, int requestId);
    }
    public void setDeliveredListener(FragmentDeliveredResultInterface fragmentdeliveredResultInterface){
        this.deliveredlistener = fragmentdeliveredResultInterface;
    }

    public interface FragmentCollectedResultInterface{
        void fragmentcollectedResultInterface(String response, int requestId);
    }
    public void setCollectedListener(FragmentCollectedResultInterface fragmentCollectedResultInterface){
        this.collectedlistener = fragmentCollectedResultInterface;
    }
    public interface FragmentOutForDeliveryResultInterface{
        void fragmentoutofdeliveryResultInterface(String response, int requestId);
    }
    public void setOutOfDeliveryListener(FragmentOutForDeliveryResultInterface fragmentOutForDeliveryResultInterface){
        this.outfordeliverylistener = fragmentOutForDeliveryResultInterface;
    }
    public void setCancelledlistener(FragmentCancelledResultInterface fragmentCancelledResultInterface){
        this.cancelledlistener = fragmentCancelledResultInterface;
    }

    @Override
    public void getResponse(String response, int requestId) {

        if(requestId==1){
            actionlistener.fragmentActionResultInterface(response,requestId);
        }
        else if(requestId==2){
            pickuplistener.fragmentPickupResultInterface(response,requestId);
        }
        else if(requestId==3){
            progresslistener.fragmentinprogressResultInterface(response,requestId);
        }
        else if(requestId==4){
            completedlistener.fragmentcompletedResultInterface(response,requestId);
        }
        else if(requestId==5) {
            deliveredlistener.fragmentdeliveredResultInterface(response, requestId);
        }
  else if(requestId==6) {
            collectedlistener.fragmentcollectedResultInterface(response, requestId);
        }  else if(requestId==7) {
            outfordeliverylistener.fragmentoutofdeliveryResultInterface(response, requestId);
        }else if(requestId==8) {
            cancelledlistener.fragmentCancelledResultInterface(response, requestId);
        }

    }
}
