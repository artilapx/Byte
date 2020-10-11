package org.artilapx.bytepsec.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import org.artilapx.bytepsec.R;

public class WeekdayFragment extends Fragment {

    Bundle mondayBundle = new Bundle();
    Bundle tuesdayBundle = new Bundle();
    Bundle wednesdayBundle = new Bundle();
    Bundle thursdayBundle = new Bundle();
    Bundle fridayBundle = new Bundle();
    Bundle saturdayBundle = new Bundle();

    public static WeekdayFragment newInstance() {
        return new WeekdayFragment();
    }

    Comparator<String> dateComparator = (s1, s2) -> {
        try{
            SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.ENGLISH);
            Date d1 = format.parse(s1);
            Date d2 = format.parse(s2);
            if(d1.equals(d2)){
                return s1.substring(s1.indexOf(" ") + 1).compareTo(s2.substring(s2.indexOf(" ") + 1));
            }else{
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(d1);
                cal2.setTime(d2);
                return cal1.get(Calendar.DAY_OF_WEEK) - cal2.get(Calendar.DAY_OF_WEEK);
            }
        }catch(ParseException pe){
            throw new RuntimeException(pe);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_weekday, container, false);
    }
}
