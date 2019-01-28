package com.capsuladigital.androidcore.presentation.restore_password;

/**
 * Created by Luciano on 5/04/2018.
 */

public interface RestorePasswordContract {
    interface View{
        void showConnectionError();
        void launchLoginActivity();
        void showPassRestoreSuccess();
        void showPassRestoreFailure();
        void showLoadingDialog();
        void hideLoadingDialog();

    }
    interface Presenter{
        void onViewDettach();
        void onViewAttach(RestorePasswordContract.View view);
        void updatePassword(String password, String code);
    }
}
