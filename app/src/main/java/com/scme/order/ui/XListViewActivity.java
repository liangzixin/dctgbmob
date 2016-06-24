package com.scme.order.ui;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.scme.order.maxwin.view.XListView;
import com.scme.order.maxwin.view.XListView.IXListViewListener;
import com.scme.order.model.Txxx;
import com.scme.order.service.TxxxService;


public class XListViewActivity extends Activity implements IXListViewListener {
	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private Txxx txxx;
	private List txxxList;
	TxxxService txxxService = new TxxxService();
	private 	int intFrist=0;
	private int recPerPag=30;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainfy);
		geneItems();
		mListView = (XListView) findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new ArrayAdapter<String>(this, R.layout.txxxlist_item, items);
		mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
	}

	private void geneItems() {

		try {
			txxxList = txxxService.QueryAllTxxx(intFrist,recPerPag);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i<=txxxList.size()-1; ++i) {
			txxx=(Txxx)txxxList.get(i);
			items.add(txxx.getId()+"");
			items.add(txxx.getSubname1(8));
			items.add(txxx.getName());
			items.add(txxx.getSfzh());
		}
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.list_itemfy, items);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

}