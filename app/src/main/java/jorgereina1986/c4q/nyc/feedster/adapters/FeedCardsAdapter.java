package jorgereina1986.c4q.nyc.feedster.adapters;

/**
 * Created by c4q-Allison, Jorge , and Anna on 6/29/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import android.widget.ImageView;

import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jorgereina1986.c4q.nyc.feedster.AppController;
import jorgereina1986.c4q.nyc.feedster.helpers.Constants;
import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.models.CardData;
import jorgereina1986.c4q.nyc.feedster.models.MusicData;
import jorgereina1986.c4q.nyc.feedster.models.MusicItemData;
import jorgereina1986.c4q.nyc.feedster.models.ToDoData;
import jorgereina1986.c4q.nyc.feedster.models.TrendingData;
import jorgereina1986.c4q.nyc.feedster.models.WeatherData;


public class FeedCardsAdapter extends RecyclerView.Adapter<FeedCardsAdapter.CardViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    List<CardData> cardDataList;
    List<String> toDoItemsList;
    ArrayAdapter<String> toDoAdapter;
    Context mContext;

    public FeedCardsAdapter(Context context) {
        mContext = context;
        cardDataList = new ArrayList<>();
        toDoItemsList = new ArrayList<>();
    }

    public List<CardData> getCardDataList() {
        return cardDataList;
    }

    public void setToDoCardData(ToDoData toDoData) {
        //replacing old card data
        Boolean foundOldData = false;
        for (int i = cardDataList.size() - 1; i >= 0; i--) {
            if (cardDataList.get(i) instanceof ToDoData) {
                cardDataList.set(i, toDoData);
                foundOldData = true;
            }
        }
        if (!foundOldData) {
            cardDataList.add(toDoData);
        }
        toDoItemsList = toDoData.getToDoList();
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
            cardDataList.add(0, weatherData);
        }
    }

    public void setMusicCardData(MusicData musicData) {
        //replacing old card data
        Boolean foundOldData = false;
        for (int i = cardDataList.size() - 1; i >= 0; i--) {
            if (cardDataList.get(i) instanceof MusicData) {
                cardDataList.set(i, musicData);
                foundOldData = true;
            }
        }
        if (!foundOldData) {
            cardDataList.add(musicData);
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

            case 4:
                View toDoCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_card, parent, false);
                ToDoCardViewHolder toDoCardViewHolder = new ToDoCardViewHolder(toDoCard);
                return toDoCardViewHolder;
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
            } catch (Exception e) {
                Log.e("trendingData", e.getMessage());
            }
        } else if (cardData instanceof WeatherData) {
            try {
                WeatherData weatherData = (WeatherData) cardData;
                WeatherCardViewHolder weatherCardViewHolder = (WeatherCardViewHolder) holder;

                weatherCardViewHolder.tvSummary.setText(weatherData.getSummary());
                weatherCardViewHolder.tvTimezone.setText(weatherData.getTimezone());
                showWeatherIcon(weatherData.getIcon(), weatherCardViewHolder.ivIcon);
                int temp = (int) Double.parseDouble(weatherData.getTemperature());
                weatherCardViewHolder.tvTemperature.setText(temp + "" + "℉");
                weatherCardViewHolder.tvHumidity.setText(weatherData.getHumidity());
                weatherCardViewHolder.tvWindSpeed.setText(weatherData.getWindSpeed());
            } catch (Exception e) {
                Log.e("weather", e.getMessage());
            }
        } else if (cardData instanceof MusicData) {
            try {

                MusicData musicData = (MusicData) cardData;
                MusicCardViewHolder musicCardViewHolder = (MusicCardViewHolder) holder;

                MusicItemData itemData0 = musicData.getMusicItemDataList().get(0);
                bindMusicRow(itemData0, musicCardViewHolder.rlMusicItem0);

                MusicItemData itemData1 = musicData.getMusicItemDataList().get(1);
                bindMusicRow(itemData1, musicCardViewHolder.rlMusicItem1);

                MusicItemData itemData2 = musicData.getMusicItemDataList().get(2);
                bindMusicRow(itemData2, musicCardViewHolder.rlMusicItem2);

                MusicItemData itemData3 = musicData.getMusicItemDataList().get(3);
                bindMusicRow(itemData3, musicCardViewHolder.rlMusicItem3);

                MusicItemData itemData4 = musicData.getMusicItemDataList().get(4);
                bindMusicRow(itemData4, musicCardViewHolder.rlMusicItem4);
            } catch (Exception e) {
                Log.e("music", e.getMessage());
            }
        } else if (cardData instanceof ToDoData) {
            try {
                ToDoData toDoData = (ToDoData) cardData;
                toDoItemsList = toDoData.getToDoList();
                final ArrayAdapter<String> toDoAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, toDoItemsList);

                final ToDoCardViewHolder toDoCardViewHolder = (ToDoCardViewHolder) holder;
                toDoCardViewHolder.etToDoItem.setText("");

//                toDoAdapter.clear();
                //  toDoAdapter.addAll(toDoItemsList);

                toDoCardViewHolder.lvToDoList.setAdapter(toDoAdapter);
                toDoAdapter.notifyDataSetChanged();

                toDoCardViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String todoItem = String.valueOf(toDoCardViewHolder.etToDoItem.getText());
                        toDoItemsList.add(todoItem);
//                        toDoAdapter.add(todoItem);
                        toDoCardViewHolder.etToDoItem.setText("");
                        toDoAdapter.notifyDataSetChanged();
                        saveToDoList();
                    }
                });

            } catch (Exception e) {
                Log.e("TODO", e.getMessage());
            }
        }
    }

    private void saveToDoList() {
        SharedPreferences thePrefs = mContext.getSharedPreferences(Constants.MY_APP_PREFS, 0);
        SharedPreferences.Editor editor = thePrefs.edit();
        Set<String> toDoSet = new HashSet<String>(toDoItemsList);
        editor.putStringSet(Constants.TO_DO_ITEMS_SET, toDoSet);
        editor.commit();
    }

    protected void bindMusicRow(MusicItemData musicItemData, RelativeLayout musicItemRow) {
        TextView tvTitle = (TextView) musicItemRow.findViewById(R.id.title);
        TextView tvArtist = (TextView) musicItemRow.findViewById(R.id.artist);
        NetworkImageView thumbNail = (NetworkImageView) musicItemRow.findViewById(R.id.thumbnail);
        tvArtist.setText(musicItemData.getArtist());
        tvTitle.setText((musicItemData.getTitle()));
        thumbNail.setImageUrl(musicItemData.getThumbnailUrl(), imageLoader);
    }

    private void showWeatherIcon(String iconData, ImageView imageView) {
        if (iconData == null) {
            //find another icon to replace this icon
            imageView.setImageResource(R.drawable.weather_sunny_icon);
            return;
        }
        if (iconData.equals("partly-cloudy-day")) {
            imageView.setImageResource(R.drawable.cloud_256);
        } else if (iconData.equals("rain")) {
            imageView.setImageResource(R.drawable.rainy_cloud_256);
        } else {
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
        if (cardData instanceof MusicData) {
            return 2;
        }
        if (cardData instanceof ToDoData) {
            return 4;
        }

        return -1;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ToDoCardViewHolder extends CardViewHolder {
        CardView cvToDoCard;
        EditText etToDoItem;
        Button btnAdd;
        ListView lvToDoList;

        public ToDoCardViewHolder(View itemView) {
            super(itemView);
//don't need the "this"s but they're helpful as a just-in-case:
            this.cvToDoCard = (CardView) itemView.findViewById(R.id.to_do_cardview);
            this.etToDoItem = (EditText) itemView.findViewById(R.id.toDoEditTxtItem);
            this.btnAdd = (Button) itemView.findViewById(R.id.toDoBtnAdd);
            this.lvToDoList = (ListView) itemView.findViewById(R.id.toDoListItems);
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
        TextView tvTimezone;
        ImageView ivIcon;
        TextView tvHumidity;
        TextView tvWindSpeed;
        TextView tvTemperature;

        public WeatherCardViewHolder(View itemView) {
            super(itemView);

            //also add here.findViewById....from layout file.
            this.cvWeatherCard = (CardView) itemView.findViewById(R.id.weather_cardview);

            this.tvSummary = (TextView) cvWeatherCard.findViewById(R.id.summary);
            this.tvTimezone = (TextView) cvWeatherCard.findViewById(R.id.timezone);
            this.ivIcon = (ImageView) cvWeatherCard.findViewById(R.id.icon);
            this.tvHumidity = (TextView) cvWeatherCard.findViewById(R.id.humidity);
            this.tvWindSpeed = (TextView) cvWeatherCard.findViewById(R.id.windSpeed);
            this.tvTemperature = (TextView) cvWeatherCard.findViewById(R.id.temperature);
        }
    }

    public static class MusicCardViewHolder extends CardViewHolder {
        CardView cvMusicCard;
        RelativeLayout rlMusicItem0;
        RelativeLayout rlMusicItem1;
        RelativeLayout rlMusicItem2;
        RelativeLayout rlMusicItem3;
        RelativeLayout rlMusicItem4;

        public MusicCardViewHolder(View itemView) {
            super(itemView);
//don't need the "this"s but they're helpful as a just-in-case:
            this.cvMusicCard = (CardView) itemView.findViewById(R.id.music_cardview);
            this.rlMusicItem0 = (RelativeLayout) cvMusicCard.findViewById(R.id.music_item_0);
            this.rlMusicItem1 = (RelativeLayout) cvMusicCard.findViewById(R.id.music_item_1);
            this.rlMusicItem2 = (RelativeLayout) cvMusicCard.findViewById(R.id.music_item_2);
            this.rlMusicItem3 = (RelativeLayout) cvMusicCard.findViewById(R.id.music_item_3);
            this.rlMusicItem4 = (RelativeLayout) cvMusicCard.findViewById(R.id.music_item_4);
        }

    }
}