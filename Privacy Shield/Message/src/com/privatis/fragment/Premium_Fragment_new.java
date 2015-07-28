package com.privatis.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.privatis.message.R;

public class Premium_Fragment_new extends Fragment {
	private Button btn_open_browser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		View v = inflater.inflate(R.layout.pramium_fragment_new, container,
				false);

		try {
			btn_open_browser = (Button) v.findViewById(R.id.btn_open_browser);
			btn_open_browser.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String url = "http://www.google.com";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				}
			});

			// --------------
			ActionBar mActionBar = getActivity().getActionBar();
			mActionBar.setDisplayShowHomeEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setLogo(R.drawable.action_icon);
			// mActionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater mInflater = LayoutInflater.from(getActivity());

			View mCustomView = mInflater.inflate(R.layout.actionbar, null);

			mActionBar.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.actionbarbg));

			TextView mTitleTextView = (TextView) mCustomView
					.findViewById(R.id.txt_title);
			mTitleTextView.setText("");

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			// return super.onCreateView(inflater, container, savedInstanceState);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		try {
			menu.findItem(R.id.menu_NewEmails).setVisible(false);
			menu.findItem(R.id.menu_search).setVisible(false);
			super.onPrepareOptionsMenu(menu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		getActivity().getActionBar().setTitle("");
	}
}
