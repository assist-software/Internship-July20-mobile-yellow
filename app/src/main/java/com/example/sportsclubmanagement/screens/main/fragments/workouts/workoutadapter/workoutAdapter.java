package com.example.sportsclubmanagement.screens.main.fragments.workouts.workoutadapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagement.R;
import com.example.sportsclubmanagement.models.WorkoutAdapterModel;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class workoutAdapter extends RecyclerView.Adapter<workoutAdapter.WorkoutViewHolder> {
    private List<WorkoutAdapterModel> workoutLists;
    private Context ctx;

    @NonNull
    @Override
    public workoutAdapter.WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_item, parent, false);

        return new WorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull workoutAdapter.WorkoutViewHolder holder, int position) {
        holder.bind(workoutLists.get(position));
    }

    @Override
    public int getItemCount() {
        return workoutLists.size();
    }

    public workoutAdapter(List<WorkoutAdapterModel> workoutLists, Context context) {
        this.workoutLists = workoutLists;
        this.ctx = context;
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private TextView dayTxt, monthTxt, yearTxt, dayOfWeekTxt;
        private TextView distanceTravelledTxt, caloriesTxt, bpmTxt, time_of_workout_text;

        public WorkoutViewHolder(View view) {
            super(view);
            this.dayTxt = view.findViewById(R.id.text_day);
            Shader textShader = new LinearGradient(0, 0, 0, dayTxt.getHeight(),
                    new int[]{R.color.top_gradient, R.color.bottom_gradient},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
            this.dayTxt.getPaint().setShader(textShader);
            this.monthTxt = view.findViewById(R.id.text_month);
            this.yearTxt = view.findViewById(R.id.text_year);
            this.dayOfWeekTxt = view.findViewById(R.id.day_week);
            this.distanceTravelledTxt = view.findViewById(R.id.distance_travelled);
            this.caloriesTxt = view.findViewById(R.id.calories);
            this.bpmTxt = view.findViewById(R.id.bpm_score);
            this.time_of_workout_text = view.findViewById(R.id.time_passed);
        }

        public void bind(final WorkoutAdapterModel workoutAdapterModel) {
            distanceTravelledTxt.setText(String.format("%.2f", workoutAdapterModel.getDistance_travelled()));
            caloriesTxt.setText(String.format("%.2f", workoutAdapterModel.getCalories()));
            bpmTxt.setText(String.valueOf(workoutAdapterModel.getBpm()));
            int min = workoutAdapterModel.getTime_workout() / 60;
            int sec = workoutAdapterModel.getTime_workout() % 60;
            time_of_workout_text.setText(String.format("%d:%d", min, sec));
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
            calendar.setTime(workoutAdapterModel.getDate_workout());
            DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
            String weekdays[] = dfs.getWeekdays();
            String yearMonths[] = dfs.getMonths();
            dayTxt.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            dayOfWeekTxt.setText(weekdays[calendar.get(Calendar.DAY_OF_WEEK)]);
            yearTxt.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            monthTxt.setText(yearMonths[calendar.get(Calendar.MONTH)]);
        }
    }
}
