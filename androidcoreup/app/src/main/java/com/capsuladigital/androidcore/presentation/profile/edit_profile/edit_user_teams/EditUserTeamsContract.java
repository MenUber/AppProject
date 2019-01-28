package com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_teams;

import com.capsuladigital.androidcore.data.model.team.Team;

import java.util.List;

/**
 * Created by Luciano on 14/05/2018.
 */

public interface EditUserTeamsContract {
    interface View{
        void showSuccessToast();
        void showLoadingDialog();
        void hideLoadingDialog();
        void showWSError();
        void showConnectionError();
        void launchProfile();
        void setTeams(List<Team> teams);
    }
    interface Presenter{
        void onViewDettach();
        void onViewAttach(EditUserTeamsContract.View view);
        void  editFavTeams( List<Team> teams);
        void getAllTeams();

    }
}
