package admin.com.almoskyadmin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import admin.com.almoskyadmin.fragments.ActionOrdersFragments;
import admin.com.almoskyadmin.fragments.CompletedOrdersFragments;
import admin.com.almoskyadmin.fragments.InProgressOrdersFragments;
import admin.com.almoskyadmin.fragments.PickupOrdersFragments;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.

     /*   if (position == 0)
           // return ActionOrdersFragments.newInstance(position + 1);
        else if (position == 1)
           // return PickupOrdersFragments.newInstance(position + 1);
        else if (position == 2)
            return InProgressOrdersFragments.newInstance(position + 1);

        else
            return CompletedOrdersFragments.newInstance(position + 1); */

      /*   switch (position){
            case 0: return  ActionOrdersFragments.newInstance(position);
            case 1: return  PickupOrdersFragments.newInstance(position);
            case 2: return  InProgressOrdersFragments.newInstance(position);
            case 3: return  CompletedOrdersFragments.newInstance(position);
        } */
    /*    if (position == 1){

            ActionOrdersFragments frag=new ActionOrdersFragments();
            return frag;
        }
            //return ActionOrdersFragments.newInstance(position);
        if (position == 2){
            PickupOrdersFragments frag1=new PickupOrdersFragments();
            return frag1;
        }
           // return PickupOrdersFragments.newInstance(position);
        else if (position == 3){
            InProgressOrdersFragments frag3=new InProgressOrdersFragments();
            return frag3;
        }
            //return  PickupOrdersFragments.newInstance(position);
        else {
            CompletedOrdersFragments frag4=new CompletedOrdersFragments();
            return  frag4;
        } */
           // return  InProgressOrdersFragments.newInstance(position);




      /*  Switch(position){

           case 0: return new ActionOrdersFragments();
            case 1: return new PickupOrdersFragments();
            case 2: return new InProgressOrdersFragments();
           */

       /*   if (position == 0)
                //return new ActionOrdersFragments();//.newInstance(position+1);
                // else if (position == 1)
                 return PriceListFragment.newInstance(position + 1);
            else if (position == 2)
                return new PickupOrdersFragments();//.newInstance(position+1);
            else if (position == 3)
                return new InProgressOrdersFragments();//.newInstance(position+1);
            else //if(position==4)
                return new CompletedOrdersFragments();//.newInstance(position+1); */
       // else
         ///    return null;


        return null;

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0)
            return "Actions";
        else if (position == 1)
            return "Pickup &Delivery ";
        else if (position == 2)
            return "In Progress";
        else
            return "Completed";

    }
}
