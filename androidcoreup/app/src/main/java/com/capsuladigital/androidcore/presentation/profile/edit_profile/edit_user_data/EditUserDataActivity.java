package com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_data;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.person.PersonEdit;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserDataActivity extends AppCompatActivity implements EditUserDataContract.View{

    @BindView(R.id.toolbar_squad)
    Toolbar toolbarEditUserData;
    @BindView(R.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R.id.edit_photo)
    FloatingActionButton editPhoto;
    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.et_lastname)
    TextInputEditText etLastname;
    @BindView(R.id.til_lastname)
    TextInputLayout tilLastname;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.et_birthday)
    TextInputEditText etBirthday;
    @BindView(R.id.til_birthday)
    TextInputLayout tilBirthday;
    private SessionManager sessionManager = SessionManager.getInstance(this);

    //global variables to store birthdate
    private int gDay;
    private int gMonth;
    private int gYear;

    private ProgressDialog mProgressDialog;
    private EditUserDataPresenter presenter;
    private Context context;

    private boolean nameChanged = false;
    private boolean lastNameChanged = false;
    private boolean birthdayChanged = false;
    private int originalGender = -1;
    private boolean flag;
    DatePickerDialog datePickerDialog;
    int initDay;
    int initMonth;
    int initYear;
    int currentDay;
    int currentMonth;
    int currentYear;

    @Override
    public void onBackPressed() {
        if (nameChanged || lastNameChanged || birthdayChanged || originalGender != spinGender.getSelectedItemPosition()) {
            showBackConfirmDialog();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_data);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarEditUserData);
        getSupportActionBar().setElevation(15.0f);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        context=this;
        if (presenter == null) {
            presenter = new EditUserDataPresenter(context);
        }

        getPresenter().onViewAttach(EditUserDataActivity.this);

        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);
        //Initialize date picker's values
        final Calendar c = Calendar.getInstance();
        initDay = c.get(Calendar.DAY_OF_MONTH);
        initMonth = c.get(Calendar.MONTH);
        initYear = c.get(Calendar.YEAR) - 13;
        //Initialize date picker
        initDatePicker();

        setInfo();

        etName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                nameChanged = true;
            }
        });

        etLastname.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                lastNameChanged = true;
            }
        });

        etBirthday.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                birthdayChanged = true;
            }
        });


        //Buttons listeners

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker();
                showDatePicker();
                hideKeyboard();
            }
        });


    }

    private void setInfo(){

        if(!sessionManager.getUserInfo().getPersonImageUrl().equals("")){
            Picasso.with(this).load(sessionManager.getUserInfo().getPersonImageUrl())
                    .resize(150, 150)
                    .placeholder(R.drawable.user_placeholder)
                    .into(userPhoto);
        }

        if (sessionManager.getUserInfo().getPersonGender().equals(getResources().getString(R.string.male_id))) {
            spinGender.setSelection(0);
        } else if (sessionManager.getUserInfo().getPersonGender().equals(getResources().getString(R.string.female_id))) {
            spinGender.setSelection(1);
        } else if (sessionManager.getUserInfo().getPersonGender().equals(getResources().getString(R.string.other_id))) {
            spinGender.setSelection(2);
        } else {
            spinGender.setSelection(3);
        }
        originalGender = spinGender.getSelectedItemPosition();
        etName.setText(sessionManager.getUserInfo().getPersonName());
        etLastname.setText(sessionManager.getUserInfo().getPersonLastName());
        etBirthday.setText(sessionManager.getUserInfo().getPersonBirthDay());
        if(!sessionManager.getUserInfo().getPersonBirthDay().equals("")){
            String[] parts = sessionManager.getUserInfo().getPersonBirthDay().split("-");
            currentDay = Integer.valueOf(parts[0]);
            currentMonth = Integer.valueOf(parts[1]) - 1;
            currentYear = Integer.valueOf(parts[2]);
        }else{
            currentDay = initDay;
            currentMonth = initMonth;
            currentYear = initYear;
        }
    }

    private EditUserDataContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(EditUserDataActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_profile_edit_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Pattern p = Pattern.compile("^[\\p{L}\\s'.-]+$");

            //Clean values if you press the button again
            tilName.setErrorEnabled(false);
            tilLastname.setErrorEnabled(false);
            tilBirthday.setErrorEnabled(false);
            tilName.setError(null);
            tilLastname.setError(null);
            tilBirthday.setError(null);

            //Flag for validate format
            flag = true;

            //Validations
            validateName(p);
            validateLastname(p);
            validateBirthday();
            //Fix bug when you press "Save" consecutive times
            saveLoadException();

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
                PersonEdit person = new PersonEdit(
                        etName.getText().toString(),
                        etLastname.getText().toString(),
                        gYear + "-" + gMonth + "-" + gDay,//We set birthdate values as a string
                        gender,
                        "");
                getPresenter().editPerson(person,etBirthday.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.complete_sign_in_form), Toast.LENGTH_SHORT).show();
            }

        } else {
            onBackPressed();
        }
        return true;
    }

    private void saveLoadException(){
        if (gDay == 0 || gMonth == 0 || gYear == 0) {
            String[] parts = etBirthday.getText().toString().split("-");
            gDay = Integer.parseInt(parts[0]);
            gMonth = Integer.parseInt(parts[1]);
            gYear = Integer.parseInt(parts[2]);
        }
    }

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

    private void initDatePicker() {
        final Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, R.style.DataPickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Set the values of the date picker in our global variables
                currentDay = dayOfMonth;
                currentMonth = month;
                currentYear = year;
                //Set temporal strings to store day and month values
                String sDay = Integer.toString(dayOfMonth);
                String sMonth = Integer.toString(month + 1);
                if (dayOfMonth < 10) {
                    sDay = "0" + sDay;
                }
                if (month + 1 < 10) {
                    sMonth = "0" + sMonth;
                }
                etBirthday.setText(sDay + "-" + sMonth + "-" + year);
                hideKeyboard();
            }
        }, currentYear, currentMonth, currentDay);
        //This limits the Date Picker date
        c.set(initYear, initMonth, initDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.setTitle(null);

    }

    public void showDatePicker() {
        datePickerDialog.show();
    }


    private void hideKeyboard() {
        InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
    }

    public void showBackConfirmDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // set title
        dialogBuilder.setTitle(getResources().getString(R.string.dialog_back_title));

        // set dialog message
        dialogBuilder
                .setMessage(getResources().getString(R.string.dialog_back_content))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        dialogBuilder.show();
    }

    @Override
    public void showSuccessToast() {
        Toast.makeText(context, context.getResources().getString(R.string.eud_sucess), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showWSError() {
        Toast.makeText(context, context.getResources().getString(R.string.eud_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionError() {
        Toast.makeText(context, context.getResources().getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchProfile() {
        finish();
    }
}
