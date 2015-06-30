package jorgereina1986.c4q.nyc.feedster.adapters;

/**
 * Created by c4q-Allison on 6/29/15.
 */
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
import jorgereina1986.c4q.nyc.feedster.models.TrendingData;


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

    public void setTrendingCardData(TrendingData trendingData) {
        //TODO: replace old card data
        cardDataList.add(trendingData);

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


}