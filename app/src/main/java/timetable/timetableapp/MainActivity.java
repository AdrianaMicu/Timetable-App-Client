package timetable.timetableapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Properties;

import timetable.utils.backend.ApplicationProperties;
import timetable.utils.backend.WSAsyncTaskCallback;
import timetable.utils.backend.WebServiceAsyncTask;


public class MainActivity extends Activity implements WSAsyncTaskCallback {

    public static String androidID;
    private ApplicationProperties applicationProperties;
    private Context context;
    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("userPreferences", MODE_PRIVATE);
        String restoredText = prefs.getString("userType", null);
        if (restoredText != null) {
            Intent intent = new Intent(this, DaysListActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }

        context = this;
        applicationProperties = new ApplicationProperties(context);
        properties = applicationProperties.getProperties("application.properties");
    }

    public void onClickStudent(View view) {
        setUserType("student");
    }

    public void onClickProfessor(View view) {
        setUserType("professor");
    }

    public void setUserType(String type) {
        SharedPreferences.Editor editor = getSharedPreferences("userPreferences", MODE_PRIVATE).edit();
        editor.putString("userType", type);
        editor.commit();

        androidID = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        editor.putString("androidID", androidID);
        editor.commit();

        // set androidID and userType to DB
        WebServiceAsyncTask wstg = new WebServiceAsyncTask(WebServiceAsyncTask.POST_TASK,
                this, this);
        wstg.execute(new String[]{properties.getProperty("urlSaveUserData") + "?androidID=" + androidID + "&type=" + type});

        Intent intent = new Intent(this, DaysListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleResponse(String response) {

        System.out.print("RESPONSE: " + response);
    }

    @Override
    public void didGetResponse(String response) {
        handleResponse(response);
    }
}

