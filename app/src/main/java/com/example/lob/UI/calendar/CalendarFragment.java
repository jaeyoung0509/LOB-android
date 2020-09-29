package com.example.lob.UI.calendar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lob.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private CalendarViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
      final   MaterialCalendarView cal_food =  root.findViewById(R.id.cal_food);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                cal_food.state().edit()
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setMinimumDate(CalendarDay.from(1990,0,1))
                        .setMaximumDate(CalendarDay.from(2050,0,1))
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();


            }
        });
        return root;
    }

}
