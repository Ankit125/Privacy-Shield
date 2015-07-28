package com.privatis.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.privatis.message.MainActivity;
import com.privatis.message.R;

public class Premium_Fragment extends Fragment {
	Menu menu;
	private LinearLayout ll_first, ll_second, ll_third;
	private Button Btn_next;
	private EditText editText1, editText2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		View v = inflater.inflate(R.layout.pramium_fragment, container, false);

		try {
			ll_first = (LinearLayout) v.findViewById(R.id.ll_first);
			ll_second = (LinearLayout) v.findViewById(R.id.ll_second);
			ll_third = (LinearLayout) v.findViewById(R.id.ll_third);

			ll_first.setVisibility(View.VISIBLE);
			ll_second.setVisibility(View.GONE);
			ll_third.setVisibility(View.GONE);
			editText1 = (EditText) v.findViewById(R.id.editText1);
			editText2 = (EditText) v.findViewById(R.id.editText2);
			editText1.setHintTextColor(Color.WHITE);
			editText2.setHintTextColor(Color.WHITE);

			Btn_next = (Button) v.findViewById(R.id.btn_next);
			Btn_next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (ll_second.isShown()) {
						ll_second.setVisibility(View.GONE);
						ll_third.setVisibility(View.VISIBLE);
						menu.findItem(R.id.menu_cancel).setVisible(false);
					}
				}
			});

			// return super.onCreateView(inflater, container,
			// savedInstanceState);
			// ActionBar actionbar = getActivity().getActionBar();
			// actionbar.setBackgroundDrawable(getResources().getDrawable(
			// R.drawable.actionbarbg));
			// actionbar.setTitle("Go");
			// actionbar.setHomeButtonEnabled(true);
			// actionbar.setDisplayHomeAsUpEnabled(true);

			// ---------It's working but but effect real menu drawer
			// ActionBar mActionBar = getActivity().getActionBar();
			// mActionBar.setDisplayShowHomeEnabled(false);
			// mActionBar.setDisplayShowTitleEnabled(false);
			// LayoutInflater mInflater = LayoutInflater.from(getActivity());
			//
			// View mCustomView =
			// mInflater.inflate(R.layout.actionbar_message_reply,
			// null);
			//
			// mActionBar.setCustomView(mCustomView);
			// mActionBar.setDisplayShowCustomEnabled(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		// MenuInflater inflaters = inflater;
		// inflaters.inflate(R.menu.compose_email, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		try {
			super.onPrepareOptionsMenu(menu);

			// menu.clear();
			// ActionBar action = getActivity().getActionBar();
			// action.setTitle("Go Premium");
			this.menu = menu;
			menu.findItem(R.id.menu_NewEmails).setVisible(false);
			menu.findItem(R.id.menu_search).setVisible(false);

			menu.findItem(R.id.menu_agree).setVisible(true);
			menu.findItem(R.id.menu_cancel).setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_agree:
			if (ll_first.isShown()) {
				ll_first.setVisibility(View.GONE);
				ll_second.setVisibility(View.VISIBLE);
			}
			menu.findItem(R.id.menu_agree).setVisible(false);
			break;
		case R.id.menu_cancel:
			getActivity().finish();
			Intent intent = new Intent(getActivity(), MainActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
