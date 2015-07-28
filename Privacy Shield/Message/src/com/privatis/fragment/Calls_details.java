package com.privatis.fragment;

import com.privatis.message.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Calls_details extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.call_details, container, false);

		// return super.onCreateView(inflater, container, savedInstanceState);
		// Calls_Fragment cf = new Calls_Fragment();
		// Menu menu = cf.getMenu();
		// MenuInflater menu_inflater = getActivity().getMenuInflater();
		// menu_inflater.inflate(R.layout.call_details, menu);

		setHasOptionsMenu(true);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public static Calls_details newInstance(String text) {

		Calls_details f = new Calls_details();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// // TODO Auto-generated method stub
	//
	// super.onCreateOptionsMenu(menu, inflater);
	// menu.clear();
	// getActivity().invalidateOptionsMenu();
	// // inflater.inflate(R.layout.call_details, menu);
	// getActivity().getMenuInflater().inflate(R.menu.call_details, menu);
	// }

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.findItem(R.id.menu_NewEmails).setVisible(false);
		menu.findItem(R.id.menu_search).setVisible(false);

		menu.findItem(R.id.menu_call).setVisible(true);
		menu.findItem(R.id.menu_block).setVisible(true);
		menu.findItem(R.id.menu_addcontect).setVisible(true);

		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			getActivity().finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
