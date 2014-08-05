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
                String data = new String(entry.data, "UTF-8");
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
                moment.setMomentTitle(momentObj.getString("title"));
                moment.setMomentDesc(momentObj.getString("description"));

                JSONObject userObj = momentObj.getJSONObject("user");
                moment.setUserName(userObj.getString("username"));
                moment.setProfilePicURL(userObj.getString("avatar"));
                moment.setBackgroundURL(userObj.getString("background"));

                JSONArray momentItems = momentObj.getJSONArray("moment_items");
                for(int x = 0; x < momentItems.length(); x++){
                    JSONObject itemObj = (JSONObject) momentItems.get(x);

                    if(itemObj.getString("item_type").equalsIgnoreCase("photo")){
                        moment.setPhotoCount(moment.getPhotoCount()+ 1);
                    } else if(itemObj.getString("item_type").equalsIgnoreCase("audio")){
                        moment.setAudioCount(moment.getAudioCount() + 1);
                    } else if (itemObj.getString("item_type").equalsIgnoreCase("video")){
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
        getMenuInflater().inflate(R.menu.main, menu);
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
