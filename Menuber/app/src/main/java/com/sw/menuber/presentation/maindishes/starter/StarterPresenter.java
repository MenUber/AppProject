package com.sw.menuber.presentation.maindishes.starter;

public class StarterPresenter implements StarterContract.Presenter {

    private final StarterContract.View mView;

    public StarterPresenter(StarterContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
