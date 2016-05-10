package timetable.utils.ui;

/**
 * Created by adriana on 01/11/14.
 */
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import timetable.timetableapp.DayDetailsListActivity;
import timetable.timetableapp.R;

public class TimetableArrayAdapterDays extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> days;
    private int color;

    public TimetableArrayAdapterDays(Context context, List<String> days) {
        super(context, R.layout.listitem_dayslist, days);
        this.context = context;
        this.days = days;
        color = context.getResources().getColor(android.R.color.holo_blue_bright);
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
        public TextView day;
        //public ImageView i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = null;

        if (convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listitem_dayslist, parent, false);

            final TextView textViewDay = (TextView) rowView.findViewById(R.id.textViewDay);
            //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            ViewHolder holder = new ViewHolder();
            holder.day = textViewDay;
            //holder.i = imageView;
            rowView.setTag(holder);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, textViewDay.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, DayDetailsListActivity.class);
                    intent.putExtra(DayDetailsListActivity.SELECTED_DAY, position);
                    context.startActivity(intent);
                }
            });
        }
        ViewHolder tag = (ViewHolder) rowView.getTag();

        tag.day.setText(days.get(position));

//        if (values.get(position).isSelected()) {
//            tag.i.setImageResource(R.drawable.ok);
//        } else {
//            tag.i.setImageResource(R.drawable.no);
//        }

        rowView.setBackgroundColor(color);
        return rowView;
    }
}
