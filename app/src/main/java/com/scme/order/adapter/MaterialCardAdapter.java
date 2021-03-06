package com.scme.order.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scme.order.card.Card;
import com.scme.order.card.HeadlineBodyCard;
import com.scme.order.ui.R;

import java.util.List;

import static com.scme.order.card.Card.TYPE_HEADLINE_BODY_1;

public class MaterialCardAdapter extends RecyclerView.Adapter {

    private List<Card> dataset;

    public MaterialCardAdapter(List<Card> dataset) {
        this.dataset = dataset;
    }

    @Override
    public int getItemViewType(int position) {
        return dataset.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEADLINE_BODY_1: {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_headline_body1_material, parent, false);
                viewHolder = new HeadlineBodyCard.ViewHolder(v);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Card card = dataset.get(position);
        ((Card.CardHolder) viewHolder).bindView(card, position);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
