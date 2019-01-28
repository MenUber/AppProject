package com.capsuladigital.androidcore.presentation.profile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.Team;

import java.util.List;

/**
 * Created by lvert on 5/03/2018.
 */

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {

    public List<Team> teams;
    public Context context;

    public TeamListAdapter(List<Team> teams, Context context) {
        this.teams = teams;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView teamPhoto;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.teamName);
            teamPhoto = (ImageView) itemView.findViewById(R.id.imgTeam);

        }
    }


    @Override
    public TeamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        TeamListAdapter.ViewHolder viewholder = new TeamListAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(TeamListAdapter.ViewHolder holder, int position) {
        holder.name.setText(teams.get(position).getName());
        holder.teamPhoto.setImageResource(R.drawable.flag);

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }
}

