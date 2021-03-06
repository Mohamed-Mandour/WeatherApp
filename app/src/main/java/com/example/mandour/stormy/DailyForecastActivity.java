package com.example.mandour.stormy;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.mandour.stormy.adapters.DayAdapter;
import com.example.mandour.stormy.weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {


    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);


        Intent intent = getIntent();

        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DALIY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter dayAdapter = new DayAdapter(this, mDays);
        setListAdapter(dayAdapter);

    }
}