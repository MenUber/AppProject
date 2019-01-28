package com.capsuladigital.androidcore.presentation.home.league.standings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capsuladigital.androidcore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.Unbinder;

public class StandingsFragment extends Fragment implements StandingsContract.View {

    @BindView(R.id.st_swipe)
    SwipeRefreshLayout stSwipe;
    Unbinder unbinder;

    StandingsContract.Presenter presenter;
    @BindView(R.id.layout_standings)
    NestedScrollView layoutStandings;
    private Context context;
    private final String TAG = StandingsFragment.class.getSimpleName();

    private int id;

    public static StandingsFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        StandingsFragment fragment = new StandingsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        View rootView = inflater.inflate(R.layout.fragment_standings, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        readBundle(getArguments());

        if (presenter == null) {
            presenter = new StandingsPresenter(context);
        }
        getPresenter().onViewAttach(StandingsFragment.this);

        stSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

        return rootView;
    }

    private StandingsContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttach(StandingsFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettach();
    }

    @Override
    public void showContent() {
    }

    @Override
    public void hideContent() {

    }

    @Override
    public void stopRefresh() {
        stSwipe.setRefreshing(false);
    }

    @Override
    public void hideWSError() {

    }

    @Override
    public void showConnectionError() {
        layoutStandings.setVisibility(View.GONE);
    }

    @Override
    public void showWSError() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
