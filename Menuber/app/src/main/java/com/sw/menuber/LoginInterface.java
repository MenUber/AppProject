package com.sw.menuber;

public interface LoginInterface {

    interface View{

        void setRequiredEmail();
        void setRequiredPass();
        void navigateToHome();

    }

    interface Presenter{

        void validateUserPresenter(String user, String pass);
    }

    interface Model{
        void validateUserModel( String user, String pass,loginListener listener);
    }

    interface loginListener{

        void setRequiredEmail();
        void setRequiredPass();
        void successOperation();
    }



}
