package jorgereina1986.c4q.nyc.feedster.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.WeatherData;


/**
 * Created by c4q-Allison on 6/27/15.
 */
public class FeedCardsAdapter extends RecyclerView.Adapter<FeedCardsAdapter.CardViewHolder> {

    List<CardData> cardDataList;


    public FeedCardsAdapter() {
        cardDataList = new ArrayList<>();
    }

    public List<CardData> getCardDataList() {
        return cardDataList;
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
        if (cardData instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) cardData;
            WeatherCardViewHolder weatherCardViewHolder = (WeatherCardViewHolder) holder;

            weatherCardViewHolder.tvTemperature.setText(weatherData.getTemperature());
            weatherCardViewHolder.tvHumidity.setText(weatherData.getHumidity());
            weatherCardViewHolder.tvWindSpeed.setText(weatherData.getWindSpeed());
        }

    }


    @Override
    public int getItemCount() {
        return cardDataList.size();
    }

    @Override
    public int getItemViewType(int position) {

        CardData cardData = cardDataList.get(position);
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

    public static class WeatherCardViewHolder extends CardViewHolder {
        CardView cvWeatherCard;
        //add all the weather_card.xml
        TextView tvHumidity;
        TextView tvWindSpeed;
        TextView tvTemperature;

        public WeatherCardViewHolder(View itemView) {
            super(itemView);

            //also add here.findViewById....from layout file.
            this.cvWeatherCard = (CardView) itemView.findViewById(R.id.weather_cardview);
            this.tvHumidity = (TextView) cvWeatherCard.findViewById(R.id.humidity);
            this.tvWindSpeed = (TextView) cvWeatherCard.findViewById(R.id.windSpeed);
            this.tvTemperature = (TextView) cvWeatherCard.findViewById(R.id.temperature);
        }
    }

}


