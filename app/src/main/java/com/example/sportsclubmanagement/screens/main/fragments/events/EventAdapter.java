package com.example.sportsclubmanagement.screens.main.fragments.events;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;

import java.text.SimpleDateFormat;
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
        private ConstraintLayout layout;
        private Button upBtn;
        private Button downBtn;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.card_img_event);
            title = (TextView) view.findViewById(R.id.card_title);
            loc = (TextView) view.findViewById(R.id.card_location);
            data = (TextView) view.findViewById(R.id.card_date);
            layout = view.findViewById(R.id.rootLayout);
            upBtn = view.findViewById(R.id.join_event_btn_up);
            downBtn = view.findViewById(R.id.joinEvent_btn_down);
        }

        public void bind(final EventAdapterModel eventAdapterModel) {
            Glide.with(ctx).load(R.drawable.img_event).centerCrop().into(img);
            title.setText(eventAdapterModel.getTitle());
            loc.setText(eventAdapterModel.getLocation());
            SimpleDateFormat DateFor = new SimpleDateFormat("dd.MM.yyyy");
            data.setText(DateFor.format(eventAdapterModel.getDataEvent()));
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventClick(eventAdapterModel);
                }
            });
            downBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventJoinClick(eventAdapterModel);
                }
            });
            upBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventJoinClick(eventAdapterModel);
                }
            });
            if (eventAdapterModel.isVisibleBtn()) {
                if (eventAdapterModel.isUpPositionBtn()) {
                    downBtn.setVisibility(View.GONE);
                    img.requestLayout();
                } else {
                    upBtn.setVisibility(View.GONE);
                }
            } else {
                downBtn.setVisibility(View.GONE);
                upBtn.setVisibility(View.GONE);
            }
            if (eventAdapterModel.isBig()) {
                img.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, ctx.getResources().getDisplayMetrics());
                img.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, ctx.getResources().getDisplayMetrics());
            }
        }
    }
}
