package timetable.utils.backend;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by adriana on 01/11/14.
 */
public class WebServiceAsyncTask extends AsyncTask<String, Integer, String> {

    public WSAsyncTaskCallback wsCallback;

    public static final int POST_TASK = 1;
    public static final int GET_TASK = 2;

    // connection timeout, in milliseconds (waiting to connect)
    private static final int CONN_TIMEOUT = 3000;

    // socket timeout, in milliseconds (waiting for data)
    private static final int SOCKET_TIMEOUT = 5000;

    private int taskType = GET_TASK;

    private Context context = null;

    private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

    public WebServiceAsyncTask(int taskType, Context context, WSAsyncTaskCallback wsCallback) {
        this.taskType = taskType;
        this.context = context;
        this.wsCallback = wsCallback;
    }

    public void addNameValuePair(String name, String value) {

        params.add(new BasicNameValuePair(name, value));
    }

    private HttpParams getHttpParams() {

        HttpParams http = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(http, CONN_TIMEOUT);
        HttpConnectionParams.setSoTimeout(http, SOCKET_TIMEOUT);

        return http;
    }

    private HttpResponse doResponse(String url) {
        HttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpResponse response = null;

        try {
            switch (taskType) {

                case POST_TASK:
                    HttpPost httppost = new HttpPost(url);

                    httppost.setEntity(new UrlEncodedFormEntity(params));

                    response = httpclient.execute(httppost);
                    break;
                case GET_TASK:
                    HttpGet httpget = new HttpGet(url);
                    response = httpclient.execute(httpget);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            while ((line = br.readLine()) != null) {
                total.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total.toString();
    }

    @Override
    protected String doInBackground(String... urls) {

        String url = urls[0];
        String result = "";

        HttpResponse response = doResponse(url);

        if (response == null) {
            return result;
        } else {
            try {
                result = inputStreamToString(response.getEntity()
                        .getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String response) {
        wsCallback.didGetResponse(response);
    }
}
