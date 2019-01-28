package com.capsuladigital.androidcore.presentation.profile;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.Team;
import com.capsuladigital.androidcore.data.repository.local.db.UserSQLiteOpenHelper;
import com.capsuladigital.androidcore.presentation.profile.adapters.TeamListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserTeamsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.recyclerUserTeams)
    RecyclerView recyclerUserTeams;
    TeamListAdapter adapterTeams;
    @BindView(R.id.tv_not_found)
    TextView tvNotFound;
    private Context context;
    private final String TAG = UserTeamsFragment.class.getSimpleName();
    private boolean teamsFound = false;

    public UserTeamsFragment() {
        // Required empty public constructor
    }

    public static UserTeamsFragment newInstance() {
        UserTeamsFragment fragment = new UserTeamsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_teams, container, false);
        recyclerUserTeams = (RecyclerView) view.findViewById(R.id.recyclerUserTeams);
        recyclerUserTeams.setLayoutManager(new LinearLayoutManager(getActivity()));
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        adapterTeams = new TeamListAdapter(new ArrayList<Team>(), context);
        recyclerUserTeams.setAdapter(adapterTeams);
        setFavTeams();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume()");
        super.onResume();
        setFavTeams();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void setFavTeams() {
        tvNotFound.setVisibility(View.GONE);
        teamsFound = false;
        Log.e("setFavTeams()", "onResume()");
        List<Team> teams = new ArrayList<>();
        UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context,
                "user", null, 1);
        //Open connection
        SQLiteDatabase db = user.getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM TEAMS ORDER BY NAME", null);
        if (c.moveToFirst()) {
            teamsFound = true;
            do {
                teams.add(new Team(c.getString(1), c.getString(2), c.getInt(0)));
            } while (c.moveToNext());
        }
        if (!teamsFound) {
            tvNotFound.setVisibility(View.VISIBLE);
        }
        //Close cursor
        c.close();
        adapterTeams.teams = teams;
        recyclerUserTeams.getAdapter().notifyDataSetChanged();
    }
}
