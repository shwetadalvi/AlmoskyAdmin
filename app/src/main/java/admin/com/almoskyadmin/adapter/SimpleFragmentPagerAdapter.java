package admin.com.almoskyadmin.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import admin.com.almoskyadmin.fragments.ActionOrdersFragments;
import admin.com.almoskyadmin.fragments.CancelledOrdersFragment;
import admin.com.almoskyadmin.fragments.CollectedFragments;
import admin.com.almoskyadmin.fragments.CompletedOrdersFragments;
import admin.com.almoskyadmin.fragments.DeliveredOrdersFragments;
import admin.com.almoskyadmin.fragments.InProgressOrdersFragments;
import admin.com.almoskyadmin.fragments.OutForDeliveryFragments;
import admin.com.almoskyadmin.fragments.PickupOrdersFragments;

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
       /* if (position == 0) {
            return new PickupOrdersFragments();
        } else if (position == 1){

            return new ActionOrdersFragments();

        } else if (position == 2){
            return new InProgressOrdersFragments();
        } else if (position == 3){
            return new CompletedOrdersFragments();
        }else {
            return new DeliveredOrdersFragments();
        }*/
        if (position == 0) {
            return new PickupOrdersFragments();
        } else if (position == 1){

            return new ActionOrdersFragments();

        } else if (position == 2){
            return new CollectedFragments();
        } else if (position == 3){
            return new InProgressOrdersFragments();
        }else if (position == 4){
            return new OutForDeliveryFragments();
        }else if (position == 5){
            return new DeliveredOrdersFragments();
        }else if (position == 6){
            return new CompletedOrdersFragments();
        }else
            return new CancelledOrdersFragment();
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 8;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
         /*   case 0:
                return "Pickup";
            case 1:
                return "Action";
            case 2:
                return "Progress";
            case 3:
                return "Completed";
            case 4:
                return "Delivered";
            default:
                return null;*/
            case 0:
                return "Action";
            case 1:
                return "To Be Collect";
            case 2:
                return "Collected";
            case 3:
                return "In Progress";
            case 4:
                return "Out For Delivery";
            case 5:
                return "To be Delivered";
            case 6:
                return "Completed";
            case 7:
                return "Cancelled";
            default:
                return null;
        }
    }

}