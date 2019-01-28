package com.capsuladigital.androidcore.presentation.home.league.match.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.match.Match;
import com.capsuladigital.androidcore.presentation.squad.SquadActivity;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    public List<Match> matches;
    public Context context;

    public MatchAdapter(Context context) {
        this.context = context;
    }

    public MatchAdapter(List<Match> matches, Context context) {
        this.matches = matches;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView localPhoto, visitorPhoto;
        private TextView localName, visitorName, localGoals, visitorGoals, group, stadium, date, hour;
        private LinearLayout result;

        public ViewHolder(View itemView) {
            super(itemView);
            localPhoto = itemView.findViewById(R.id.iv_local);
            localName = itemView.findViewById(R.id.tv_local);
            visitorPhoto = itemView.findViewById(R.id.iv_visitor);
            visitorName = itemView.findViewById(R.id.tv_visitor);
            localGoals = itemView.findViewById(R.id.tv_local_goals);
            visitorGoals = itemView.findViewById(R.id.tv_visitor_goals);
            group = itemView.findViewById(R.id.tv_group);
            stadium = itemView.findViewById(R.id.tv_stadium);
            date = itemView.findViewById(R.id.tv_date);
            hour = itemView.findViewById(R.id.tv_hour);
            result = itemView.findViewById(R.id.layout_result);
        }
    }

    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        MatchAdapter.ViewHolder viewHolder = new MatchAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MatchAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.localPhoto.setImageResource(R.drawable.flag);
        holder.localPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("id",String.valueOf(matches.get(position).getLocalId()));
                launchSquadActivity(matches.get(position).getLocalId(), matches.get(position).getLocal());
            }
        });
        holder.visitorPhoto.setImageResource(R.drawable.flag);
        holder.visitorPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("id",String.valueOf(matches.get(position).getVisitorId()));
                launchSquadActivity(matches.get(position).getVisitorId(), matches.get(position).getVisitor());
            }
        });

        holder.localName.setText(matches.get(position).getLocal());
        holder.visitorName.setText(matches.get(position).getVisitor());
        holder.localGoals.setText(matches.get(position).getLocalGoals());
        holder.visitorGoals.setText(matches.get(position).getVisitorGoals());
        holder.group.setText(matches.get(position).getGroup());
        holder.stadium.setText(matches.get(position).getStadium());
        holder.date.setText(matches.get(position).getDateText());
        holder.hour.setText(matches.get(position).getHour());

        if (matches.get(position).getStatus().equals("-1")) {
            holder.hour.setVisibility(View.VISIBLE);
            holder.result.setVisibility(View.GONE);
        } else {
            holder.hour.setVisibility(View.GONE);
            holder.result.setVisibility(View.VISIBLE);
        }
    }

    private void launchSquadActivity(int id, String name) {
        Intent intent = new Intent().setClass(context, SquadActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        context.startActivity(intent);

    }



    @Override
    public int getItemCount() {
        if (matches == null) {
            return 0;
        } else {
            return matches.size();
        }
    }
}
