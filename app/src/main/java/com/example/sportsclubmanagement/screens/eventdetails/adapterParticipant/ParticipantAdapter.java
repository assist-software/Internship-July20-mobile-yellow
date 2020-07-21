package com.example.sportsclubmanagement.screens.eventdetails.adapterParticipant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.ParticipantAdapterModel;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.MyViewHolder> {
    private List<ParticipantAdapterModel> participantLists;
    private Context ctx;
    private ParticipantAdapterListener listener;
    private boolean showButton;

    @Override
    public ParticipantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participant_item, parent, false);

        return new ParticipantAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParticipantAdapter.MyViewHolder holder, int position) {
        holder.bind(participantLists.get(position), position);
    }

    @Override
    public int getItemCount() {
        return participantLists.size();
    }

    public ParticipantAdapter(List<ParticipantAdapterModel> eventList, Context context, ParticipantAdapterListener listener, boolean showButton) {
        this.participantLists = eventList;
        this.ctx = context;
        this.listener = listener;
        this.showButton = showButton;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView fullName;
        private ImageView icon, statusChecked;
        private RelativeLayout layout;

        public MyViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.user_icon);
            fullName = view.findViewById(R.id.user_name);
            statusChecked = view.findViewById(R.id.check_btn_img);
            layout = view.findViewById(R.id.base_of_card);
        }

        public void bind(final ParticipantAdapterModel participantAdapterModel, final int position) {
            Glide.with(ctx).load(R.drawable.img_event).apply(RequestOptions.circleCropTransform()).into(icon);
            fullName.setText(participantAdapterModel.getFullName());

            if (participantAdapterModel.isStatus()) {
                Glide.with(ctx).load(R.drawable.checked_part).into(statusChecked);
            } else {
                Glide.with(ctx).load((R.drawable.unchecked_part)).into(statusChecked);
            }

            if(showButton){
                statusChecked.setVisibility(View.VISIBLE);
            }
            else {
                statusChecked.setVisibility(View.GONE);
                //statusChecked.setVisibility(View.INVISIBLE);
                //statusChecked.setClickable(false);
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    participantAdapterModel.setStatus(!participantAdapterModel.isStatus());
                    notifyItemChanged(position);
                    listener.onEventClick(participantAdapterModel);
                }
            });
        }
    }
}
