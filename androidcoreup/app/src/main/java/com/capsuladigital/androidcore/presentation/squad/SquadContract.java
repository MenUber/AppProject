package com.capsuladigital.androidcore.presentation.squad;

import com.capsuladigital.androidcore.data.model.team.TeamPlayer;

import java.util.List;

/**
 * Created by Luciano on 19/12/2018.
 */

public class SquadContract {
    interface View {
        void setKeepers(List<TeamPlayer> keepers);
        void setDefenders(List<TeamPlayer> defenders);
        void setMidfielders(List<TeamPlayer> midfielders);
        void setAttackers(List<TeamPlayer> attackers);
        void showWSError();
        void hideWSError();
        void showConnectionError();
        void showRecycler();
        void hideRecycler();
        void stopRefresh();
    }

    interface Presenter {
        void getTeamSquad(int id);
        void onViewDettach();
        void onViewAttach(SquadContract.View view);
    }
}

