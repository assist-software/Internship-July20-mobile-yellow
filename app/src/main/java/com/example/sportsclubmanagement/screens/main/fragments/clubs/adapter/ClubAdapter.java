package com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder> {

    private List<Clubs> clubsList;
    private ClubAdapterListener clubAdapterListener;
    private boolean hasBorder;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView clubName;
        private TextView clubStatus;
        private FrameLayout joinBtn;
        private RelativeLayout clubItem;

        public MyViewHolder(View view) {
            super(view);
            clubName = view.findViewById(R.id.club_name);
            clubStatus = view.findViewById(R.id.club_status_TextView);
            joinBtn = view.findViewById(R.id.club_status_frameLayout);
            clubItem = view.findViewById(R.id.club_item);
        }

        public void bind(final Clubs clubAdapterModel) {
            clubName.setText(clubAdapterModel.getName());
            clubStatus.setText(clubAdapterModel.getClubStats());
            clubItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clubAdapterListener.onClubClick(clubAdapterModel.getName());
                }
            });
            if (hasBorder) {
                joinBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clubAdapterListener.onJoinClick(clubAdapterModel.getName());
                    }
                });
            } else {
                joinBtn.setBackgroundResource(0);
            }
        }
    }

    public ClubAdapter(List<Clubs> clubList, ClubAdapterListener clubAdapterListener, boolean hasBorder) {
        this.clubsList = clubList;
        this.clubAdapterListener = clubAdapterListener;
        this.hasBorder = hasBorder;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.club_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(clubsList.get(position));
    }

    @Override
    public int getItemCount() {
        return clubsList.size();
    }
}
