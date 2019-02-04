package com.sw.menuber.presentation.maindishes;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sw.menuber.presentation.maindishes.dessert.DessertFragment;
import com.sw.menuber.presentation.maindishes.drink.DrinkFragment;
import com.sw.menuber.presentation.maindishes.maincourse.MainCourseFragment;
import com.sw.menuber.presentation.maindishes.starter.StarterFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static int NUMBER_OF_TABS = 4;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new StarterFragment();

            case 1:
                return new MainCourseFragment();

            case 2:
                return new DrinkFragment();

            case 3:
                return new DessertFragment();

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Entradas";
            case 1:
                return "Segundos";
            case 2:
                return "Bebidas";
            case 3:
                return "Postres";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.NUMBER_OF_TABS;
    }
}
