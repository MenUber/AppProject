package com.capsuladigital.androidcore.presentation.home.league;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.presentation.home.MainActivity;
import com.capsuladigital.androidcore.presentation.home.league.match.MatchFragment;
import com.capsuladigital.androidcore.presentation.home.league.standings.StandingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LeagueFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.league_content)
    FrameLayout leagueContent;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    private Context context;
    private final String TAG = LeagueFragment.class.getSimpleName();
    private Fragment fragment;
    private int id;

    public static LeagueFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        LeagueFragment fragment = new LeagueFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        readBundle(getArguments());
        if (id == 1) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_worldcup);
        }

        View rootView = inflater.inflate(R.layout.fragment_league, container, false);
        context = getActivity();
        unbinder = ButterKnife.bind(this, rootView);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btm_standings:
                        fragment = StandingsFragment.newInstance(id);
                        break;
                    case R.id.btm_fixture:
                        fragment = MatchFragment.newInstance(id);
                        break;
                }

                FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.league_content, fragment);
                transaction.commit();

                return true;
            }
        });

        //Set default fragment
        bottomNavigation.setSelectedItemId(R.id.btm_fixture);

        //reselection not allowed
        bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
