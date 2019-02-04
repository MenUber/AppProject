package com.sw.menuber;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View {
    private TextInputLayout textInputEmail,textInputPass;
    private AppCompatButton btnCheck;
    private LoginInterface.Presenter presenter;
    private TextView recoverPass;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEmail=(TextInputLayout)findViewById(R.id.til_user_email);
        textInputPass=(TextInputLayout)findViewById(R.id.til_user_pass);
        btnCheck = (AppCompatButton)findViewById(R.id.btn_login);
        recoverPass = (TextView) findViewById(R.id.tv_pass_recovery);
        presenter = new LoginPresenter(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnCheck.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                validateUserView();
            }
        });

        recoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPassDialog();
            }
        });
    }

/*    @Override
    public void setErrorEmail() {
        textInputEmail.setError("Invalid Email");
    }

    @Override
    public void setErrorPass() {
        textInputPass.setError("Invalid Password");
    }*/

    @Override
    public void setRequiredEmail() {
        textInputEmail.setError("field required");
    }
    @Override
    public void setRequiredPass() {
        textInputPass.setError("field required");
    }

    @Override
    public void navigateToHome() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void validateUserView(){

        presenter.validateUserPresenter(textInputEmail.getEditText().getText().toString().trim(),textInputPass.getEditText().getText().toString().trim());

    }

    public void showRecoverPassDialog(){
        final Dialog dialogBuilder = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogo_recover_pass, null);
        dialogBuilder.setContentView(dialogView);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBuilder.show();
    }
}
