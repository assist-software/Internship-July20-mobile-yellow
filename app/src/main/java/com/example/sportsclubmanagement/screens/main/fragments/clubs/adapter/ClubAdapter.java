package com.example.sportsclubmanagement.screens.main.fragments.clubs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.apiModels.Response.Clubs;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder> {

    private List<Clubs> clubsList;
    private ClubAdapterListener clubAdapterListener;
    private boolean hasBorder;

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

    public ClubAdapter(List<Clubs> clubList, ClubAdapterListener clubAdapterListener, boolean hasBorder) {
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

        public void bind(final Clubs clubAdapterModel) {
            clubName.setText(clubAdapterModel.getClubInfo().getName());
            if (!(clubAdapterModel.isInvited() || clubAdapterModel.isMember() || clubAdapterModel.isRequested())) {
                clubStatus.setText(R.string.join);
            }
            if (clubAdapterModel.isMember()) {
                clubStatus.setText(R.string.joined);
            }
            if (clubAdapterModel.isRequested()) {
                clubStatus.setText(R.string.pending);
            }
            clubItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clubAdapterListener.onClubClick(clubAdapterModel.getClubInfo().getName());
                }
            });
            if (hasBorder) {
                joinBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clubAdapterListener.onJoinClick(clubAdapterModel.getClubInfo().getName(), clubAdapterModel.getClubInfo().getId());
                    }
                });
            } else {
                if(clubAdapterModel.isRequested()){
                    clubStatus.setText("Pending");
                }
                if(clubAdapterModel.isMember())
                {
                    clubStatus.setText("Joined");
                }
                joinBtn.setBackgroundResource(0);
            }
        }
    }
}
