package com.capsuladigital.androidcore.presentation.sign_up.improve_xperience;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.person.PersonSignUpPost;
import com.capsuladigital.androidcore.data.model.team.Team;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.presentation.login.LoginActivity;
import com.capsuladigital.androidcore.presentation.sign_up.improve_xperience.adapters.TeamSignUpCheckAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ImproveXperienceActivity extends AppCompatActivity implements ImproveXperienceContract.View{


    @BindView(R.id.toolbar_interests)
    Toolbar toolbarInterests;
    @BindView(R.id.team_banner_id)
    FrameLayout teamBannerId;
    @BindView(R.id.recyclerTeams)
    RecyclerView recyclerTeams;
    @BindView(R.id.teams_container_id)
    LinearLayout teamsContainerId;
    @BindView(R.id.skip_teams_id)
    TextView skipTeamsId;
    @BindView(R.id.continue_teams_id)
    TextView continueTeamsId;
    @BindView(R.id.container_buttons_id)
    LinearLayout containerButtonsId;
    private boolean flag = false;
    private SessionManager sessionManager;
    private ImproveXperiencePresenter presenter;
    private ProgressDialog mProgressDialog;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_xperience);
        ButterKnife.bind(this);
        context = this;

        if (presenter == null) {
            presenter = new ImproveXperiencePresenter(context);
        }
        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        recyclerTeams.setLayoutManager(new LinearLayoutManager(this));
        getPresenter().getAllTeams();

    }

    private ImproveXperienceContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flag) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        getPresenter().onViewAttach(ImproveXperienceActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }


    @Override
    public void showSuccessToast() {
        Toast.makeText(context, context.getResources().getString(R.string.sign_in_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWSError() {
        Toast.makeText(context, context.getResources().getString(R.string.social_media_ws_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionError() {
        Toast.makeText(context, context.getResources().getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
    }

    public void setTeams(List<Team> teams){
        TeamSignUpCheckAdapter adapterTeams = new TeamSignUpCheckAdapter(teams, this);
        recyclerTeams.setAdapter(adapterTeams);
    }

    @Override
    public void showAlreadyRegisteredUser() {
        Toast.makeText(context, context.getResources().getString(R.string.sign_in_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.dismiss();
    }


    private void showEmailConfirmDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // set title
        dialogBuilder.setTitle(getResources().getString(R.string.email_confirm_dialog_title));

        // set dialog message
        dialogBuilder
                .setMessage(getResources().getString(R.string.email_confirm_dialog_content))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.email_confirm_dialog_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        launchEmailApps();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.email_confirm_dialog_back), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        launchLogin();
                        dialog.dismiss();
                    }
                });

        dialogBuilder.show();
    }

    private void signUpRequest(List<Team> teams, boolean areTeams) {

        sessionManager = SessionManager.getInstance(getApplicationContext());

        List<Integer> idTeams = new ArrayList<>();
        if (areTeams) {
            for (Team team : teams) {
                if (team.getStatus() != 0) {
                    idTeams.add(team.getId());
                    Log.e("gg", team.getId() + " " + team.getStatus());
                }
            }
        }
        PersonSignUpPost person = new PersonSignUpPost(
                sessionManager.getKeySignUpName(),
                sessionManager.getKeySignUpLastName(),
                sessionManager.getKeySignUpBirthday(),
                sessionManager.getKeySignUpGender(),
                sessionManager.getKeySignUpEmail(),
                sessionManager.getKeySignUpPassword(),
                idTeams
        );
        getPresenter().signUp(person);
    }

    public void skipTeamsSelection(){
        signUpRequest(((TeamSignUpCheckAdapter) recyclerTeams.getAdapter()).getTeams(), false);
        showEmailConfirmDialog();
    }

    public void selectTeams(){
        signUpRequest(((TeamSignUpCheckAdapter) recyclerTeams.getAdapter()).getTeams(), true);
        showEmailConfirmDialog();
    }

    public void launchLogin(){
        Intent loginIntent = new Intent().setClass(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void launchEmailApps(){
        Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        flag = true;
        startActivity(intent);
    }

    @OnClick({R.id.skip_teams_id, R.id.continue_teams_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.skip_teams_id:
                skipTeamsSelection();
                break;
            case R.id.continue_teams_id:
                selectTeams();
                break;
        }
    }
}
