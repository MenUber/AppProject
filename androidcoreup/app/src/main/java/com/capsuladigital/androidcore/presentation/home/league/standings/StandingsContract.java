package com.capsuladigital.androidcore.presentation.home.league.standings;


public class StandingsContract {
    interface View {
        void showContent();
        void hideContent();
        void stopRefresh();
        void hideWSError();
        void showWSError();
        void showConnectionError();
    }

    interface Presenter {
        void getStandings(int id);
        void onViewDettach();
        void onViewAttach(StandingsContract.View view);
    }
}
