package jorgereina1986.c4q.nyc.feedster.adapters;

/**
 * Created by c4q-Allison, Jorge , and Anna on 6/29/15.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.TrendingData;
import jorgereina1986.c4q.nyc.feedster.models.WeatherData;


public class FeedCardsAdapter extends RecyclerView.Adapter<FeedCardsAdapter.CardViewHolder> {

    List<CardData> cardDataList;


    public FeedCardsAdapter() {
        cardDataList = new ArrayList<>();
    }

    public List<CardData> getCardDataList() {
        return cardDataList;
    }

    public void setTrendingCardData(TrendingData trendingData) {
        //replacing old card data
        Boolean foundOldData = false;
        for (int i = cardDataList.size() - 1; i >= 0; i--) {
            if (cardDataList.get(i) instanceof TrendingData) {
                cardDataList.set(i, trendingData);
                foundOldData = true;

            }
        }
        if (!foundOldData) {
            cardDataList.add(trendingData);
        }


    }

    public void setWeatherCardData(WeatherData weatherData) {
        //replacing old card data
        Boolean foundOldData = false;
        for (int i = cardDataList.size() - 1; i >= 0; i--) {
            if (cardDataList.get(i) instanceof WeatherData) {
                cardDataList.set(i, weatherData);
                foundOldData = true;
            }
        }
        if (!foundOldData) {
            cardDataList.add(weatherData);
        }
    }


    public void setCardDataList(List<CardData> cardDataList) {
        this.cardDataList = cardDataList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 3:

                View trendingCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_card, parent, false);
                TrendingCardViewHolder trendingCardViewHolder = new TrendingCardViewHolder(trendingCard);
                return trendingCardViewHolder;
            case 1:

                View weatherCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
                WeatherCardViewHolder weatherWeatherCardViewHolder = new WeatherCardViewHolder(weatherCard);
                return weatherWeatherCardViewHolder;

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardData cardData = cardDataList.get(position);
        if (cardData instanceof TrendingData) {
            TrendingData trendingData = (TrendingData) cardData;
            TrendingCardViewHolder trendingCardViewHolder = (TrendingCardViewHolder) holder;

            try {
                trendingCardViewHolder.tvTrendItem0.setText(trendingData.getTrendingItems().get(0));
                trendingCardViewHolder.tvTrendItem1.setText(trendingData.getTrendingItems().get(1));
                trendingCardViewHolder.tvTrendItem2.setText(trendingData.getTrendingItems().get(2));
                trendingCardViewHolder.tvTrendItem3.setText(trendingData.getTrendingItems().get(3));
                trendingCardViewHolder.tvTrendItem4.setText(trendingData.getTrendingItems().get(4));
            }catch (Exception e){
                Log.e("trendingData",e.getMessage());
            }
        } else if (cardData instanceof WeatherData)

        {
            WeatherData weatherData = (WeatherData) cardData;
            WeatherCardViewHolder weatherCardViewHolder = (WeatherCardViewHolder) holder;

            weatherCardViewHolder.tvSummary.setText(weatherData.getSummary());
        //    weatherCardViewHolder.tvTimezone.setText(weatherData.getTimezone());
            showWeatherIcon(weatherData.getIcon(),weatherCardViewHolder.ivIcon);
            weatherCardViewHolder.tvTemperature.setText(weatherData.getTemperature());
            weatherCardViewHolder.tvHumidity.setText(weatherData.getHumidity());
            weatherCardViewHolder.tvWindSpeed.setText(weatherData.getWindSpeed());
        }

    }

    private void showWeatherIcon(String iconData, ImageView imageView){
        if (iconData == null){
            //find another icon to replace this icon
            imageView.setImageResource(R.drawable.weather_sunny_icon);
            return;
        }
        if (iconData.equals("partly-cloudy-day")){
            imageView.setImageResource(R.drawable.cloud_256);
        }else if (iconData.equals("rain")){
            imageView.setImageResource(R.drawable.rainy_cloud_256);
        }else {
            imageView.setImageResource(R.drawable.big_sun_256);
        }
    }
    @Override
    public int getItemCount() {
        return cardDataList.size();
    }

    @Override
    public int getItemViewType(int position) {

        CardData cardData = cardDataList.get(position);
        if (cardData instanceof TrendingData) {
            return 3;
        }
        if (cardData instanceof WeatherData) {
            return 1;
        }
        return -1;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class TrendingCardViewHolder extends CardViewHolder {
        CardView cvTrendingCard;
        TextView tvTrendItem0;
        TextView tvTrendItem1;
        TextView tvTrendItem2;
        TextView tvTrendItem3;
        TextView tvTrendItem4;

        public TrendingCardViewHolder(View itemView) {
            super(itemView);
//don't need the "this"s but they're helpful as a just-in-case:
            this.cvTrendingCard = (CardView) itemView.findViewById(R.id.trending_cardview);
            this.tvTrendItem0 = (TextView) cvTrendingCard.findViewById(R.id.trending_item0);
            this.tvTrendItem1 = (TextView) cvTrendingCard.findViewById(R.id.trending_item1);
            this.tvTrendItem2 = (TextView) cvTrendingCard.findViewById(R.id.trending_item2);
            this.tvTrendItem3 = (TextView) cvTrendingCard.findViewById(R.id.trending_item3);
            this.tvTrendItem4 = (TextView) cvTrendingCard.findViewById(R.id.trending_item4);
        }

    }

    public static class WeatherCardViewHolder extends CardViewHolder {
        CardView cvWeatherCard;
        //add all the weather_card.xml
        TextView tvSummary;
        //TextView tvTimezone;
        ImageView ivIcon;
        TextView tvHumidity;
        TextView tvWindSpeed;
        TextView tvTemperature;

        public WeatherCardViewHolder(View itemView) {
            super(itemView);

            //also add here.findViewById....from layout file.
            this.cvWeatherCard = (CardView) itemView.findViewById(R.id.weather_cardview);

            this.tvSummary = (TextView) cvWeatherCard.findViewById(R.id.summary);
//            this.tvTimezone = (TextView) cvWeatherCard.findViewById(R.id.timezone);
            this.ivIcon = (ImageView) cvWeatherCard.findViewById(R.id.icon);
            this.tvHumidity = (TextView) cvWeatherCard.findViewById(R.id.humidity);
            this.tvWindSpeed = (TextView) cvWeatherCard.findViewById(R.id.windSpeed);
            this.tvTemperature = (TextView) cvWeatherCard.findViewById(R.id.temperature);
        }
    }
}


