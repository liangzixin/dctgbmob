package com.scme.order.adpater;

import java.util.List;

import com.scme.order.model.Ttables;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TablesAdapter extends  BaseAdapter {

	public List<Ttables>tables;//数据源
	public int layoutId;//样式布局文件

	public TablesAdapter(List<Ttables> tables,int layoutId)
	{
		this.tables=tables;
		this.layoutId=layoutId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
		{
			//convertView=getL
		}
		return null;
	}

}
