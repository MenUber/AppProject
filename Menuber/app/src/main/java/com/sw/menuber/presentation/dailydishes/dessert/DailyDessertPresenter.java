package com.sw.menuber.presentation.dailydishes.dessert;

public class DailyDessertPresenter implements DailyDessertContract.Presenter{

    private final DailyDessertContract.View mView;

    public DailyDessertPresenter(DailyDessertContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
