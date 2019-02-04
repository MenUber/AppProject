package com.sw.menuber.presentation.dailydishes.starter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sw.menuber.R;

public class DailyStarterAdapter extends RecyclerView.Adapter<DailyStarterAdapter.ViewHolder> {

    public DailyStarterAdapter() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // variables
        public ViewHolder(View itemView){
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_starter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
