package com.sw.menuber.presentation.dailydishes;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sw.menuber.presentation.dailydishes.dessert.DailyDessertFragment;
import com.sw.menuber.presentation.dailydishes.drink.DailyDrinkFragment;
import com.sw.menuber.presentation.dailydishes.maincourse.DailyMainCourseFragment;
import com.sw.menuber.presentation.dailydishes.starter.DailyStarterFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_TABS = 4;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DailyStarterFragment();
            case 1:
                return new DailyMainCourseFragment();
            case 2:
                return new DailyDrinkFragment();
            case 3:
                return new DailyDessertFragment();
            default:
                 return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Entradas";
            case 1: return "Segundos";
            case 2: return "Bebidas";
            case 3: return "Postres";
            default: return null;
        }
    }
}
