package com.capsuladigital.androidcore.presentation.home.league.standings;

import android.content.Context;
import android.support.annotation.Nullable;

public class StandingsPresenter implements StandingsContract.Presenter {
    private Context context;
    private StandingsContract.View view;

    public StandingsPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onViewDettach() {
        this.view = null;
    }

    @Override
    public void onViewAttach(StandingsContract.View view) {
        this.view = view;
    }

    @Nullable
    private StandingsContract.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    @Override
    public void getStandings(int id) {

    }

}
