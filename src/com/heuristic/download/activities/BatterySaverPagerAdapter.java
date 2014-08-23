package com.heuristic.download.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

//Since this is an object collection, use a FragmentStatePagerAdapter,
//and NOT a FragmentPagerAdapter.
public class BatterySaverPagerAdapter extends FragmentStatePagerAdapter {
	public BatterySaverPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		
		Fragment fragment = null;
		
		switch(i){
		case 0:
			fragment = new TaskListFragment();
			break;
		case 1:
			fragment = new StatisticsFragment();
			break;
		default:
			fragment = new AboutFragment();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch(position){
		case 0:
			return "Task List";
		case 1:
			return "Statistics";
		default:
			return "About us";
		}
	}
}