package jorgereina1986.c4q.nyc.feedster.models;
/**
 * Created by c4q-nali on 6/27/15.
 */
public class WeatherData extends CardData {

//    String timezone;
    String summary;
    String icon;
    String temperature;
    String humidity;
    String windSpeed;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

//    public String getTimezone() {
//        return timezone;
//    }
//
//    public void setTimezone(String timezone) {
//        this.timezone = timezone;
//    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}

/*
{
latitude: 37.8267,
longitude: -122.423,
timezone: "America/Los_Angeles",
offset: -7,
currently: {
time: 1435610118,
summary: "Partly Cloudy",
icon: "partly-cloudy-day",
nearestStormDistance: 25,
nearestStormBearing: 77,
precipIntensity: 0,
precipProbability: 0,
temperature: 65.9,
apparentTemperature: 65.9,
dewPoint: 53.69,
humidity: 0.65,
windSpeed: 10.5,
windBearing: 270,
visibility: 9.33,
cloudCover: 0.37,
pressure: 1014.34,
ozone: 310.43
},
 */