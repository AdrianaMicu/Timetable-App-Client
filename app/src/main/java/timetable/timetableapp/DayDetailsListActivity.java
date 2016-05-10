package timetable.timetableapp;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import timetable.utils.backend.ApplicationProperties;
import timetable.utils.backend.WSAsyncTaskCallback;
import timetable.utils.backend.WebServiceAsyncTask;
import timetable.utils.ui.CoursesHolder;
import timetable.utils.ui.TimetableArrayAdapterDayDetails;

/**
 * Created by adriana on 02/11/14.
 */
public class DayDetailsListActivity extends ListActivity implements WSAsyncTaskCallback {

    public static final String SELECTED_DAY = "dayselected";

    private ApplicationProperties applicationProperties;
    private Context context;
    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daydetailslist);

        context = this;
        applicationProperties = new ApplicationProperties(context);
        properties = applicationProperties.getProperties("application.properties");

        SharedPreferences prefs = getSharedPreferences("userPreferences", MODE_PRIVATE);

        WebServiceAsyncTask wstg = new WebServiceAsyncTask(WebServiceAsyncTask.GET_TASK,
                this, this);
        wstg.execute(new String[]{properties.getProperty("url") + "/daydetails/getCoursesAndHours?day=" + getIntent().getExtras().get(SELECTED_DAY) + "&androidID=" + prefs.getString("androidID", null)});
    }

    private List<CoursesHolder> getDayDetails(String response) {

        List<CoursesHolder> daysList = new ArrayList<CoursesHolder>();

        JSONObject jso = null;
        try {
            jso = new JSONObject(response);
            Iterator i = jso.keys();
            while (i.hasNext()) {
                String course = i.next().toString();
                daysList.add(new CoursesHolder(course, jso.get(course).toString()));
                i.remove();
            }
        } catch (JSONException e) {
            System.out.println();
        }

        return daysList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daydetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_add_daydetails:
                // custom dialog
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_add_daydetails);
                dialog.setTitle("Title...");

                TextView labelCourseName = (TextView) dialog.findViewById(R.id.textViewLabel1);
                labelCourseName.setText("Course name: ");

                TextView labelCourseHour = (TextView) dialog.findViewById(R.id.textViewLabel2);
                labelCourseHour.setText("Course hour: ");

                TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                timePicker.setCurrentHour(new Integer(3));
                timePicker.setCurrentMinute(new Integer(50));

                EditText editText = (EditText) dialog.findViewById(R.id.editText);
                editText.setText("sdjhajsbdascabscaksca cabjscbas chjavcan bcha cas bcka cascjkabc ancnas can");

                Button dialogButton = (Button) dialog.findViewById(R.id.button1);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void didGetResponse(String response) {
        TimetableArrayAdapterDayDetails adapter = new TimetableArrayAdapterDayDetails(this, getDayDetails(response));
        setListAdapter(adapter);
    }
}
