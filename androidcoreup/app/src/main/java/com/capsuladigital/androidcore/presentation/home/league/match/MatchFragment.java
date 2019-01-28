package com.capsuladigital.androidcore.presentation.home.league.match;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.match.Match;
import com.capsuladigital.androidcore.data.model.team.Team;
import com.capsuladigital.androidcore.presentation.home.league.match.adapters.MatchAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchFragment extends Fragment implements MatchContract.View {
    @BindView(R.id.layout_connection_error)
    View layoutConnectionError;
    @BindView(R.id.layout_service_error)
    View layoutServiceError;
    @BindView(R.id.layout_matches)
    LinearLayout layoutMatches;
    @BindView(R.id.tv_date_filter)
    TextView tvDateFilter;
    @BindView(R.id.iv_date_filter)
    ImageView ivDateFilter;
    @BindView(R.id.date_filter_container)
    RelativeLayout dateFilterContainer;
    Unbinder unbinder;
    @BindView(R.id.tv_not_found)
    TextView tvNotFound;
    @BindView(R.id.match_recycler)
    RecyclerView rvMatches;
    @BindView(R.id.matches_swipe)
    SwipeRefreshLayout matchesSwipe;

    DatePickerDialog datePickerDialog;
    AlertDialog.Builder builderTeams;
    AlertDialog alertDialogTeams;
    private MatchContract.Presenter presenter;
    private Context context;
    private final String TAG = MatchFragment.class.getSimpleName();

    private int idLeague, size;
    private String date;
    MatchAdapter adapter;
    List<Match> matchList;
    private boolean matchesFound = false;
    int currentDay;
    int currentMonth;
    int currentYear;
    private ArrayList<String> teamsArray = new ArrayList<>();
    private LinearLayoutManager rvManager;

    public static MatchFragment newInstance(int idLeague) {
        Bundle bundle = new Bundle();
        bundle.putInt("idLeague", idLeague);

        MatchFragment fragment = new MatchFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public void readBundle(Bundle bundle) {
        if (bundle != null) {
            idLeague = bundle.getInt("idLeague");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        readBundle(getArguments());

        View rootView = inflater.inflate(R.layout.fragment_match, container, false);
        context = getActivity();
        setHasOptionsMenu(true);
        rvMatches = rootView.findViewById(R.id.match_recycler);
        rvManager=new LinearLayoutManager(context);
        rvMatches.setLayoutManager(rvManager);
        rvMatches.setAdapter(new MatchAdapter(context));

        presenter = new MatchPresenter(context);
        presenter.getMatches(idLeague);
        unbinder = ButterKnife.bind(this, rootView);

        final Calendar c = Calendar.getInstance();
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        currentMonth = c.get(Calendar.MONTH);
        currentYear = c.get(Calendar.YEAR);

        matchesSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentDay = c.get(Calendar.DAY_OF_MONTH);
                currentMonth = c.get(Calendar.MONTH);
                currentYear = c.get(Calendar.YEAR);
                dateFilterContainer.setVisibility(View.GONE);
                getPresenter().getMatches(idLeague);
            }
        });
        return rootView;
    }


    private MatchContract.Presenter getPresenter() {
        return presenter;
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
        if(alertDialogTeams!=null){
            if( alertDialogTeams.isShowing()){
                alertDialogTeams.dismiss();
                showTeamsDialog();
            }
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.appbar_matches_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_calendar) {
            initPickerDate();
            showPickerDate();
        } else if (item.getItemId() == R.id.action_teams) {
            getPresenter().getTeams();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttach(MatchFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setMatches(List<Match> matches) {
        adapter = new MatchAdapter(matches, context);
        rvMatches.setAdapter(adapter);
        matchList = adapter.matches;
        if (matchList == null) {
            size = 0;
            /*tvNotFound.setVisibility(View.VISIBLE);
            setHasOptionsMenu(false);*/
        } else {
            size = matchList.size();
            setHasOptionsMenu(true);
        }
        scrollToLastMatch();

    }

    public void scrollToLastMatch(){
        int chosen=0;
        for(int i=0;i<adapter.matches.size();i++){
            if(adapter.matches.get(i).getStatus().equals("0")||adapter.matches.get(i).getStatus().equals("-1")){
                chosen=i-1;
                break;
            }

        }
        Log.e("Final chosen iteration",String.valueOf(chosen));
        //Log.e(TAG,cDate.toString());
        rvManager.scrollToPositionWithOffset(chosen, 0);
    }

    @Override
    public void showContent() {
        layoutMatches.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        layoutMatches.setVisibility(View.GONE);
    }

    @Override
    public void stopRefresh() {
        matchesSwipe.setRefreshing(false);
    }

    @Override
    public void hideWSError() {
        layoutServiceError.setVisibility(View.GONE);
    }

    @Override
    public void showConnectionError() {
        layoutConnectionError.setVisibility(View.VISIBLE);
        layoutMatches.setVisibility(View.GONE);
    }

    @Override
    public void showWSError() {
        layoutServiceError.setVisibility(View.VISIBLE);
        layoutMatches.setVisibility(View.GONE);
        setHasOptionsMenu(false);
    }


    private void initPickerDate() {
        datePickerDialog = new DatePickerDialog(context, R.style.DataPickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
             filterMatchesByDate(year,month,dayOfMonth);
            }
        }, currentYear, currentMonth, currentDay);

    }

    public void showPickerDate() {
        datePickerDialog.updateDate(currentYear, currentMonth, currentDay);
        datePickerDialog.setTitle(null);
        datePickerDialog.show();
    }

    public void filterMatchesByDate(int year, int month, int dayOfMonth){
        tvNotFound.setVisibility(View.GONE);
        rvManager.scrollToPositionWithOffset(0, 0);
        matchesFound = false;
        //we set temporal strings to store day and month values
        String sDay = Integer.toString(dayOfMonth);
        String sMonth = Integer.toString(month + 1);
        if (dayOfMonth < 10) {
            sDay = "0" + sDay;
        }
        if (month + 1 < 10) {
            sMonth = "0" + sMonth;
        }
        date = year + "/" + sMonth + "/" + sDay;
        List<Match> filtered = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (matchList.get(i).getDate().equals(date)) {
                filtered.add(matchList.get(i));
                matchesFound = true;
            }
        }
        if (!matchesFound) {
            tvNotFound.setVisibility(View.VISIBLE);
        }
        currentDay = dayOfMonth;
        currentMonth = month;
        currentYear = year;
        adapter.matches = filtered;
        dateFilterContainer.setVisibility(View.VISIBLE);
        tvDateFilter.setText(getResources().getString(R.string.date) + " " + sDay + "/" + sMonth + "/" + year);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void setTeamsArray(List<Team> arrayList) {
        builderTeams = new AlertDialog.Builder(getActivity());
        teamsArray.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            teamsArray.add(arrayList.get(i).getName());
        }
        final String[] teams = teamsArray.toArray(new String[teamsArray.size()]);
        builderTeams.setTitle(context.getResources().getString(R.string.sf_dialog_choose_title));
        builderTeams.setItems(teams,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      filterMatchesByTeam(i);
                    }
                });
        alertDialogTeams = builderTeams.create();
        showTeamsDialog();

    }

    public void showTeamsDialog(){
        alertDialogTeams.show();
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        alertDialogTeams.getWindow().setLayout((int)(displayRectangle.width() *
                0.8f), (int)(displayRectangle.height() * 0.9f));
    }


    public void filterMatchesByTeam(int i){
        List<Match> filtered = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            if (matchList.get(j).getLocal().equals(teamsArray.get(i))||
                    matchList.get(j).getVisitor().equals(teamsArray.get(i))) {
                filtered.add(matchList.get(j));
            }
        }
        adapter.matches = filtered;
        dateFilterContainer.setVisibility(View.VISIBLE);
        tvDateFilter.setText(teamsArray.get(i));
        adapter.notifyDataSetChanged();
        rvManager.scrollToPositionWithOffset(0, 0);
    }

    @OnClick(R.id.iv_date_filter)
    public void onViewClicked() {
        final Calendar c = Calendar.getInstance();
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        currentMonth = c.get(Calendar.MONTH);
        currentYear = c.get(Calendar.YEAR);
        adapter.matches = matchList;
        adapter.notifyDataSetChanged();
        tvNotFound.setVisibility(View.GONE);
        dateFilterContainer.setVisibility(View.GONE);
        scrollToLastMatch();
    }
}