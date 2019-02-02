package com.sw.menuber;

import android.os.Handler;

public class LoginModel implements LoginInterface.Model {
    @Override
    public void validateUserModel(final String user,final String pass,final LoginInterface.loginListener listener) {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!user.isEmpty() && !pass.isEmpty()){

                    /*String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    if (user.matches(emailPattern)){

                    }*/
                    listener.successOperation();
                }else{
                    if(user.isEmpty()){
                            listener.setRequiredEmail();
                    }
                    if(pass.isEmpty()){
                        listener.setRequiredPass();
                    }
                }
            }
        },1000);


    }
}
