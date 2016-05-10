package timetable.utils.ui;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import timetable.timetableapp.DayDetailsListActivity;
import timetable.timetableapp.R;

/**
 * Created by adriana on 02/11/14.
 */
public class TimetableArrayAdapterDayDetails extends  ArrayAdapter<CoursesHolder>  {

    private final Context context;
    private final List<CoursesHolder> courseNamesAndHours;

    public TimetableArrayAdapterDayDetails(Context context, List<CoursesHolder> courseNamesAndHours) {
        super(context, R.layout.listitem_daydetailslist, courseNamesAndHours);
        this.context = context;
        this.courseNamesAndHours = courseNamesAndHours;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    public static class ViewHolder {
        public TextView courseName;
        public TextView hour;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;

        if (convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listitem_daydetailslist, parent, false);

            final TextView textViewDayDetails = (TextView) rowView.findViewById(R.id.textViewDayDetails);
            TextView textViewHour = (TextView) rowView.findViewById(R.id.textViewHour);
            ViewHolder holder = new ViewHolder();
            holder.courseName = textViewDayDetails;
            holder.hour = textViewHour;
            rowView.setTag(holder);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        ViewHolder tag = (ViewHolder) rowView.getTag();

        tag.courseName.setText(courseNamesAndHours.get(position).getCourseName());
        tag.hour.setText(courseNamesAndHours.get(position).getHour());

        return rowView;
    }
}
