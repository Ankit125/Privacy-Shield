package com.privatis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

//import com.actionbarsherlock.app.SherlockFragment;
//import com.actionbarsherlock.app.SherlockFragmentActivity;
//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuInflater;
//import com.actionbarsherlock.view.MenuItem;
import com.privatis.adapter.MenuListAdapter;
import com.privatis.message.MainActivity;
import com.privatis.message.MainActivity_all;
import com.privatis.message.Main_test;
import com.privatis.message.R;

public class Main_Menu extends Fragment {

	private LinearLayout ll_emails, ll_message, ll_calls, ll_keypad;

	// ----------------
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	String[] title;
	String[] subtitle;
	MenuListAdapter mMenuAdapter;

	// ------------
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.activity_menu, container, false);

		try {
			ll_emails = (LinearLayout) v.findViewById(R.id.ll_emails);
			ll_message = (LinearLayout) v.findViewById(R.id.ll_messages);
			ll_calls = (LinearLayout) v.findViewById(R.id.ll_calls);
			ll_keypad = (LinearLayout) v.findViewById(R.id.ll_keypad);

			ll_emails.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Intent intent = new Intent(getActivity(),
					// Main_test.class);
					// startActivity(intent);
					// showFragment(0);
					try {
						Bundle bdl = new Bundle();
						bdl.putInt("position", 0);
						Intent intent = new Intent(getActivity(),
								MainActivity_all.class);
						intent.putExtras(bdl);
						startActivity(intent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			ll_message.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// showFragment(1);
					try {
						Bundle bdl = new Bundle();
						bdl.putInt("position", 1);
						Intent intent = new Intent(getActivity(),
								MainActivity_all.class);
						intent.putExtras(bdl);
						startActivity(intent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			ll_calls.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// showFragment(2);
					try {
						Bundle bdl = new Bundle();
						bdl.putInt("position", 2);
						Intent intent = new Intent(getActivity(),
								MainActivity_all.class);
						intent.putExtras(bdl);
						startActivity(intent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			ll_keypad.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// showFragment(3);
					try {
						Bundle bdl = new Bundle();
						bdl.putInt("position", 3);
						Intent intent = new Intent(getActivity(),
								MainActivity_all.class);
						intent.putExtras(bdl);
						startActivity(intent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			setHasOptionsMenu(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

	// public void showFragment(int position) {
	//
	// // Creating a fragment object
	// Main_test cFragment = new Main_test();
	//
	// // Creating a Bundle object
	// Bundle data = new Bundle();
	//
	// // Setting the index of the currently selected item of mDrawerList
	// data.putInt("position", position);
	//
	// // Setting the position to the fragment
	// cFragment.setArguments(data);
	//
	// // Getting reference to the FragmentManager
	// FragmentManager fragmentManager = getActivity()
	// .getSupportFragmentManager();
	//
	// // Creating a fragment transaction
	// FragmentTransaction ft = fragmentManager.beginTransaction();
	//
	// // Adding a fragment to the fragment transaction
	// // ft.replace(R.id.content_frame, cFragment);
	//
	// String st = String.valueOf(position);
	// ft.replace(R.id.llContainer, cFragment, st);
	//
	// // Committing the transaction
	// ft.commit();
	// }

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);

		menu.findItem(R.id.menu_NewEmails).setVisible(false);
		menu.findItem(R.id.menu_search).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		// if (item.getItemId() == R.id.menu_NewEmails) {
		// Toast.makeText(getActivity(), "press new Email", Toast.LENGTH_LONG)
		// .show();
		// // return true;
		// }
		// switch (item.getItemId()) {
		// case android.R.id.home:
		// getActivity().finish();
		// }
		return super.onOptionsItemSelected(item);
	}

}
