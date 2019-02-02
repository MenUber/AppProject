package com.sw.menuber;

public class LoginPresenter implements LoginInterface.Presenter,LoginInterface.loginListener {

    private LoginInterface.View view;
    private LoginInterface.Model model;

    public LoginPresenter(LoginInterface.View view){
        this.view=view;
        model = new LoginModel();
    }
    @Override
    public void validateUserPresenter(String user, String pass) {
        model.validateUserModel(user,pass,this);
    }

/*    @Override
    public void setErrorEmail() {
        if(view!=null){
            view.setErrorEmail();
        }
    }

    @Override
    public void setErrorPass() {
        if(view!=null){
            view.setErrorPass();
        }
    }*/

    @Override
    public void setRequiredEmail() {
        if(view!=null){
            view.setRequiredEmail();
        }
    }

    @Override
    public void setRequiredPass() {
        if(view!=null){
            view.setRequiredPass();
        }
    }

    @Override
    public void successOperation() {
        if(view!=null){
            view.navigateToHome();
        }
    }

}
