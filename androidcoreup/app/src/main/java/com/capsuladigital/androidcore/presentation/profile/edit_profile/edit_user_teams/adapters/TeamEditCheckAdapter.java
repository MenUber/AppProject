package com.capsuladigital.androidcore.presentation.profile.edit_profile.edit_user_teams.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.Team;
import com.capsuladigital.androidcore.data.repository.local.db.UserSQLiteOpenHelper;

import java.util.List;


public class TeamEditCheckAdapter extends RecyclerView.Adapter<TeamEditCheckAdapter.ViewHolder> {

    public List<Team> teams;
    public Context context;

    public TeamEditCheckAdapter(List<Team> teams, Context context) {
        this.teams = teams;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        ImageView teamPhoto;
        private CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.teamNameCard);
            teamPhoto = (ImageView) itemView.findViewById(R.id.imgTeamCard);
            check = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_checkable, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(teams.get(position).getName());
        holder.teamPhoto.setImageResource(R.drawable.flag);
        UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context,
                "user", null, 1);
        SQLiteDatabase db = user.getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM TEAMS ORDER BY NAME", null);
        teams.get(position).setStatus(-2);
        if (c.moveToFirst()) {
            do {
                if (c.getInt(0) == teams.get(position).getId()) {
                    holder.check.setChecked(true);
                    teams.get(position).setStatus(0);
                }
                Log.e("check", teams.get(position).getStatus() + " " + holder.check.isChecked());
            } while (c.moveToNext());
        }
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //I don't know why this works but it works
                if (!b) {
                    switch (teams.get(position).getStatus()) {
                        case 0:
                            teams.get(position).setStatus(-1)
                            ;
                            break;
                        case 1:
                            teams.get(position).setStatus(-2)
                            ;
                            break;
                    }

                } else {
                    switch (teams.get(position).getStatus()) {
                        case -1:
                            teams.get(position).setStatus(0)
                            ;
                            break;
                        case -2:
                            teams.get(position).setStatus(1)
                            ;
                            break;
                    }

                }
                Log.e("check", teams.get(position).getStatus() + " " + b);

            }
        });

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
