package com.capsuladigital.androidcore.presentation.sign_up.sign_up_form;

import com.capsuladigital.androidcore.data.model.person.PersonSignUpForm;

/**
 * Created by Luciano on 5/04/2018.
 */

public interface SignUpFormContract {
    interface View {
        void showLoadingDialog();
        void hideLoadingDialog();
        void showEmailAlreadyRegisteredDialog();
        void showWSError();
        void showConnectionError();
        void launchImproveXperienceActivity();

    }

    interface Presenter {
        void onViewDettach();
        void onViewAttach(SignUpFormContract.View view);
        void storeSignUpInfo(PersonSignUpForm person);
    }
}
