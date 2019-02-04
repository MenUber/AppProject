package com.sw.menuber.presentation.dailydishes.starter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sw.menuber.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyStarterFragment extends Fragment implements DailyStarterContract.View{

    private DailyStarterContract.Presenter mPresenter;
    RecyclerView recyclerStarter;
    DailyStarterAdapter starterAdapter;

    public DailyStarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_starter, container, false);

        starterAdapter = new DailyStarterAdapter();

        recyclerStarter = view.findViewById(R.id.rvDailyStarter);
        recyclerStarter.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerStarter.setAdapter(starterAdapter);

        return view;
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
