package com.sw.menuber.presentation.maindishes.drink;

public class DrinkPresenter implements DrinkContract.Presenter{

    private final DrinkContract.View mView;

    public DrinkPresenter(DrinkContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
