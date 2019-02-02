package com.sw.menuber.presentation.dailydishes.starter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sw.menuber.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyStarterFragment extends Fragment implements DailyStarterContract.View{

    private DailyStarterContract.Presenter mPresenter;

    public DailyStarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_starter, container, false);
    }

    @Override
    public void setPresenter(DailyStarterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showErrorMessage(String msg) {

    }
}
