package jorgereina1986.c4q.nyc.feedster.ui.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jorgereina1986.c4q.nyc.feedster.AppController;
import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.adapters.FeedCardsAdapter;
import jorgereina1986.c4q.nyc.feedster.helpers.Constants;
import jorgereina1986.c4q.nyc.feedster.loaders.TrendingNewsLoader;
import jorgereina1986.c4q.nyc.feedster.loaders.WeatherNewsLoader;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.MusicData;
import jorgereina1986.c4q.nyc.feedster.models.MusicItemData;
import jorgereina1986.c4q.nyc.feedster.models.ToDoData;
import jorgereina1986.c4q.nyc.feedster.models.TrendingData;
import jorgereina1986.c4q.nyc.feedster.models.WeatherData;

/**
 * Created by c4q-jorgereina on 7/27/15.
 */
public class FragmentLeft extends Fragment {

    public static final String TAG = "FragmentLeft";
    private TrendingNewsLoader trendsLoader;
    private List<String> trendsList;
    private FeedCardsAdapter feedCardsAdapter;
    private TrendingData trendingData;
    private WeatherNewsLoader weatherLoader;
    private WeatherData weatherData;
    private List<MusicItemData> musicList = new ArrayList<>();
    private MusicData musicData;
    private ToDoData toDoData;

    List<String> toDoItemsList;
    ArrayAdapter<String> toDoAdapter;

    private static final String MUSIC_API_URL = "https://itunes.apple.com/us/rss/topsongs/limit=10/explicit=true/json";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mTrendingMessageReceiver,
                new IntentFilter("trendingDataReady"));

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mWeatherMessageReceiver,
                new IntentFilter("weatherDataReady"));//"weatherDataReady"

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_left, container, false);

        // code moved from MainActivity' onCreate

        //initializing instance variables
        trendingData = new TrendingData();
        weatherData = new WeatherData();
        musicData = new MusicData();
        toDoData = new ToDoData();

        //the next block locates the RecyclerView in the layout, creates the Adapter, & associates the Adapter to the RecyclerView
        RecyclerView rvFeedCards = (RecyclerView) fragmentView.findViewById(R.id.rv_feed_cards);

        rvFeedCards.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvFeedCards.setLayoutManager(llm);
        feedCardsAdapter = new FeedCardsAdapter(getActivity()); //creating the adapter

        reloadToDoSet();
        toDoData.setToDoList(toDoItemsList);

        List<CardData> cardDataList = new ArrayList<>();   //  TestData.getTestData();
        cardDataList.add(toDoData);
        feedCardsAdapter.setCardDataList(cardDataList);
        feedCardsAdapter.setToDoCardData(toDoData);

        rvFeedCards.setAdapter(feedCardsAdapter);  //tying the adapter to the recycler view

        //create the Async task to trending data from the API. When it finishes, it will send us a notification.
        trendsLoader = new TrendingNewsLoader(getActivity());
        trendsLoader.execute();

        //create the Async task to trending data from the API. When it finishes, it will send us a notification.
        weatherLoader = new WeatherNewsLoader(getActivity());
        weatherLoader.execute();

        // Creating volley request obj
        Response.Listener<JSONObject> volleyCallbackListener = getResponseListener();
        Response.ErrorListener volleyErrorListener = getResponseErrorListener();

        JsonObjectRequest movieReq = new JsonObjectRequest(MUSIC_API_URL, null, volleyCallbackListener, volleyErrorListener);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

        return fragmentView;
    }


    private void reloadToDoSet() {
        SharedPreferences thePrefs = getActivity().getSharedPreferences(Constants.MY_APP_PREFS, 0);
        Set<String> toDoItemsSet = new HashSet<String>();
        toDoItemsSet = thePrefs.getStringSet(Constants.TO_DO_ITEMS_SET, toDoItemsSet);
        toDoItemsList = new ArrayList<String>(toDoItemsSet);
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
    public void onDestroy() {
        super.onDestroy();
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mWeatherMessageReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mTrendingMessageReceiver);

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
