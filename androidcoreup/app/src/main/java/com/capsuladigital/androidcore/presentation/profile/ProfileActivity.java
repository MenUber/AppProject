package com.capsuladigital.androidcore.presentation.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.presentation.home.MainActivity;
import com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_teams.EditUserTeamsActivity;
import com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_data.EditUserDataActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.android.segmented.SegmentedGroup;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_profile)
    Toolbar toolbarProfile;
    @BindView(R.id.p_photo_person)
    CircleImageView pPhotoPerson;
    @BindView(R.id.btn_user_data)
    RadioButton btnUserData;
    @BindView(R.id.btn_user_teams)
    RadioButton btnUserTeams;
    @BindView(R.id.segment_profile)
    SegmentedGroup segmentProfile;
    @BindView(R.id.container)
    FrameLayout container;
    private SessionManager sessionManager;

    @Override
    public void onBackPressed() {
        launchActivity(this, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarProfile);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(15.0f);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = SessionManager.getInstance(this);

        btnUserData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showFragment(UserDataFragment.newInstance());
                }

            }
        });
        btnUserTeams.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showFragment(UserTeamsFragment.newInstance());
                }
            }
        });
        segmentProfile.check(btnUserData.getId());

    }

    private void setUserPhoto(){
        //Set user photo
        if (!sessionManager.getUserInfo().getPersonImageUrl().equals("")) {
            Picasso.with(this).load(sessionManager.getUserInfo().getPersonImageUrl())
                    .resize(150, 150)
                    .onlyScaleDown()
                    .placeholder(R.drawable.user_placeholder)
                    .into(pPhotoPerson);
        }

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit && btnUserData.isChecked()) {
            if (sessionManager.getKeyLType() == 1) {
                Intent edtUserDataIntent = new Intent().setClass(
                        getApplicationContext(), EditUserDataActivity.class);
                startActivity(edtUserDataIntent);
            } else {
                showSocialMediaErrorDialog();
            }
        } else if (item.getItemId() == R.id.action_edit && btnUserTeams.isChecked()) {
            Intent edtUserTeamsIntent = new Intent().setClass(
                    getApplicationContext(), EditUserTeamsActivity.class);
            startActivity(edtUserTeamsIntent);
        } else {
            onBackPressed();
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserPhoto();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void launchActivity(Context context, Class destinyClass) {
        Intent intent = new Intent(context, destinyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void showSocialMediaErrorDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // set title
        dialogBuilder.setTitle(getResources().getString(R.string.error_social_edit_title));

        // set dialog message
        dialogBuilder
                .setMessage(getResources().getString(R.string.error_social_edit_content))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //something
                    }
                });

        dialogBuilder.show();
    }

}

