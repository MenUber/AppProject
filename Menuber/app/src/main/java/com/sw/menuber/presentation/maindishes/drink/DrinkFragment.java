package com.sw.menuber.presentation.maindishes.drink;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sw.menuber.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkFragment extends Fragment implements DrinkContract.View{

    private DrinkContract.Presenter mPresenter;


    public DrinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink, container, false);
    }

    @Override
    public void setPresenter(DrinkContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showErrorMessage(String msg) {

    }
}
