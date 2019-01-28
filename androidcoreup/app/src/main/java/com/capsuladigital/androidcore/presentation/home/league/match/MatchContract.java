package com.capsuladigital.androidcore.presentation.home.league.match;

import com.capsuladigital.androidcore.data.model.match.Match;
import com.capsuladigital.androidcore.data.model.team.Team;

import java.util.List;

public class MatchContract {
    interface View {
        void setMatches(List<Match> matches);
        void setTeamsArray(List<Team> arrayList);
        void showContent();
        void hideContent();
        void stopRefresh();
        void hideWSError();
        void showWSError();
        void showConnectionError();
    }
    interface Presenter {
        void getMatches(int idLeague);
        void getTeams();
        void onViewDettach();
        void onViewAttach(MatchContract.View view);
    }
}
