package com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_data;

import com.capsuladigital.androidcore.data.model.person.PersonEdit;

/**
 * Created by Luciano on 14/05/2018.
 */

public interface EditUserDataContract {
    interface View{
        void showSuccessToast();
        void showLoadingDialog();
        void hideLoadingDialog();
        void showWSError();
        void showConnectionError();
        void launchProfile();
    }
    interface Presenter{
        void onViewDettach();
        void onViewAttach(EditUserDataContract.View view);
        void editPerson(PersonEdit person, String birthdate);

    }
}
