package com.capsuladigital.androidcore.presentation.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.presentation.home.MainActivity;
import com.capsuladigital.androidcore.presentation.restore_password.RestorePasswordActivity;
import com.capsuladigital.androidcore.presentation.sign_up.sign_up_form.SignUpFormActivity;
import com.capsuladigital.androidcore.util.GoogleSingInWrapper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.capsuladigital.androidcore.R.id.button_dialog;
import static com.capsuladigital.androidcore.R.id.et_restore_pass_email;
import static com.capsuladigital.androidcore.R.id.til_restore_pass_email;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.et_user_email)
    TextInputEditText etUserEmail;
    @BindView(R.id.til_user_email)
    TextInputLayout tilUserEmail;
    @BindView(R.id.et_user_password)
    TextInputEditText etUserPassword;
    @BindView(R.id.til_user_password)
    TextInputLayout tilUserPassword;
    @BindView(R.id.tv_pass_recovery)
    TextView tvPassRecovery;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.iv_fb_login)
    ImageView ivFbLogin;
    @BindView(R.id.iv_google_login)
    ImageView ivGoogleLogin;
    @BindView(R.id.login_form)
    NestedScrollView loginForm;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;

    private ProgressDialog mProgressDialog;
    private LoginPresenter presenter;
    private Context context;
    private CallbackManager mCallbackManager;
    private GoogleSingInWrapper mGoogleSingInWrapper;
    public static final int SIGN_IN_GOOGLE_CODE = 777;
    boolean mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //InitializeFacebookSDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        context = this;

        if (presenter == null) {
            presenter = new LoginPresenter(context);
        }

        getPresenter().onViewAttach(LoginActivity.this);

        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        mGoogleSingInWrapper = GoogleSingInWrapper.getInstance(this);

        setUpLoginWithFB();

    }


    private void setUpLoginWithFB() {

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject jsonObject,
                                                            GraphResponse response) {

                                        // Getting FB User Data
                                        Bundle facebookData = getFacebookData(jsonObject);

                                        //We set the user profile info in temp variables
                                        final String name = facebookData.getString("first_name");
                                        final String lastName = facebookData.getString("last_name");
                                        // final String gender = facebookData.getString("gender", "");
                                        final String email = facebookData.getString("email");
                                        final String urlPhoto = facebookData.getString("profile_pic", "");
                                        String fbBirthday = facebookData.getString("birthday", "");

                                        //Only for debug purpose
                                        Log.e("FIRST NAME", name);
                                        Log.e("LAST NAME", lastName);
                                        Log.e("EMAIL", email);
                                        Log.e("URL PHOTO", urlPhoto);

                                        if (!fbBirthday.equals("")) {

                                            Log.e("BIRTHDAY", fbBirthday);

                                            //We give the correct date format for birthday
                                            String[] parts = fbBirthday.split("/");
                                            int gMonth = Integer.parseInt(parts[0]);
                                            int gDay = Integer.parseInt(parts[1]);
                                            int gYear = Integer.parseInt(parts[2]);

                                            fbBirthday = gYear + "-" + gMonth + "-" + gDay;

                                            Log.e("BIRTHDAY", fbBirthday);

                                        }
                                        //Call the presenter
                                        getPresenter().socialMedia(email, name, lastName, urlPhoto, fbBirthday);
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        showFBLoginCancelToast();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("Facebook error:", exception.getMessage());
                        showSocialMediaWSError();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(LoginActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }

    /*
    We override activity result for login with social networks
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Only for Google Sign In
        if (requestCode == SIGN_IN_GOOGLE_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        //This works for Facebook Sign In
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*
    Methods for Google
     */

    //Method for handle the result of sign in operation
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleSingInWrapper.getGoogleApiClient());
            if (opr.isDone()) {
                GoogleSignInResult singInResult = opr.get();
                handleSignInResultAfterAttempt(singInResult);
            } else {
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                        handleSignInResultAfterAttempt(googleSignInResult);
                    }
                });
            }
        } else {
            showGoogleLoginFailureToast();
        }

    }

    //Method for handle a successful sign in operation
    private void handleSignInResultAfterAttempt(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            if (account != null) {//Validate if result isn't null

                mGoogleSingInWrapper.getGoogleApiClient().connect();

                //We set the user profile info in temp variables

                final String name = account.getGivenName();
                final String lastName = account.getFamilyName();
                final String email = account.getEmail();
                final String urlPhoto;
                if (account.getPhotoUrl() != null) {
                    urlPhoto = account.getPhotoUrl().toString();
                } else {
                    urlPhoto = "";
                }
                final String birthday = getResources().getString(R.string.null_birthdate);

                //Only for debug purpose
                Log.e("FIRST NAME", name);
                Log.e("LAST NAME", lastName);
                Log.e("EMAIL", email);
                Log.e("URL PHOTO", urlPhoto);
                Log.e("BIRTHDAY", birthday);

                //We call the presenter. Oh yeah!
                getPresenter().socialMedia(email, name, lastName, urlPhoto, birthday);
            }

        } else {

            showGoogleAuthError();
        }
    }

    /*
    Method for Facebook Login
    */

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));

        } catch (Exception e) {
            Log.d("TAG", "BUNDLE Exception : " + e.toString());
        }

        return bundle;
    }

    /*
    Login method for regular user
     */

    public void login() {
        tilUserPassword.setErrorEnabled(false);
        tilUserEmail.setErrorEnabled(false);
        tilUserEmail.setError(null);
        tilUserPassword.setError(null);
        mFlag = true;


        validatePassword();

        validateEmailEditText();

        if (mFlag) {
            getPresenter().login(etUserEmail.getText().toString(), etUserPassword.getText().toString());
        } else {
            showCompleteLoginFormToast();
        }

    }

    //Get presenter
    private LoginContract.Presenter getPresenter() {
        return presenter;
    }

    /*
    Login Form Validations
    */

    private boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void validateEmailEditText() {
        if (!validateEmail(etUserEmail.getText().toString())) {
            etUserEmail.requestFocus();
            tilUserEmail.setErrorEnabled(true);
            tilUserEmail.setError(getResources().getString(R.string.required_email));
            mFlag = false;
        }
    }

    private void validatePassword() {
        if (etUserPassword.length() < 5) {
            etUserPassword.requestFocus();
            tilUserPassword.setErrorEnabled(true);
            tilUserPassword.setError(getResources().getString(R.string.invalid_pass));
            mFlag = false;
        }
    }

    /*
    Dialogs
    */
    public void showRestorePassDialog() {
        final Dialog dialogBuilder = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_restore_pass, null);
        final Button sendButton = (Button) dialogView.findViewById(button_dialog);
        final EditText etEmail = (EditText) dialogView.findViewById(et_restore_pass_email);
        final TextInputLayout tilEmail = (TextInputLayout) dialogView.findViewById(til_restore_pass_email);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tilEmail.setErrorEnabled(false);
                tilEmail.setError(null);

                mFlag = true;
                if (!validateEmail(etEmail.getText().toString())) {
                    etEmail.requestFocus();
                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError(getResources().getString(R.string.invalid_email));
                    mFlag = false;
                }

                if (mFlag) {
                    getPresenter().sendPassUpdateAttempt(etEmail.getText().toString());
                    dialogBuilder.dismiss();
                } else {
                    showCompleteRestorePasswordFormToast();
                }

            }
        });
        dialogBuilder.setContentView(dialogView);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBuilder.show();
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.dismiss();
    }

    /*
    UI messages methods
    */

    //Login
    @Override
    public void showSuccessToast(String name) {
        Toast.makeText(context, context.getResources().getString(R.string.welcome) + " " + name + "!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWrongCredentialsToast() {
        Toast.makeText(context, context.getResources().getString(R.string.email_password_ws_error),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmailConfirmError() {
        Toast.makeText(context, context.getResources().getString(R.string.email_confirmation_error),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSocialMediaWSError() {
        Toast.makeText(context, context.getResources().getString(R.string.social_media_ws_error),
                Toast.LENGTH_LONG).show();
    }

    public void showCompleteLoginFormToast() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.cant_pass), Toast.LENGTH_SHORT).show();
    }

    //Social Media Login
    @Override
    public void showSocialMediaLoginAttemptError() {
        Toast.makeText(context, context.getResources().getString(R.string.social_networks_login_attempt_error),
                Toast.LENGTH_LONG).show();
    }

    public void showFBLoginCancelToast() {
        Toast.makeText(this, getResources().getString(R.string.fb_login_cancel), Toast.LENGTH_SHORT).show();
    }

    public void showGoogleLoginFailureToast() {
        Toast.makeText(this, getResources().getString(R.string.google_login_failure), Toast.LENGTH_SHORT).show();
    }

    public void showGoogleAuthError() {
        Toast.makeText(this, getResources().getString(R.string.google_auth_error), Toast.LENGTH_SHORT).show();
    }

    //Restore password
    public void showCompleteRestorePasswordFormToast() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.complete_sign_in_form), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPassRestoreDialogSucess() {
        Toast.makeText(context, context.getResources().getString(R.string.pass_restore_dialog_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPassRestoreDialogFailure() {
        Toast.makeText(context, context.getResources().getString(R.string.pass_restore_dialog_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPassRestoreEmailNotConfirmed() {
        Toast.makeText(context, context.getResources().getString(R.string.pass_restore_dialog_email_not_confirmed), Toast.LENGTH_SHORT).show();
    }


    //Connection error
    @Override
    public void showConnectionError() {
        Toast.makeText(context, context.getResources().getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
    }

    /*
    Launch activities
    */

    @Override
    public void launchHome() {
        launchActivityClearStack(this, MainActivity.class);
    }

    @Override
    public void launchSignUp() {
        launchActivity(this, SignUpFormActivity.class);
    }

    @Override
    public void launchRestorePassword() {
        launchActivity(this, RestorePasswordActivity.class);
    }

    private void launchActivityClearStack(Context context, Class destinyClass) {
        Intent intent = new Intent(context, destinyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void launchActivity(Context context, Class destinyClass) {
        Intent intent = new Intent(context, destinyClass);
        context.startActivity(intent);
    }

    @Override
    public void subscribeToNotifications() {
        FirebaseMessaging.getInstance().subscribeToTopic("peru");
        FirebaseMessaging.getInstance().subscribeToTopic("general");
    }

    /*
    On click
     */

    @OnClick({R.id.btn_login, R.id.tv_pass_recovery, R.id.tv_sign_up, R.id.iv_fb_login, R.id.iv_google_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_pass_recovery:
                showRestorePassDialog();
                break;
            case R.id.tv_sign_up:
                launchSignUp();
                break;
            case R.id.iv_fb_login:
                LoginManager.getInstance().
                        logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile", "user_birthday"));
                break;
            case R.id.iv_google_login:
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSingInWrapper.getGoogleApiClient());
                startActivityForResult(intent, SIGN_IN_GOOGLE_CODE);
                break;
        }
    }
}

