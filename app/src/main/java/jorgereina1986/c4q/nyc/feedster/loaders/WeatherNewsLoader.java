package jorgereina1986.c4q.nyc.feedster.loaders;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jorgereina1986.c4q.nyc.feedster.models.WeatherData;


public class WeatherNewsLoader extends AsyncTask<Void, Void, WeatherData> {
    private Context mContext;

    WeatherData mWeatherData = new WeatherData();

    public WeatherNewsLoader(Context context) {
        mContext = context;
    }

    public WeatherData getmWeatherData() {
        return mWeatherData;
    }

    @Override
    protected WeatherData doInBackground(Void... params) {

        String jsonUrl = "https://api.forecast.io/forecast/94f6ec198bf9cbc3021ee4ae49a87d57";
        WeatherData weatherData = new WeatherData();
        //use this variable to load everything from JSON.
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        Location location = lm.getLastKnownLocation(null);
        double longitude = -73.58; //location.getLongitude();
        double latitude = 40.47; //location.getLatitude();

        jsonUrl = jsonUrl + "/" + latitude + "," + longitude;
        //       WeatherData weatherData = new WeatherData();
//        //use this variable to load everything from JSON.
//

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


            JSONObject topLevelObject = new JSONObject(jsonString);
            JSONObject weatherObject = topLevelObject.getJSONObject("currently");

            try {
                weatherData.setTimezone(topLevelObject.getString("timezone"));
            } catch (Exception e) {
                Log.e("weather JSON", e.getMessage());
            }
            try {
                weatherData.setSummary(weatherObject.getString("summary"));
            } catch (Exception e) {
                Log.e("weather JSON", e.getMessage());
            }
            try {
                weatherData.setIcon(weatherObject.getString("icon") + "");
            } catch (Exception e) {
                Log.e("weather JSON", e.getMessage());
            }
            try {
                weatherData.setWindSpeed(weatherObject.getDouble("windSpeed") + "");
            } catch (Exception e) {
                Log.e("weather JSON", e.getMessage());
            }
            try {
                weatherData.setHumidity(weatherObject.getDouble("humidity") + "");
            } catch (Exception e) {
                Log.e("weather JSON", e.getMessage());
            }
            try {
                weatherData.setTemperature(weatherObject.getDouble("temperature") + "");
            } catch (Exception e) {
                Log.e("weather JSON", e.getMessage());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherData; //returns the result from background thread, this line will be different.
    }

    @Override
    protected void onPostExecute(WeatherData weatherData) { //receiving the weatherdata result in the main (UI) thread
        mWeatherData = weatherData;

        Intent intent = new Intent("weatherDataReady"); //creating the Broadcast Message with "weatherDataReady"
        if (mContext != null) {
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent); //doing the actual broadcast--Local Broadcast Manager will both do the broadcast and receive it
        }

    }
}

