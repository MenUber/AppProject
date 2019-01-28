package com.capsuladigital.androidcore.presentation.sign_up.improve_xperience.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.Team;

import java.util.List;

/**
 * Created by lvert on 5/03/2018.
 */

public class TeamSignUpCheckAdapter extends RecyclerView.Adapter<TeamSignUpCheckAdapter.ViewHolder>{
    public List<Team> teams;
    public Context context;

    public TeamSignUpCheckAdapter(List<Team> teams, Context context) {
        this.teams = teams;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        ImageView teamPhoto;
        private CheckBox check;
        LinearLayout layoutCheckable;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.teamNameCard);
            teamPhoto = (ImageView) itemView.findViewById(R.id.imgTeamCard);
            check = (CheckBox) itemView.findViewById(R.id.checkbox);
            layoutCheckable=(LinearLayout) itemView.findViewById(R.id.layout_checkable);
        }
    }

    @Override
    public TeamSignUpCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_checkable, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(teams.get(position).getName());
        String localIcon = "team_" + teams.get(position).getId();
        int id = context.getResources().getIdentifier(localIcon, "drawable", context.getPackageName());
        holder.teamPhoto.setImageResource(id);
        if(holder.check.isChecked()){
            holder.check.setChecked(true);
            teams.get(position).setStatus(1);
        }else{
            holder.check.setChecked(false);
            teams.get(position).setStatus(0);
        }
        holder.layoutCheckable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check.isChecked()){
                    holder.check.setChecked(false);
                    teams.get(position).setStatus(0);
                }else{
                    holder.check.setChecked(true);
                    teams.get(position).setStatus(1);
                }
            }
        });
        if(teams.get(position).getId()==11){
            holder.check.setChecked(true);
            teams.get(position).setStatus(1);
        }

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }


    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
