package com.example.sportsclubmanagement.screens.main.fragments.events;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventsFragment extends Fragment implements EventAdapterListener {
    RecyclerView eventRecycler;
    EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        eventRecycler = rootView.findViewById(R.id.eventRecycler);
        eventAdapter = new EventAdapter(getMockedList(), this.getContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        eventRecycler.setLayoutManager(mLayoutManager);
        eventRecycler.setAdapter(eventAdapter);

        return rootView;
    }

    @Override
    public void onEventClick(EventAdapterModel event) {
        Toast.makeText(this.getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void initComp() {

    }

    private List<EventAdapterModel> getMockedList() {
        List<EventAdapterModel> mocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mocks.add(new EventAdapterModel("title test", "loc test", "12.06.1998"));
        }
        return mocks;
    }
}