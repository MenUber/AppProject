package com.capsuladigital.androidcore.presentation.squad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.TeamPlayer;
import com.capsuladigital.androidcore.presentation.squad.adapters.SquadAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SquadActivity extends AppCompatActivity implements SquadContract.View{

    @BindView(R.id.toolbar_squad)
    Toolbar toolbarSquad;
    @BindView(R.id.header_keeper)
    RelativeLayout headerKeeper;
    @BindView(R.id.keepers_recycler)
    RecyclerView keepersRecycler;
    @BindView(R.id.header_defender)
    RelativeLayout headerDefender;
    @BindView(R.id.defenders_recycler)
    RecyclerView defendersRecycler;
    @BindView(R.id.header_midfielder)
    RelativeLayout headerMidfielder;
    @BindView(R.id.midfielders_recycler)
    RecyclerView midfieldersRecycler;
    @BindView(R.id.header_attacker)
    RelativeLayout headerAttacker;
    @BindView(R.id.attackers_recycler)
    RecyclerView attackersRecycler;
    @BindView(R.id.layout_squad)
    LinearLayout layoutSquad;
    @BindView(R.id.squad_swipe)
    SwipeRefreshLayout squadSwipe;
    @BindView(R.id.bet_progress)
    ProgressBar betProgress;
    @BindView(R.id.layout_connection_error)
    View layoutConnectionError;
    @BindView(R.id.layout_service_error)
    View layoutServiceError;


    private SquadContract.Presenter presenter;
    private Context context;
    private final String TAG = SquadActivity.class.getSimpleName();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squad);
        ButterKnife.bind(this);

        context = this;

        setSupportActionBar(toolbarSquad);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        setTitle(intent.getStringExtra("name"));


        intent = getIntent();
        id = intent.getIntExtra("id",0);

        keepersRecycler.setLayoutManager(new LinearLayoutManager(context));
        keepersRecycler.setAdapter(new SquadAdapter(context));
        defendersRecycler.setLayoutManager(new LinearLayoutManager(context));
        defendersRecycler.setAdapter(new SquadAdapter(context));
        midfieldersRecycler.setLayoutManager(new LinearLayoutManager(context));
        midfieldersRecycler.setAdapter(new SquadAdapter(context));
        attackersRecycler.setLayoutManager(new LinearLayoutManager(context));
        attackersRecycler.setAdapter(new SquadAdapter(context));

        presenter = new SquadPresenter(context);
        presenter.getTeamSquad(id);

        squadSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getTeamSquad(id);
            }
        });


    }

    private SquadContract.Presenter getPresenter() {
        return presenter;
    }


    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttach(SquadActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettach();
    }

    @Override
    public void setKeepers(List<TeamPlayer> keepers) {
        SquadAdapter keepersAdapter = new SquadAdapter(keepers, context);
        keepersRecycler.setAdapter(keepersAdapter);
    }

    @Override
    public void setDefenders(List<TeamPlayer> defenders) {
        SquadAdapter defendersAdapter = new SquadAdapter(defenders, context);
        defendersRecycler.setAdapter(defendersAdapter);
    }

    @Override
    public void setMidfielders(List<TeamPlayer> midfielders) {
        SquadAdapter midfieldersAdapter = new SquadAdapter(midfielders, context);
        midfieldersRecycler.setAdapter(midfieldersAdapter);
    }

    @Override
    public void setAttackers(List<TeamPlayer> attackers) {
        SquadAdapter attackersAdapter = new SquadAdapter(attackers, context);
        attackersRecycler.setAdapter(attackersAdapter);
    }

    @Override
    public void showConnectionError() {
        layoutConnectionError.setVisibility(View.VISIBLE);
        layoutSquad.setVisibility(View.GONE);
    }

    @Override
    public void showWSError() {
        layoutServiceError.setVisibility(View.VISIBLE);
        layoutSquad.setVisibility(View.GONE);
    }

    @Override
    public void hideWSError() {
        layoutServiceError.setVisibility(View.GONE);
    }

    @Override
    public void showRecycler() {
        layoutSquad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecycler() {
        layoutSquad.setVisibility(View.GONE);
    }

    @Override
    public void stopRefresh() {
        squadSwipe.setRefreshing(false);
    }

}
