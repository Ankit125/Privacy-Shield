package com.privatis.message;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.actionbarsherlock.app.SherlockFragment;
//import com.actionbarsherlock.app.SherlockFragmentActivity;
//import com.actionbarsherlock.view.MenuInflater;
//import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.privatis.fragment.Calls_Fragment;
import com.privatis.fragment.Calls_details;
import com.privatis.fragment.Email_Fragment;
import com.privatis.fragment.Keypad_Fragment;
import com.privatis.fragment.Keypad_Fragment_new;
import com.privatis.fragment.Message_Fragment;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class Main_test extends Fragment {

	private TextView textView1;
	private ImageView img_menu;
	private static final String[] CONTENT = new String[] { "EMAILS",
			"MESSAGES", "CALLS", "KEYPAD" };

	int check = 0;
	Calls_Fragment call_fragment;

	private static final int[] ICONS = new int[] { R.drawable.message_menu,
			R.drawable.call_menu, R.drawable.keypade_menu, R.drawable.app_icon };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.frag_main, container, false);

		try {
			int postion = 0;

			// Bundle bdl = getIntent().getExtras();
			// if (bdl != null) {
			// postion = bdl.getInt("position");
			// }
			postion = getArguments().getInt("position");

			FragmentPagerAdapter adapter = new GoogleMusicAdapter(
					getChildFragmentManager());

			ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
			pager.setAdapter(adapter);

			TabPageIndicator indicator = (TabPageIndicator) v
					.findViewById(R.id.indicator);
			indicator.setViewPager(pager);

			indicator.setCurrentItem(postion);

			getActivity().getActionBar().setBackgroundDrawable(
					getResources().getDrawable(R.drawable.actionbarbg));
			getActivity().getActionBar().setTitle("");
			getActivity().getActionBar().setLogo(R.drawable.action_icon);
			// Enable ActionBar app icon to behave as action to toggle nav
			// drawer
			getActivity().getActionBar().setHomeButtonEnabled(true);
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

			getActivity().invalidateOptionsMenu();

			// setHasOptionsMenu(true);
			// return super.onCreateView(inflater, container,
			// savedInstanceState);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;

	}

	// @Override
	// protected void onCreate(Bundle arg0) {
	// // TODO Auto-generated method stub
	// super.onCreate(arg0);
	// setContentView(R.layout.frag_main);
	//
	// int postion = 0;
	//
	// Bundle bdl = getIntent().getExtras();
	// if (bdl != null) {
	// postion = bdl.getInt("position");
	// }
	//
	// FragmentPagerAdapter adapter = new GoogleMusicAdapter(
	// getChildFragmentManager());
	//
	// ViewPager pager = (ViewPager) findViewById(R.id.pager);
	// pager.setAdapter(adapter);
	//
	// TabPageIndicator indicator = (TabPageIndicator)
	// findViewById(R.id.indicator);
	// indicator.setViewPager(pager);
	//
	// indicator.setCurrentItem(postion);
	//
	// getActionBar().setBackgroundDrawable(
	// getResources().getDrawable(R.drawable.actionbarbg));
	// getActionBar().setTitle("");
	// // Enable ActionBar app icon to behave as action to toggle nav drawer
	// getActionBar().setHomeButtonEnabled(true);
	// getActionBar().setDisplayHomeAsUpEnabled(true);
	//
	// invalidateOptionsMenu();
	// }

	// @Override
	// public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu)
	// {
	// // TODO Auto-generated method stub
	//
	// MenuInflater inflaters = getSupportMenuInflater();
	// inflaters.inflate(R.menu.activity_main_actions, menu);
	// return super.onCreateOptionsMenu(menu);
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // TODO Auto-generated method stub
	// MenuInflater inflaters = getMenuInflater();
	// inflaters.inflate(R.menu.activity_main_actions, menu);
	// return super.onCreateOptionsMenu(menu);
	// }

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// // TODO Auto-generated method stub
	//
	// // MenuInflater inflaters = inflater;
	// // inflaters.inflate(R.menu.activity_main_actions, menu);
	//
	// super.onCreateOptionsMenu(menu, inflater);
	// }

	// http://stackoverflow.com/questions/7992216/android-fragment-handle-back-button-press
	// on back button fragment (I'd rather do something like this:)

	// @Override
	// public void onPrepareOptionsMenu(com.actionbarsherlock.view.Menu menu) {
	// // TODO Auto-generated method stub
	//
	// menu.findItem(R.id.menu_NewEmails).setVisible(true);
	// menu.findItem(R.id.menu_search).setVisible(true);
	//
	// menu.findItem(R.id.menu_NewEmails).setOnMenuItemClickListener(
	// new OnMenuItemClickListener() {
	//
	// @Override
	// public boolean onMenuItemClick(
	// com.actionbarsherlock.view.MenuItem item) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getActivity(), "test app",
	// Toast.LENGTH_LONG).show();
	// return false;
	// }
	// });
	// super.onPrepareOptionsMenu(menu);
	// }

	// @Override
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// super.onBackPressed();
	//
	// switch (check) {
	// case 0:
	// finish();
	// break;
	// case 3:
	// call_fragment.onBackPressed();
	// break;
	//
	// }
	// }

	class GoogleMusicAdapter extends FragmentPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// return TestFragment.newInstance(CONTENT[position %
			// CONTENT.length]);
			switch (position) {
			case 0:
				return Email_Fragment.newInstance("first");
			case 1:
				return Message_Fragment.newInstance("forth");
			case 2:
				call_fragment = new Calls_Fragment();
				return Calls_Fragment.newInstance("second");
			case 3:
				return Keypad_Fragment_new.newInstance("Third");
			default:
				return Email_Fragment.newInstance("first");
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length];// .toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}

	// public boolean onBackPressed() {
	//
	// if (call_fragment.onBackPressed()) {
	// MainActivity_all.flag = false;
	// return false;
	// } else {
	// MainActivity_all.flag = true;
	// return true;
	// }
	// }
}
