package com.capsuladigital.androidcore.presentation.login;

/**
 * Created by Luciano on 5/04/2018.
 */

public interface LoginContract {
    interface View{
        void showSuccessToast(String name);
        void showLoadingDialog();
        void hideLoadingDialog();
        void showWrongCredentialsToast();
        void showEmailConfirmError();
        void showSocialMediaWSError();
        void showSocialMediaLoginAttemptError();
        void showConnectionError();
        void showPassRestoreDialogSucess();
        void showPassRestoreDialogFailure();
        void showPassRestoreEmailNotConfirmed();
        void launchHome();
        void launchSignUp();
        void launchRestorePassword();
        void subscribeToNotifications();
    }
    interface Presenter{
        void onViewDettach();
        void onViewAttach(LoginContract.View view);
        void login(String email, String password);
        void sendPassUpdateAttempt( String email);
        void socialMedia(String email, String name, String lastName, String photo, String birthday);
    }
}
