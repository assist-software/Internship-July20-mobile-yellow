package com.example.sportsclubmanagement.screens.main.fragments.events;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.EventAdapterModel;
import com.example.sportsclubmanagement.models.apiModels.Response.Events;
import com.example.sportsclubmanagement.rest.APIClient;
import com.example.sportsclubmanagement.rest.APIInterface;
import com.example.sportsclubmanagement.screens.eventdetails.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements EventAdapterListener {
    RecyclerView eventRecycler,joinedRecycler,pendingRecycler;
    EventAdapter eventAdapter,joinedAdapter,pendingAdapter;
    APIInterface apiInterface;
    List<EventAdapterModel> eventList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        Call<List<Events>> call = apiInterface.getAllEvents();
//        call.enqueue(new Callback<List<Events>>() {
//            @Override
//            public void onResponse(Call<List<Events>> call, Response<List<Events>> response) {
//                if(response.isSuccessful()){
////                    eventList = response.body();
////                    String content = "";
////                    for(EventAdapterModel eventAdapterModel: eventList){
////                        content += "ID: " + eventAdapterModel.getClub_id() + "\n";
////                        content += "Title: " + eventAdapterModel.getDescription();
////                    }
////                    Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<EventAdapterModel>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error in getEvents call", Toast.LENGTH_SHORT).show();
//            }
//        });

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

//    private List<Events> getEventsList(){
//        List<Events> eventsList = new ArrayList<>();
//        Call<Void> call = apiInterface.get_events(club_id,last,email.getText().toString(),pass.getText().toString());
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Log.d("TAG", response.code() + "");
//                } else {
//                    Log.d("error message", response.message());
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
//                call.cancel();
//            }
//        });
//
//        return eventsList;
//    }
}