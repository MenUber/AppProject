package com.sw.menuber.presentation.dailydishes.drink;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sw.menuber.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyDrinkFragment extends Fragment implements DailyDrinkContract.View{

    private DailyDrinkContract.Presenter mPresenter;

    public DailyDrinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_drink, container, false);
    }

    @Override
    public void setPresenter(DailyDrinkContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showErrorMessage(String msg) {

    }
}
