package com.example.sportsclubmanagement.screens.main.fragments.events;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    private List<EventAdapterModel> getMockedList() {
        List<EventAdapterModel> mocks = new ArrayList<>();

        return mocks;
    }
}