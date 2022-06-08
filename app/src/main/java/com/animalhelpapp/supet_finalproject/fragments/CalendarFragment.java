package com.animalhelpapp.supet_finalproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.animalhelpapp.supet_finalproject.R;

import java.util.Calendar;

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        Button cal_btn = view.findViewById(R.id.calendar_btn);

        cal_btn.setOnClickListener(v -> guardarEvent());


        return view;
    }

    private void guardarEvent() {
        Calendar calendarEvent = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", calendarEvent.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rule", "FREQ=YEARLY");
        intent.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        intent.putExtra("title", "Calendar Event");
        startActivity(intent);
    }
}