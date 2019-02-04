package com.sw.menuber.presentation.dailydishes.maincourse;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sw.menuber.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyMainCourseFragment extends Fragment implements DailyMainCourseContract.View{

    private DailyMainCourseContract.Presenter mPresenter;

    public DailyMainCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_main_course, container, false);
    }

    @Override
    public void setPresenter(DailyMainCourseContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showErrorMessage(String msg) {

    }
}
