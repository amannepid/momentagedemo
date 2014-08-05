package com.momentage.momentagedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.momentage.momentagedemo.adapter.MomentListAdapter;
import com.momentage.momentagedemo.data.Moment;
import com.momentage.momentagedemo.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private ListView listView;
    private ArrayList<Moment> momentList;
    private MomentListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lvMomentList);

        momentList = new ArrayList<Moment>();

        listAdapter = new MomentListAdapter(this, momentList);
        listView.setAdapter(listAdapter);

        Cache cache = MomentageApplication.getAppInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Constants.DATA_URL);

        if (entry != null) {
            try {
                String data = new String(entry.data, Constants.UTF_8_FILE_FORMAT);
                try {
                    parseMomentJSON(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    Constants.DATA_URL, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(MomentageApplication.TAG,
                            "Response: " + response.toString());
                    if (response != null) {
                        parseMomentJSON(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(MomentageApplication.TAG, "Error: " + error.getMessage());
                }
            });

            MomentageApplication.getAppInstance().addToRequestQueue(jsonReq);
        }

    }

    private void parseMomentJSON(JSONObject response) {
        try {
            JSONArray momentsArray = response.getJSONArray("moments");

            for (int i = 0; i < momentsArray.length(); i++) {
                JSONObject momentObj = (JSONObject) momentsArray.get(i);

                Moment moment = new Moment();
                moment.setMomentTitle(momentObj.getString(Constants.M_TITLE));
                moment.setMomentDesc(momentObj.getString(Constants.M_DESCRIPTION));

                JSONObject userObj = momentObj.getJSONObject(Constants.M_USER);
                moment.setUserName(userObj.getString(Constants.M_USERNAME));
                moment.setProfilePicURL(userObj.getString(Constants.M_AVATAR));
                moment.setBackgroundURL(userObj.getString(Constants.M_BACKGROUND));

                JSONArray momentItems = momentObj.getJSONArray(Constants.M_ITEMS);
                for (int x = 0; x < momentItems.length(); x++) {
                    JSONObject itemObj = (JSONObject) momentItems.get(x);

                    if (itemObj.getString(Constants.M_ITEM_TYPE).equalsIgnoreCase(Constants.M_ITEM_TYPE_PHOTO)) {
                        moment.setPhotoCount(moment.getPhotoCount() + 1);
                    } else if (itemObj.getString(Constants.M_ITEM_TYPE).equalsIgnoreCase(Constants.M_ITEM_TYPE_AUDIO)) {
                        moment.setAudioCount(moment.getAudioCount() + 1);
                    } else if (itemObj.getString(Constants.M_ITEM_TYPE).equalsIgnoreCase(Constants.M_ITEM_TYPE_VIDEO)) {
                        moment.setVideoCount(moment.getVideoCount() + 1);
                    }
                }

                momentList.add(moment);
            }

            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
