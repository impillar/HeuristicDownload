package com.heuristic.download;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.heuristic.download.activities.BatterySaverPagerAdapter;
import com.heuristic.download.util.Initiator;

public class BatterySaverLibraryActivity extends FragmentActivity {

	BatterySaverPagerAdapter pagerAdapter;
	ViewPager mViewPager;
	String[] tabTitles = {"Task List", "Statistics", "About us"};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_service_library);
        
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        pagerAdapter = new BatterySaverPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
        };
        
        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < tabTitles.length; i++) {
            actionBar.addTab(actionBar.newTab().setText(tabTitles[i]).setTabListener(tabListener));
        }
        
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        
        //Do some initial work for the application
        Initiator.v(this).theCreation();
        
    }
	
	
}

