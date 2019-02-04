package com.sw.menuber.presentation.maindishes.dessert;

public class DessertPresenter implements DessertContract.Presenter {

    private final DessertContract.View mView;

    public DessertPresenter(DessertContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }
}
