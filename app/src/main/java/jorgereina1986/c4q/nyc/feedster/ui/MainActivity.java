package jorgereina1986.c4q.nyc.feedster.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jorgereina1986.c4q.nyc.feedster.AppController;
import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.adapters.FeedCardsAdapter;
import jorgereina1986.c4q.nyc.feedster.loaders.TrendingNewsLoader;
import jorgereina1986.c4q.nyc.feedster.loaders.WeatherNewsLoader;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.MusicData;
import jorgereina1986.c4q.nyc.feedster.models.MusicItemData;
import jorgereina1986.c4q.nyc.feedster.models.TrendingData;
import jorgereina1986.c4q.nyc.feedster.models.WeatherData;

public class MainActivity extends Activity {

    public static final String TAG = "My_Logcat";
    private TrendingNewsLoader trendsLoader;
    private List<String> trendsList;
    private FeedCardsAdapter feedCardsAdapter;
    private TrendingData trendingData;
    private WeatherNewsLoader weatherLoader;
    private WeatherData weatherData;
    private List<MusicItemData> musicList = new ArrayList<>();
    private MusicData musicData;

    private static final String MUSIC_API_URL = "https://itunes.apple.com/us/rss/topsongs/limit=10/explicit=true/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing instance variables
        trendingData = new TrendingData();
        weatherData = new WeatherData();
        musicData = new MusicData();

        List<CardData> cardDataList = new ArrayList<>();   //  TestData.getTestData();

        //the next block locates the RecyclerView in the layout, creates the Adapter, & associates the Adapter to the RecyclerView
        RecyclerView rvFeedCards = (RecyclerView) findViewById(R.id.rv_feed_cards);

        rvFeedCards.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvFeedCards.setLayoutManager(llm);
        feedCardsAdapter = new FeedCardsAdapter(); //creating the adapter
        feedCardsAdapter.setCardDataList(cardDataList);
        rvFeedCards.setAdapter(feedCardsAdapter);  //tying the adapter to the recycler view

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mTrendingMessageReceiver,
                new IntentFilter("trendingDataReady"));

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mWeatherMessageReceiver,
                new IntentFilter("weatherDataReady"));//"weatherDataReady"

        //create the Async task to trending data from the API. When it finishes, it will send us a notification.
        trendsLoader = new TrendingNewsLoader(this);
        trendsLoader.execute();

        //create the Async task to trending data from the API. When it finishes, it will send us a notification.
        weatherLoader = new WeatherNewsLoader(this);
        weatherLoader.execute();

        // Creating volley request obj
        Response.Listener<JSONObject> volleyCallbackListener = getResponseListener();
        Response.ErrorListener volleyErrorListener = getResponseErrorListener();

        JsonObjectRequest movieReq = new JsonObjectRequest(MUSIC_API_URL, null, volleyCallbackListener, volleyErrorListener);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }



    // handler for received Intents for the "trendingDataReady" event
    private BroadcastReceiver mTrendingMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) { //message coming from ASYNC task loader
            trendsList = trendsLoader.getTrendingListData();
            Log.d("Receiver", "Received trendsList " + trendsList.size());
            //next 2 lines are sending data to the Adapter
            trendingData.setTrendingItems(trendsList);
            feedCardsAdapter.setTrendingCardData(trendingData);
            feedCardsAdapter.notifyDataSetChanged(); //asking Adapter to refresh the UI
        }
    };

    // handler for received Intents for the "trendingDataReady" event
    //mWeatherReceiver.....
    private BroadcastReceiver mWeatherMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) { //message coming from ASYNC task loader
            weatherData = weatherLoader.getmWeatherData();
            //next line is sending data to the Adapter
            feedCardsAdapter.setWeatherCardData(weatherData);
            feedCardsAdapter.notifyDataSetChanged(); //asking Adapter to refresh the UI
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mWeatherMessageReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mTrendingMessageReceiver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Response.Listener<JSONObject> getResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                /// Parsing json
                try {
                    JSONObject feedObject = response.getJSONObject("feed");

                    Iterator iterator = feedObject.keys();
                    while(iterator.hasNext()) {
                        Log.i("debug", "feedObject element: " + iterator.next());
                    }

                    JSONArray entryArray =  feedObject.getJSONArray("entry");
                    Log.i("debug", "entryarray length is: " + entryArray.length());


                    for(int i = 0; i < entryArray.length(); i++) {

                        //get Tracks
                        JSONObject item = entryArray.getJSONObject(i);

                        //getting Title
                        JSONObject title = item.getJSONObject("im:name");
                        String trackTitle = title.getString("label");


                        //getting Artist
                        JSONObject artist = item.getJSONObject("im:artist");
                        String trackArtist = artist.getString("label");

                        //getting Image
                        JSONArray imgArray = item.getJSONArray("im:image");
                        JSONObject imgObject = imgArray.getJSONObject(1)    ;
                        String imgUrl = imgObject.getString("label");

                        MusicItemData music = new MusicItemData();
                        music.setTitle(trackTitle);
                        music.setThumbnailUrl(imgUrl);
                        music.setArtist(trackArtist);

                        // adding movie to movies array
                        musicList.add(music);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                musicData.setMusicItemDataList(musicList);
                feedCardsAdapter.setMusicCardData(musicData);
                feedCardsAdapter.notifyDataSetChanged();
            }
        };
    }

    private Response.ErrorListener getResponseErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        };
    }

}