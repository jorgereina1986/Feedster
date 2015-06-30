package jorgereina1986.c4q.nyc.feedster.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.adapters.FeedCardsAdapter;
import jorgereina1986.c4q.nyc.feedster.loaders.TrendingNewsLoader;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.TrendingData;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "My_Logcat";
    private TrendingNewsLoader trendsLoader;
    private List<String> trendsList;
    private  FeedCardsAdapter feedCardsAdapter;
    private TrendingData trendingData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvFeedCards = (RecyclerView)findViewById(R.id.rv_feed_cards);
        rvFeedCards.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvFeedCards.setLayoutManager(llm);

        trendingData = new TrendingData();
        feedCardsAdapter = new FeedCardsAdapter();
        List<CardData> cardDataList = new ArrayList<>();   //  TestData.getTestData();
        feedCardsAdapter.setCardDataList(cardDataList);
        rvFeedCards.setAdapter(feedCardsAdapter);
        trendsLoader = new TrendingNewsLoader(this);
        trendsLoader.execute();



    }
    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("trendingDataReady"));
    }
    // handler for received Intents for the "trendingDataReady" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            trendsList = trendsLoader.getTrendingListData();
            Log.d("Receiver", "Received trendsList " + trendsList.size());
            trendingData.setTrendingItems(trendsList);
            feedCardsAdapter.setTrendingCardData(trendingData);
            feedCardsAdapter.notifyDataSetChanged();


        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }






//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
