package com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_teams;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.Team;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_teams.adapters.TeamEditCheckAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserTeamsActivity extends AppCompatActivity implements EditUserTeamsContract.View{


    @BindView(R.id.toolbar_edit_user_teams)
    Toolbar toolbarEditUserTeams;
    @BindView(R.id.user_photo_teams)
    CircleImageView userPhotoTeams;
    @BindView(R.id.recyclerTeams)
    RecyclerView recyclerTeams;
    @BindView(R.id.teams_container)
    FrameLayout teamsContainer;
    private SessionManager sessionManager = SessionManager.getInstance(this);
    private ProgressDialog mProgressDialog;
    private EditUserTeamsPresenter presenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_teams);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarEditUserTeams);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(15.0f);

        context = this;

        if (presenter == null) {
            presenter = new EditUserTeamsPresenter(context);
        }

        mProgressDialog = new ProgressDialog(context, R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        //Set user photo
        setUserPhoto();
        //SetTeams
        recyclerTeams.setLayoutManager(new LinearLayoutManager(this));
        getPresenter().getAllTeams();
    }

    //Get presenter
    private EditUserTeamsContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setTeams(List<Team> teams){
        TeamEditCheckAdapter adapterTeams = new TeamEditCheckAdapter(teams, this);
        recyclerTeams.setAdapter(adapterTeams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(EditUserTeamsActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }

    private void setUserPhoto(){
        if (!sessionManager.getUserInfo().getPersonImageUrl().equals("")) {
            Picasso.with(this).load(sessionManager.getUserInfo().getPersonImageUrl())
                    .resize(150, 150)
                    .onlyScaleDown()
                    .placeholder(R.drawable.user_placeholder)
                    .into(userPhotoTeams);
        }
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
            getPresenter().editFavTeams(((TeamEditCheckAdapter)recyclerTeams.getAdapter()).getTeams());
        } else {
            finish();
        }
        return true;
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
