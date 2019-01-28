package com.capsuladigital.androidcore.presentation.sign_up.sign_up_form;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.person.PersonSignUpForm;
import com.capsuladigital.androidcore.presentation.sign_up.improve_xperience.ImproveXperienceActivity;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFormActivity extends AppCompatActivity implements SignUpFormContract.View{


    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.et_lastname)
    TextInputEditText etLastname;
    @BindView(R.id.til_lastname)
    TextInputLayout tilLastname;
    @BindView(R.id.et_birthday)
    TextInputEditText etBirthday;
    @BindView(R.id.til_birthday)
    TextInputLayout tilBirthday;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password_confirm)
    TextInputEditText etPasswordConfirm;
    @BindView(R.id.til_password_confirm)
    TextInputLayout tilPasswordConfirm;
    @BindView(R.id.btn_sign_in)
    AppCompatButton btnSignIn;
    private SignUpFormPresenter presenter;
    private Context context;
    private ProgressDialog mProgressDialog;
    DatePickerDialog datePickerDialog;
    int currentDay;
    int currentMonth;
    int currentYear;

    //global variables to store birthdate
    private int gDay;
    private int gMonth;
    private int gYear;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        ButterKnife.bind(this);
        context = this;

        if (presenter == null) {
            presenter = new SignUpFormPresenter(context);
        }
        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);
        final Calendar c = Calendar.getInstance();
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        currentMonth = c.get(Calendar.MONTH);
        currentYear = c.get(Calendar.YEAR)-10;

    }

    private SignUpFormContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(SignUpFormActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onViewDettach();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (datePickerDialog!=null) {
            if (datePickerDialog.isShowing()) {
                datePickerDialog.dismiss();
                initPickerDate();
                showPickerDate();
            }
        }
    }

    private void register(){
        Pattern p = Pattern.compile("^[\\p{L}\\s'.-]+$");


        //Clean values if you press the button again
        tilName.setErrorEnabled(false);
        tilLastname.setErrorEnabled(false);
        tilBirthday.setErrorEnabled(false);
        tilEmail.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        tilPasswordConfirm.setErrorEnabled(false);

        tilName.setError(null);
        tilLastname.setError(null);
        tilBirthday.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilPasswordConfirm.setError(null);

        //Flag for validate format
        flag = true;

        //Validations
        validateName(p);
        validateLastname(p);
        validateBirthday();
        validateEmail();
        validatePassword();

        if (flag) {
            String gender;
            if (spinGender.getSelectedItem().toString().equals(getResources().
                    getString(R.string.male))) {
                gender = getResources().getString(R.string.male_id);
            } else if (spinGender.getSelectedItem().toString().equals(getResources().
                    getString(R.string.female))) {
                gender = getResources().getString(R.string.female_id);
            } else if (spinGender.getSelectedItem().toString().equals(getResources().
                    getString(R.string.other))) {
                gender = getResources().getString(R.string.other_id);
            } else {
                gender = getResources().getString(R.string.not_say_id);
            }
            PersonSignUpForm person = new PersonSignUpForm(
                    etName.getText().toString(),
                    etLastname.getText().toString(),
                    gYear + "-" + gMonth + "-" + gDay,
                    gender,
                    etEmail.getText().toString(),
                    etPassword.getText().toString());
            presenter.storeSignUpInfo(person);
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.complete_sign_in_form), Toast.LENGTH_SHORT).show();
        }


    }

    /*Validation methods*/

    private void validateName(Pattern p){
       Matcher mName = p.matcher(etName.getText().toString());
       if (TextUtils.isEmpty(etName.getText())) {
           etName.requestFocus();
           tilName.setErrorEnabled(true);
           tilName.setError(getResources().getString(R.string.required_name));
           flag = false;
       }
       if (!mName.find()) {
           etName.requestFocus();
           tilName.setErrorEnabled(true);
           tilName.setError(getResources().getString(R.string.invalid_name));
           flag = false;
       }
   }

    private void validateLastname(Pattern p){
        Matcher mLastname = p.matcher(etLastname.getText().toString());
        if (TextUtils.isEmpty(etLastname.getText())) {
            etLastname.requestFocus();
            tilLastname.setErrorEnabled(true);
            tilLastname.setError(getResources().getString(R.string.required_lastname));
            flag = false;

        }
        if (!mLastname.find()) {
            etLastname.requestFocus();
            tilLastname.setErrorEnabled(true);
            tilLastname.setError(getResources().getString(R.string.invalid_lastname));
            flag = false;
        }
    }

    private void validateBirthday(){

        if (TextUtils.isEmpty(etBirthday.getText())) {
            etBirthday.requestFocus();
            tilBirthday.setErrorEnabled(true);
            tilBirthday.setError(getResources().getString(R.string.required_birthday));
            flag = false;

        }

    }

    private void validateEmail(){

        if (!validateEmail(etEmail.getText().toString())) {
            etEmail.requestFocus();
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getResources().getString(R.string.required_email));
            flag = false;
        }

    }

    private boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void validatePassword(){
        if (etPassword.length() < 5) {
            etPassword.requestFocus();
            tilPassword.setErrorEnabled(true);
            tilPassword.setError(getResources().getString(R.string.required_pass));
            flag = false;
        }

        if (etPasswordConfirm.length() < 5) {
            etPasswordConfirm.requestFocus();
            tilPasswordConfirm.setErrorEnabled(true);
            tilPasswordConfirm.setError(getResources().getString(R.string.required_pass_confirm));
            flag = false;
        }

        if (!etPasswordConfirm.getText().toString().equals(etPassword.getText().toString())) {
            flag = false;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwords_doesnt_match), Toast.LENGTH_SHORT).show();
        }

    }

   /*Dialogs*/
   private void initPickerDate(){
       datePickerDialog = new DatePickerDialog(this, R.style.DataPickerTheme, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               //we set the values of the date picker in our global variables
               gDay = dayOfMonth;
               gMonth = month + 1;
               gYear = year;
               //we set temporal strings to store day and month values
               String sDay = Integer.toString(dayOfMonth);
               String sMonth = Integer.toString(month + 1);
               if (dayOfMonth < 10) {
                   sDay = "0" + sDay;
               }
               if (month + 1 < 10) {
                   sMonth = "0" + sMonth;
               }
               etBirthday.setText(sDay + "-" + sMonth + "-" + year);
               currentDay = dayOfMonth;
               currentMonth = month;
               currentYear = year;
               hideKeyboard();
           }
       }, currentYear, currentMonth, currentDay);
       //This limits the Date Picker date
       final Calendar tempCalendar=Calendar.getInstance();
       tempCalendar.set(currentYear,currentMonth,currentDay);
       datePickerDialog.getDatePicker().setMaxDate(tempCalendar.getTimeInMillis());
   }
    private void showPickerDate() {

        datePickerDialog.setTitle(null);
        datePickerDialog.show();
    }

    /*Utils*/
    private void hideKeyboard() {
        InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
    }




    @OnClick({R.id.et_birthday, R.id.btn_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_birthday:
                initPickerDate();
                showPickerDate();
                break;
            case R.id.btn_sign_in:
                register();
                break;
        }
    }

    @Override
    public void launchImproveXperienceActivity() {
        launchActivity(context, ImproveXperienceActivity.class);
    }

    private void launchActivity(Context context, Class destinyClass) {
        Intent intent = new Intent(context, destinyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void showEmailAlreadyRegisteredDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.already_registered_dialog_title));
        builder.setMessage(context.getResources().getString(R.string.already_registered_dialog_text)).setCancelable(true)
                .setPositiveButton(context.getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();

    }

    @Override
    public void showWSError() {
        Toast.makeText(context, context.getResources().getString(R.string.social_media_ws_error),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showConnectionError() {
        Toast.makeText(context, context.getResources().getString(R.string.error_connect),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.dismiss();
    }

}
