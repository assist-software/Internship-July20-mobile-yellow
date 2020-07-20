package com.example.sportsclubmanagement.screens.main.fragments.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<EventAdapterModel> eventLists;
    private Context ctx;
    private EventAdapterListener listener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_layout_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(eventLists.get(position));
    }

    @Override
    public int getItemCount() {
        return eventLists.size();
    }

    public EventAdapter(List<EventAdapterModel> eventList, Context context, EventAdapterListener listener) {
        this.eventLists = eventList;
        this.ctx = context;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, loc, data;
        private ImageView img;
        private LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.card_img_event);
            title = (TextView) view.findViewById(R.id.card_title);
            loc = (TextView) view.findViewById(R.id.card_location);
            data = (TextView) view.findViewById(R.id.card_date);
            layout = view.findViewById(R.id.linear_card);
        }

        public void bind(final EventAdapterModel eventAdapterModel) {
            Glide.with(ctx).load(R.drawable.img_event).centerCrop().into(img);
            title.setText(eventAdapterModel.getTitle());
            loc.setText(eventAdapterModel.getLocation());
            data.setText(eventAdapterModel.getDataEvent());
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventClick(eventAdapterModel);
                }
            });
        }
    }
}
