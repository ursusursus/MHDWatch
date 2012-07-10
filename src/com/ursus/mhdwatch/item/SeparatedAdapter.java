package com.ursus.mhdwatch.item;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ursus.mhdwatch.R;

public class SeparatedAdapter extends ArrayAdapter<Item>{
	
	
	private ArrayList<Item> list;
	

	public SeparatedAdapter(Context context, int textViewResourceId, ArrayList<Item> list) {
		
		super(context, textViewResourceId, list);
		this.list = list;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view;
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Item item = list.get(position);
				
		if(item instanceof HeaderItem) {
			
			HeaderItem headerItem = (HeaderItem)item;
			view = inflater.inflate(R.layout.header, parent, false);
			
			TextView headerNameTextView = (TextView) view.findViewById(R.id.headerName);
			headerNameTextView.setText(headerItem.getName().toUpperCase());
			
		} else {
			
			ListItem listItem = (ListItem)item;
			view = inflater.inflate(R.layout.item, parent, false);
			
			TextView departureTimeTextView = (TextView) view.findViewById(R.id.departureTime);
			departureTimeTextView.setText("• " + listItem.getDepartureTime());
			
			TextView remainingTimeTextView = (TextView) view.findViewById(R.id.remainingTime);
			remainingTimeTextView.setText(listItem.getRemainingTime());
			
		}
		
		return view;
		
	}
}
