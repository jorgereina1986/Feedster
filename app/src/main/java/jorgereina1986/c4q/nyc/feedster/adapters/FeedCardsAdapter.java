package jorgereina1986.c4q.nyc.feedster.adapters;

/**
 * Created by c4q-Allison, Jorge , and Anna on 6/29/15.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import jorgereina1986.c4q.nyc.feedster.AppController;
import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.MusicData;
import jorgereina1986.c4q.nyc.feedster.models.TrendingData;
import jorgereina1986.c4q.nyc.feedster.models.WeatherData;


public class FeedCardsAdapter extends RecyclerView.Adapter<FeedCardsAdapter.CardViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
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

            case 2:
                View musicCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_card, parent, false);
                MusicCardViewHolder musicCardViewHolder = new MusicCardViewHolder(musicCard);
                return musicCardViewHolder;

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

            trendingCardViewHolder.tvTrendItem0.setText(trendingData.getTrendingItems().get(0));
            trendingCardViewHolder.tvTrendItem1.setText(trendingData.getTrendingItems().get(1));
            trendingCardViewHolder.tvTrendItem2.setText(trendingData.getTrendingItems().get(2));
            trendingCardViewHolder.tvTrendItem3.setText(trendingData.getTrendingItems().get(3));
            trendingCardViewHolder.tvTrendItem4.setText(trendingData.getTrendingItems().get(4));

        } else if (cardData instanceof WeatherData){
            WeatherData weatherData = (WeatherData) cardData;
            WeatherCardViewHolder weatherCardViewHolder = (WeatherCardViewHolder) holder;

            weatherCardViewHolder.tvTemperature.setText(weatherData.getTemperature());
            weatherCardViewHolder.tvHumidity.setText(weatherData.getHumidity());
            weatherCardViewHolder.tvWindSpeed.setText(weatherData.getWindSpeed());
        } else if (cardData instanceof MusicData){
            MusicData weatherData = (MusicData) cardData;
            MusicCardViewHolder musicCardViewHolder = (MusicCardViewHolder) holder;

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
        if (cardData instanceof MusicData) {
            return 2;
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
    public static class MusicCardViewHolder extends CardViewHolder {
        CardView cvMusicCard;
        LinearLayout llMusicItem0;
        LinearLayout llMusicItem1;
        LinearLayout llMusicItem2;
        LinearLayout llMusicItem3;
        LinearLayout llMusicItem4;

        public MusicCardViewHolder(View itemView) {
            super(itemView);
//don't need the "this"s but they're helpful as a just-in-case:
            this.cvMusicCard = (CardView) itemView.findViewById(R.id.music_cardview);
            this.llMusicItem0 = (LinearLayout) cvMusicCard.findViewById(R.id.music_item_0);
            this.llMusicItem1 = (LinearLayout) cvMusicCard.findViewById(R.id.music_item_1);
            this.llMusicItem2 = (LinearLayout) cvMusicCard.findViewById(R.id.music_item_2);
            this.llMusicItem3 = (LinearLayout) cvMusicCard.findViewById(R.id.music_item_3);
            this.llMusicItem4 = (LinearLayout) cvMusicCard.findViewById(R.id.music_item_4);
        }

    }
}


