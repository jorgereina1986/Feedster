package jorgereina1986.c4q.nyc.feedster.models;

import java.util.List;

/**
 * Created by c4q-Allison on 6/29/15.
 */
public class TrendingData extends CardData {
    List<String> trendingItems;

    public List<String> getTrendingItems() {
        return trendingItems;
    }

    public void setTrendingItems(List<String> trendingItems) {
        this.trendingItems = trendingItems;
    }
}
