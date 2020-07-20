package com.example.sportsclubmanagement.screens.main.fragments.events;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.screens.eventdetails.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements EventAdapterListener {
    RecyclerView eventRecycler,joinedRecycler,pendingRecycler;
    EventAdapter eventAdapter,joinedAdapter,pendingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        eventRecycler = rootView.findViewById(R.id.eventRecycler);
        eventAdapter = new EventAdapter(getMockedList(), this.getContext(), this);
        eventRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        eventRecycler.setAdapter(eventAdapter);

        joinedRecycler = rootView.findViewById(R.id.joinedRecycler);
        joinedAdapter = new EventAdapter(getMockedList(), this.getContext(), this);
        joinedRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        joinedRecycler.setAdapter(joinedAdapter);

        pendingRecycler = rootView.findViewById(R.id.pendingRecycler);
        pendingAdapter = new EventAdapter(getMockedList(), this.getContext(), this);
        pendingRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL,false));
        pendingRecycler.setAdapter(pendingAdapter);
        return rootView;
    }

    @Override
    public void onEventClick(EventAdapterModel event) {
        String event_id = "test id";
        Intent i = new Intent(getContext(), EventActivity.class);
        i.putExtra("event_id",event_id);
        startActivity(i);
    }

    private List<EventAdapterModel> getMockedList() {
        List<EventAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new EventAdapterModel("title test", "loc test", "12.06.1998"));
        }
        return mocks;
    }
}