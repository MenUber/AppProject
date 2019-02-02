package com.sw.menuber.presentation.dailydishes.maincourse;

public class DailyMainCoursePresenter implements DailyMainCourseContract.Presenter {

    private final DailyMainCourseContract.View mView;

    public DailyMainCoursePresenter(DailyMainCourseContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
