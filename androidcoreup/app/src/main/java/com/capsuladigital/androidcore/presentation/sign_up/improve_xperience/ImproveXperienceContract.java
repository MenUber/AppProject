package com.capsuladigital.androidcore.presentation.sign_up.improve_xperience;

import com.capsuladigital.androidcore.data.model.person.PersonSignUpPost;
import com.capsuladigital.androidcore.data.model.team.Team;

import java.util.List;

/**
 * Created by Luciano on 5/04/2018.
 */

public interface ImproveXperienceContract {
    interface View {
        void showSuccessToast();
        void showWSError();
        void showConnectionError();
        void setTeams(List<Team> teams);
        void showAlreadyRegisteredUser();
        void showLoadingDialog();
        void hideLoadingDialog();
    }

    interface Presenter {
        void onViewDettach();
        void onViewAttach(ImproveXperienceContract.View view);
        void signUp(PersonSignUpPost person);
        void getAllTeams();
    }
}
