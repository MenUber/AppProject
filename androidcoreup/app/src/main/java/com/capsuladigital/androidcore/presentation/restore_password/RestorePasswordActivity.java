package com.capsuladigital.androidcore.presentation.restore_password;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.presentation.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestorePasswordActivity extends AppCompatActivity implements RestorePasswordContract.View {


    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txtPinEntry;
    @BindView(R.id.edit_text_password)
    TextInputEditText editTextPassword;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.edit_text_password_repeat)
    TextInputEditText editTextPasswordRepeat;
    @BindView(R.id.password_repeat)
    TextInputLayout passwordRepeat;
    @BindView(R.id.button_restore)
    AppCompatButton buttonRestore;
    private ProgressDialog mProgressDialog;
    private RestorePasswordPresenter presenter;
    private Context context;
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;

        if (presenter == null) {
            presenter = new RestorePasswordPresenter(context);
        }

        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        //Navigation sequence
        txtPinEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (EditorInfo.IME_ACTION_NEXT == actionId) {
                    editTextPassword.requestFocus();
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(RestorePasswordActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }

    private RestorePasswordContract.Presenter getPresenter() {
        return presenter;
    }


    @Override
    public void showConnectionError() {
        Toast.makeText(context, context.getResources().getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchLoginActivity() {
        launchActivityClearStack(this, LoginActivity.class);
    }

    @Override
    public void showPassRestoreSuccess() {
        Toast.makeText(context, context.getResources().getString(R.string.pass_restore_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPassRestoreFailure() {
        Toast.makeText(context, context.getResources().getString(R.string.pass_restore_failure), Toast.LENGTH_SHORT).show();
    }

    public void showCompleteRestorePasswordFormToast() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.complete_sign_in_form), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.dismiss();
    }

    private void launchActivityClearStack(Context context, Class destinyClass) {
        Intent intent = new Intent(context, destinyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void restorePassword(){
            txtPinEntry.setError(null);
            password.setErrorEnabled(false);
            password.setError(null);
            passwordRepeat.setErrorEnabled(false);
            passwordRepeat.setError(null);
            flag = true;

            validatePinEntry();
            validatePassword();
            validatePasswordRepeat();
            validateEqualPasswords();

            if (flag) {
                presenter.updatePassword(
                        editTextPassword.getText().toString(),
                        txtPinEntry.getText().toString());
            } else {
               showCompleteRestorePasswordFormToast();
            }
    }

    public void validatePinEntry(){
        if (TextUtils.isEmpty(txtPinEntry.getText())) {
            txtPinEntry.requestFocus();
            txtPinEntry.setError(getResources().getString(R.string.pin_required));
            flag = false;
        }

    }

    public void validatePassword(){

    if (editTextPassword.length() < 5) {
        editTextPassword.requestFocus();
        password.setErrorEnabled(true);
        password.setError(getResources().getString(R.string.required_pass));
        flag = false;
    }

    }

    public void validatePasswordRepeat(){
        if (editTextPasswordRepeat.length() < 5) {
            editTextPasswordRepeat.requestFocus();
            passwordRepeat.setErrorEnabled(true);
            passwordRepeat.setError(getResources().getString(R.string.required_pass_confirm));
            flag = false;
        }
    }

    public void validateEqualPasswords(){

        if (!editTextPasswordRepeat.getText().toString().equals(editTextPassword.getText().toString())) {
            flag = false;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwords_doesnt_match), Toast.LENGTH_SHORT).show();
        }

    }
    @OnClick(R.id.button_restore)
    public void onViewClicked() {
        restorePassword();
    }
}
