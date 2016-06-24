package com.scme.order.ui;

import android.os.Bundle;
import com.scme.order.adapter.MaterialCardAdapter;
import com.scme.order.card.Card;
import com.scme.order.card.HeadlineBodyCard;

import java.util.ArrayList;
import java.util.List;

public class LicensesActivity extends CardActivity {

    private static final String TAG = LicensesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRecyclerAdapter(new MaterialCardAdapter(populateDataset()));
    }

    private List<Card> populateDataset() {
        List<Card> dataset = new ArrayList<Card>();
        dataset.add(new HeadlineBodyCard(0, getString(R.string.licenses_txt_butterknife), getString(R.string.licenses_txt_butterknife_licence)));
        return dataset;
    }
}
