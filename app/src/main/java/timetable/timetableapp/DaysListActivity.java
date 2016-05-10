package timetable.timetableapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import timetable.utils.ui.TimetableArrayAdapterDays;

/**
 * Created by adriana on 01/11/14.
 */
public class DaysListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayslist);
        TimetableArrayAdapterDays adapter = new TimetableArrayAdapterDays(this, getDays());
        setListAdapter(adapter);
    }

    private List<String> getDays() {
        List<String> daysList = new ArrayList<String>();
        daysList.add("Monday");
        daysList.add("Tuesday");
        daysList.add("Wednesday");
        daysList.add("Thursday");
        daysList.add("Friday");
        daysList.add("Saturday");
        daysList.add("Sunday");

        return daysList;
    }
}
