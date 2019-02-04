package com.sw.menuber.presentation.dailydishes.drink;

public class DailyDrinkPresenter implements DailyDrinkContract.Presenter {

    private final DailyDrinkContract.View mView;

    public DailyDrinkPresenter(DailyDrinkContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
