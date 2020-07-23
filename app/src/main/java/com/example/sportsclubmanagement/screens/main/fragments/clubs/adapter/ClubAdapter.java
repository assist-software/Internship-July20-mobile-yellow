package com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder> {

    private List<ClubAdapterModel> clubsList;
    private ClubAdapterListener clubAdapterListener;
    private boolean hasBorder;

    public ClubAdapter(List<ClubAdapterModel> clubList, ClubAdapterListener clubAdapterListener, boolean hasBorder) {
        this.clubsList = clubList;
        this.clubAdapterListener = clubAdapterListener;
        this.hasBorder = hasBorder;
    }

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

        public void bind(final ClubAdapterModel clubAdapterModel) {
            clubName.setText(clubAdapterModel.getClubName());
            clubStatus.setText(clubAdapterModel.getClubStats());
            clubItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clubAdapterListener.onClubClick(clubAdapterModel.getClubName());
                }
            });
            if (hasBorder) {
                joinBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clubAdapterListener.onJoinClick(clubAdapterModel.getClubName());
                    }
                });
            } else {
                joinBtn.setBackgroundResource(0);
            }
        }
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
