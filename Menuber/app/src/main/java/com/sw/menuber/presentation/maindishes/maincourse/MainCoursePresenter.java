package com.sw.menuber.presentation.maindishes.maincourse;

public class MainCoursePresenter implements MainCourseContract.Presenter {

    private final MainCourseContract.View mView;

    public MainCoursePresenter(MainCourseContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
