package admin.com.almoskyadmin.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderLayout;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;
import admin.com.almoskyadmin.adapter.SimpleFragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragments extends Fragment implements TabLayout.OnTabSelectedListener {


    private TabLayout tabLayout;
    private SliderLayout sliderLayout;
    private FragmentPagerAdapter adapter;

    HomeActivity _activity;

    public OrderFragments() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();


        //tabLayout.getTabAt(1).select();
//        tabLayout.getTabAt(0).select();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_order, container, false);
      /*  tabLayout = view.findViewById(R.id.tabs);
        ViewPager pager = view. findViewById(R.id.pager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(this.getChildFragmentManager());


       // Pager pag=new Pager(getFragmentManager(),2);
       // adapter = new TabsPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
*/


        //  tabLayout.setSelected(true);

     //   pager.setCurrentItem(3);
//        pager.setOffscreenPageLimit(1);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager =view.findViewById(R.id.pager);

        if (viewPager != null) {
            // before screen rotation it's better to detach pagerAdapter from the ViewPager, so
            // pagerAdapter can remove all old fragments, so they're not reused after rotation.
            viewPager.setAdapter(null);
        }
        super.onSaveInstanceState(savedInstanceState);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getActivity(), getChildFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(viewPager.getCurrentItem());
        viewPager.setOffscreenPageLimit(1);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



        return view;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        Toast.makeText(getContext(), "position"+tab.getPosition(), Toast.LENGTH_SHORT).show(); tab.getText();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
