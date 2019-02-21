package admin.com.almoskyadmin.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import admin.com.almoskyadmin.fragments.ActionOrdersFragments;
import admin.com.almoskyadmin.fragments.CompletedOrdersFragments;
import admin.com.almoskyadmin.fragments.InProgressOrdersFragments;
import admin.com.almoskyadmin.fragments.PickupOrdersFragments;

public class Pager extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public Pager(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                ActionOrdersFragments home = new ActionOrdersFragments();
                return home;
            case 1:
                InProgressOrdersFragments about = new InProgressOrdersFragments();
                return about;

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0)
            return "Actions";
      //  else if (position == 1)
        //    return "Pickup &Delivery ";
        //else if (position == 1)
       //     return "In Progress";
        else
            return "In Progress";

    }
}