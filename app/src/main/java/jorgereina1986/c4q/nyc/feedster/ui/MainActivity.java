package jorgereina1986.c4q.nyc.feedster.ui;

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

import java.util.ArrayList;
import java.util.List;

import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.adapters.FeedCardsAdapter;
import jorgereina1986.c4q.nyc.feedster.loaders.WeatherNewsLoader;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.WeatherData;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "My_Logcat";
    private WeatherNewsLoader weatherLoader;
    private WeatherData weatherData;
    private FeedCardsAdapter feedCardsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing instance variables
        weatherData = new WeatherData();
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
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("weatherDataReady"));//"weatherDataReady"

        //create the Async task to trending data from the API. When it finishes, it will send us a notification.
        weatherLoader = new WeatherNewsLoader(this);
        weatherLoader.execute();
    }


    // handler for received Intents for the "trendingDataReady" event
    //mWeatherReceiver.....
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) { //message coming from ASYNC task loader
            weatherData = weatherLoader.getmWeatherData();
            //next line is sending data to the Adapter
            feedCardsAdapter.setWeatherCardData(weatherData);
            feedCardsAdapter.notifyDataSetChanged(); //asking Adapter to refresh the UI
        }
    };


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
}
