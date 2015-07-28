package com.privatis.adapter;

import com.privatis.message.R;
import com.privatis.message.R.color;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] mTitle;
	String[] mSubTitle;
	// int[] mIcon;
	LayoutInflater inflater;

	public MenuListAdapter(Context context, String[] title, String[] subtitle) {
		this.context = context;
		this.mTitle = title;
		this.mSubTitle = subtitle;
		// this.mIcon = icon;
	}

	@Override
	public int getCount() {

		return mTitle.length;
	}

	@Override
	public Object getItem(int position) {
		return mTitle[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		// Declare Variables
		View itemView = null;
		try {
			TextView txtTitle;
			TextView txtSubTitle;
			ImageView imgIcon;

			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_list_item, parent,
					false);

			TextView textView = new TextView(context);

			textView.setText("Message");
			// textView.setTextColor(color.blue_text);

			// Locate the TextViews in drawer_list_item.xml
			txtTitle = (TextView) itemView.findViewById(R.id.title);
			txtSubTitle = (TextView) itemView.findViewById(R.id.subtitle);

			// Locate the ImageView in drawer_list_item.xml
			imgIcon = (ImageView) itemView.findViewById(R.id.icon);

			// Set the results into TextViews
			txtTitle.setText(mTitle[position]);
			// txtTitle.setTextColor(color.blue_text);
			// txtSubTitle.setText(mSubTitle[position]);
			// txtSubTitle.setTextColor(color.blue_text);

			// Set the results into ImageView
			try {
				// imgIcon.setImageResource(mIcon[position]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// set selected item
			LinearLayout ActiveItem = (LinearLayout) itemView;
			// if (position == selectedItem) {
			// ActiveItem.setBackgroundResource(Color.parseColor("#33b5e5"));

			/*
			 * // for focus on it int top = (ActiveItem == null) ? 0 :
			 * ActiveItem.getTop(); ((ListView)
			 * parent).setSelectionFromTop(position, top);
			 */
			// } else {
			// ActiveItem.setBackgroundResource(Color.WHITE);
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemView;
	}

	private int selectedItem;

	public void setSelectedItem(int position) {
		selectedItem = position;
	}
}
