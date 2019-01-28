package com.capsuladigital.androidcore.presentation.squad.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.model.team.TeamPlayer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SquadAdapter extends RecyclerView.Adapter<SquadAdapter.ViewHolder> {

    public List<TeamPlayer> squad;
    public Context context;

    public SquadAdapter(Context context) {
        this.context = context;
    }

    public SquadAdapter(List<TeamPlayer> squad, Context context) {
        this.squad = squad;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView name;
        //private TextView goals;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.iv_photo);
            name = itemView.findViewById(R.id.tv_name);
            //goals = itemView.findViewById(R.id.tv_goals);
        }
    }

    @Override
    public SquadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_squad, parent, false);
        SquadAdapter.ViewHolder viewholder = new SquadAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(SquadAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load(squad.get(position).getPhoto()).
                placeholder(R.drawable.user_placeholder).
                into(holder.photo);
        holder.name.setText(squad.get(position).getName() + " " + squad.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        if (squad == null) {
            return 0;
        } else {
            return squad.size();
        }
    }
}
