package com.sw.menuber.presentation.dailydishes.starter;

public class DailyStarterPresenter implements DailyStarterContract.Presenter {

    private final DailyStarterContract.View mView;

    public DailyStarterPresenter(DailyStarterContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
