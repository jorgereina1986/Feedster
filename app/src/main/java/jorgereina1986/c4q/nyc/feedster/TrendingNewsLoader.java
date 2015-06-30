package jorgereina1986.c4q.nyc.feedster;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by c4q-Allison on 6/25/15.
 */


public class TrendingNewsLoader extends AsyncTask<Void, Void, List<String>> {
    private Context mContext;

    List<String> trendingListData = new ArrayList<>();

    public TrendingNewsLoader(Context context) {
        mContext = context;
    }

    public List<String> getTrendingListData() {
        return trendingListData;
    }

    @Override
    protected List<String> doInBackground(Void... params) {

        // TODO : 1. implement this method, get each character's image picture Url from json file then return the list.
        List<String> trendsList = new ArrayList<>();


//        String jsonUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=batman";
        String jsonUrl = "http://hawttrends.appspot.com/api/terms/";

        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            String jsonString = builder.toString();

//            JSONArray array = new JSONArray(jsonString);
            JSONObject topLevelObject = new JSONObject(jsonString);
            JSONArray usTrendDataArray = topLevelObject.getJSONArray("1");

            for (int i = 0; i < usTrendDataArray.length(); i++) {
                String trendingItem = usTrendDataArray.getString(i);
                trendsList.add(trendingItem);
            }
            Log.d("Trends", "Found items = " + trendsList.size());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return trendsList;
    }

    @Override
    protected void onPostExecute(List<String> trendsList) {
        trendingListData = trendsList;
        Intent intent = new Intent("trendingDataReady");
            if (mContext != null) {

                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }

        // TODO : 3. implement this method.

    }
}

//public class TrendingNews {
//
//    private static final String JSON_URL_TRENDS = "http://hawttrends.appspot.com/api/terms/";
//
//    private static String jsonString = "";
//
//    public static void main(String[] args) {
//
//        // TODO : Goal 1 - print entire json string
//        System.out.println(getJsonString());
//
//        // TODO : Goal 2 - print first trends JSON blob (getFirstTrend() method)
//        System.out.println(getFirstTrend());
//
//    }

//    // Goal 1
//    private static String getJsonString() {
//        String result = "";
//
//        //Step 0 - create a completed json url string
//        String jsonUrl = JSON_URL_TRENDS;
//
//        //Step 1 - create an URL instance with Step 0's result
//        try {
//            URL url = new URL(jsonUrl);
//
//            //Step 2 - create a Http Url Connection with Step 1's URL
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(0);
//            connection.setReadTimeout(0);
//
//            //Step 3 - get input stream from Step 2 and create an instance of BufferedReader
//            InputStream inputStream = connection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//            //Step 4 - create a string builder instance
//            StringBuilder stringBuilder = new StringBuilder();
//
//            //Step 5 - read json file until the end of the line and write into string builder and save
//            // it into String variable "result"
//            String line = "";
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line + "\n");
//            }
//            result = stringBuilder.toString();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    // Goal 2
//    private static String getFirstTrend() {
//        String firstTrend = "";
//
//        jsonString = getJsonString();
//        try {
//            JSONObject object = new JSONObject(jsonString);
//            JSONObject main = object.getJSONObject("main");
//            firstTrend = main.getString("");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return firstTrend;
//    }

    // Goal 3
//    private static String getDescription() {
//        String description = "";
//
//        jsonString = getJsonString();
//        try {
//            JSONObject object = new JSONObject(jsonString);
//            JSONArray weather = object.getJSONArray("weather");
//            JSONObject first = (JSONObject) weather.get(0);
//            description = first.getString("description");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return description;
//    }
