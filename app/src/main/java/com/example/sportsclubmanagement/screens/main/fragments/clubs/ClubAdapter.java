package com.example.sportsclubmanagement.screens.main.fragments.clubs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ClubAdapterModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder> {

    private List<ClubAdapterModel> clubsList;
    private ClubAdapterListener clubAdapterListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView clubName;
        private Button joinBtn;

        public MyViewHolder(View view) {
            super(view);
            clubName = (TextView) view.findViewById(R.id.club_name);
            joinBtn = view.findViewById(R.id.joinClub_btn);
        }

        public void bind(final ClubAdapterModel clubAdapterModel) {
            clubName.setText(clubAdapterModel.getClubName());
            joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clubAdapterListener.onJoinClick(clubAdapterModel.getClubName());
                }
            });
        }
    }

    public ClubAdapter(List<ClubAdapterModel> clubList, ClubAdapterListener clubAdapterListener) {
        this.clubsList = clubList;
        this.clubAdapterListener = clubAdapterListener;
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
