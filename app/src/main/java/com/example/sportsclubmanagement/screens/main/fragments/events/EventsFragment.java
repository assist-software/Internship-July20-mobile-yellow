package com.example.sportsclubmanagement.screens.main.fragments.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.EventDetails;
import com.example.sportsclubmanagement.models.apiModels.Response.EventMainInfo;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.eventdetails.EventActivity;
import com.example.sportsclubmanagement.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class EventsFragment extends Fragment implements EventAdapterListener {
    private RecyclerView eventRecycler, joinedRecycler, pendingRecycler;
    private EventAdapter eventAdapter, joinedAdapter, pendingAdapter;
    private SharedPreferences pref;
    private APIInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        initComponents(rootView);
        getListEvents();
        return rootView;
    }

    private void getListEvents() {
        Call<List<EventMainInfo>> call = apiInterface.getUsersEvents(pref.getString(Constants.TOKEN,null));
        call.enqueue(new Callback<List<EventMainInfo>>() {
            @Override
            public void onResponse(Call<List<EventMainInfo>> call, Response<List<EventMainInfo>> response) {
                List<EventMainInfo> lst = response.body();
                if(lst!=null){
                    Date today = new Date();
                    List<EventAdapterModel> pastEvents = new ArrayList<>();
                    List<EventAdapterModel> futureEvents = new ArrayList<>();
                    List<EventAdapterModel> allEvents = new ArrayList<>();
                    for(EventMainInfo ev : lst){
                        if(ev.getDate().before(today)){
                            pastEvents.add(new EventAdapterModel(ev.getId(),ev.getTitle(),ev.getLocatia(),ev.getDate(),false,false,false));
                        }else{
                            futureEvents.add(new EventAdapterModel(ev.getId(),ev.getTitle(),ev.getLocatia(),ev.getDate(),false,false,true));
                        }
                        allEvents.add(new EventAdapterModel(ev.getId(),ev.getTitle(),ev.getLocatia(),ev.getDate(),false,false,false));
                    }
                    initEventsHAdapter(pastEvents, joinedRecycler);
                    initEventsHAdapter(allEvents, eventRecycler);
                    initEventsVAdapter(futureEvents, pendingRecycler);
                }
            }

            @Override
            public void onFailure(Call<List<EventMainInfo>> call, Throwable t) {

            }
        });

    }

    private void initComponents(View rootView) {
        pref = rootView.getContext().getSharedPreferences(Constants.TOKEN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        //recyclers
        eventRecycler = rootView.findViewById(R.id.eventRecycler);
        joinedRecycler = rootView.findViewById(R.id.joinedRecycler);
        pendingRecycler = rootView.findViewById(R.id.pendingRecycler);
    }

    @Override
    public void onEventClick(EventAdapterModel event) {
        Intent i = new Intent(getContext(), EventActivity.class);
        i.putExtra("eventId", event.getId());
        startActivity(i);
    }

    @Override
    public void onEventJoinClick(EventAdapterModel event) {
    }

    private void initEventsHAdapter(List<EventAdapterModel> list, RecyclerView recyclerView) {
        EventAdapter adapter = new EventAdapter(list, this.getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(EventsFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void initEventsVAdapter(List<EventAdapterModel> list, RecyclerView recyclerView) {
        EventAdapter adapter = new EventAdapter(list, this.getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(EventsFragment.this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}