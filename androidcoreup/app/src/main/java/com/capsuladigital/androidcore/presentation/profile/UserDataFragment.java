package com.capsuladigital.androidcore.presentation.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UserDataFragment extends Fragment {

    @BindView(R.id.person_name_profile)
    TextView personNameProfile;
    @BindView(R.id.person_birthday_profile)
    TextView personBirthdayProfile;
    @BindView(R.id.person_gender_profile)
    TextView personGenderProfile;
    @BindView(R.id.person_email_profile)
    TextView personEmailProfile;
    Unbinder unbinder;
    private SessionManager sessionManager;


    public UserDataFragment() {
        // Required empty public constructor
    }

    public static UserDataFragment newInstance() {
        UserDataFragment fragment = new UserDataFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Setting person data in the navigation drawer

        View view = inflater.inflate(R.layout.fragment_user_data, container, false);

        sessionManager = SessionManager.getInstance(getActivity());

        unbinder = ButterKnife.bind(this, view);
        setPersonalData();
        return view;

    }

    private void setPersonalData() {
        //Set Full name
        personNameProfile.setText(sessionManager.getUserInfo().getPersonName()+ " " + sessionManager.getUserInfo().getPersonLastName());
        //Set Birthday
        if (sessionManager.getUserInfo().getPersonBirthDay().equals("")) {
            personBirthdayProfile.setText(getResources().getString(R.string.non_specific));
        } else {
            personBirthdayProfile.setText(sessionManager.getUserInfo().getPersonBirthDay());
        }
        //Set Gender
        if (sessionManager.getUserInfo().getPersonGender().equals(getResources().getString(R.string.male_id))) {
            personGenderProfile.setText(getResources().getString(R.string.male));
        } else if (sessionManager.getUserInfo().getPersonGender().equals(getResources().getString(R.string.female_id))) {
            personGenderProfile.setText(getResources().getString(R.string.female));
        } else if (sessionManager.getUserInfo().getPersonGender().equals(getResources().getString(R.string.other_id))) {
            personGenderProfile.setText(getResources().getString(R.string.other));
        } else if (sessionManager.getUserInfo().getPersonGender().equals(getResources().getString(R.string.not_say_id))) {
            personGenderProfile.setText(getResources().getString(R.string.not_say));
        } else {
            personGenderProfile.setText(getResources().getString(R.string.non_specific));
        }
        //Set Email
        personEmailProfile.setText(sessionManager.getKeyPEmail());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        setPersonalData();
    }




    @Override
    public void onPause() {
        super.onPause();
    }
}
