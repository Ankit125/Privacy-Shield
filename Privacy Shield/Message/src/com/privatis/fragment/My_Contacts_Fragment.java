package com.privatis.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.adapter.MyContectAdapter;
import com.privatis.adapter.MyContectAdapter.Item;
import com.privatis.adapter.MyContectAdapter.Row;
import com.privatis.adapter.MyContectAdapter.Section;
import com.privatis.databasae.DBAdapter;
import com.privatis.message.ComposeEmailActivity;
import com.privatis.message.R;
import com.privatis.message.R.color;
import com.privatis.message.Send_Mail_Activity;
import com.privatis.model.MyContact;

public class My_Contacts_Fragment extends Fragment {
	private ListView lst_contacts;
	private List<MyContact> Number;
	private MyContectAdapter ContactAdapter;
	private ProgressBar progressBar1;
	Get_Contacts GC = new Get_Contacts();
	Get_Contacts_manually GCM = new Get_Contacts_manually();
	private EditText editText1;

	// ------
	private GestureDetector mGestureDetector;
	private static float sideIndexX;
	private static float sideIndexY;
	private int sideIndexHeight;
	private int indexListSize;
	private List<Object[]> alphabet = new ArrayList<Object[]>();
	private HashMap<String, Integer> sections = new HashMap<String, Integer>();
	View v;
	DBAdapter db;
	private Button btn_refresh;
	LinearLayout sideIndex;

	// ------

	class SideIndexGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			sideIndexX = sideIndexX - distanceX;
			sideIndexY = sideIndexY - distanceY;

			if (sideIndexX >= 0 && sideIndexY >= 0) {
				displayListItem();
			}

			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.mycontacts_fragment, container, false);

		try {
			db = new DBAdapter(getActivity());
			mGestureDetector = new GestureDetector(getActivity(),
					new SideIndexGestureListener());

			setHasOptionsMenu(true);
			// return super.onCreateView(inflater, container,
			// savedInstanceState);
			// --------------
			ActionBar mActionBar = getActivity().getActionBar();
			mActionBar.setDisplayShowHomeEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setLogo(R.drawable.action_icon);
			// mActionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater mInflater = LayoutInflater.from(getActivity());

			View mCustomView = mInflater.inflate(
					R.layout.block_call__emails_actionbar, null);

			mActionBar.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.actionbarbg));

			TextView mTitleTextView = (TextView) mCustomView
					.findViewById(R.id.txt_title);
			mTitleTextView.setText("My Contacts");
			TextView txt_back = (TextView) mCustomView
					.findViewById(R.id.txt_back);
			txt_back.setVisibility(View.GONE);

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);

			progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
			progressBar1.setVisibility(View.VISIBLE);

			lst_contacts = (ListView) v.findViewById(R.id.lst_contacts);
			editText1 = (EditText) v.findViewById(R.id.editText1);
			btn_refresh = (Button) v.findViewById(R.id.btn_refresh);
			sideIndex = (LinearLayout) v.findViewById(R.id.sideIndex);

			Resources res = getResources();
			Number = new ArrayList<MyContact>();

			// if (GC.getStatus() == AsyncTask.Status.FINISHED) {

			// if (!db.isOpen()) {
			// db.open();
			// // }
			// Cursor c = db.getalldatafromsync();
			// c.moveToFirst();
			// String flag = c.getString(0);
			// if (flag.equals("1")) {
			// GC.execute();
			// } else {
			// while (flag.equals("0")) {
			// c = db.getalldatafromsync();
			// c.moveToFirst();
			// flag = c.getString(0);
			// Log.i("System out", "sdsadsadsa :" + flag);
			// if (flag.equals("1")) {
			// // if (GC.getStatus() == AsyncTask.Status.FINISHED) {
			// GC.execute();
			// // }
			// break;
			// }
			// }
			// }
			// c.close();
			// db.close();
			GC.execute();
			//
			// }
			// fetchContacts();
			// progressBar1.setVisibility(View.GONE);
			// getContacts();

			lst_contacts.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					// Item item = (Item) getItem(position);

					// Toast.makeText(getActivity(),
					// Number.get(position).getNumber(),
					// Toast.LENGTH_LONG).show();

					// -----------------
					// show_dialog(Number.get(position).getNumber(),
					// Number.get(position).getEmail());
					// ================
					// showFragment(Number.get(position).getNumber());

				}
			});

			editText1.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					try {
						if (ContactAdapter != null)
							ContactAdapter.filter(s.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}
			});

			btn_refresh.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// if (GCM.getStatus() == AsyncTask.Status.RUNNING)
					Get_Contacts_manually GCM = new Get_Contacts_manually();
					GCM.execute();
				}
			});
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

	public void updateList() {
		// LinearLayout sideIndex = (LinearLayout)
		// v.findViewById(R.id.sideIndex);
		sideIndex.removeAllViews();
		indexListSize = alphabet.size();
		if (indexListSize < 1) {
			return;
		}

		int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
		int tmpIndexListSize = indexListSize;
		while (tmpIndexListSize > indexMaxSize) {
			tmpIndexListSize = tmpIndexListSize / 2;
		}
		double delta;
		if (tmpIndexListSize > 0) {
			delta = indexListSize / tmpIndexListSize;
		} else {
			delta = 1;
		}

		TextView tmpTV;
		for (double i = 1; i <= indexListSize; i = i + delta) {
			Object[] tmpIndexItem = alphabet.get((int) i - 1);
			String tmpLetter = tmpIndexItem[0].toString();

			tmpLetter = tmpLetter.toLowerCase().trim();
			Log.i("System out", "");
			// if (tmpLetter.equals("a") || tmpLetter.equals("b")
			// || tmpLetter.equals("c") || tmpLetter.equals("d")
			// || tmpLetter.equals("e") || tmpLetter.equals("f")
			// || tmpLetter.equals("g") || tmpLetter.equals("h")
			// || tmpLetter.equals("i") || tmpLetter.equals("j")
			// || tmpLetter.equals("k") || tmpLetter.equals("l")
			// || tmpLetter.equals("m") || tmpLetter.equals("n")
			// || tmpLetter.equals("o") || tmpLetter.equals("p")
			// || tmpLetter.equals("q") || tmpLetter.equals("r")
			// || tmpLetter.equals("s") || tmpLetter.equals("t")
			// || tmpLetter.equals("u") || tmpLetter.equals("v")
			// || tmpLetter.equals("w") || tmpLetter.equals("x")
			// || tmpLetter.equals("y") || tmpLetter.equals("z")) {

			tmpTV = new TextView(getActivity());
			tmpTV.setText(tmpLetter.toUpperCase());
			tmpTV.setGravity(Gravity.CENTER);
			tmpTV.setTextSize(15);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT, 1);
			tmpTV.setTextColor(Color.rgb(5, 91, 166));
			tmpTV.setTypeface(null, Typeface.BOLD);
			tmpTV.setLayoutParams(params);
			sideIndex.addView(tmpTV);
			// }
		}

		sideIndexHeight = sideIndex.getHeight();

		sideIndex.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// now you know coordinates of touch
				sideIndexX = event.getX();
				sideIndexY = event.getY();

				// and can display a proper item it country list
				displayListItem();

				return false;
			}
		});

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.findItem(R.id.menu_NewEmails).setVisible(false);
		menu.findItem(R.id.menu_search).setVisible(false);

		menu.findItem(R.id.menu_call).setVisible(false);
		menu.findItem(R.id.menu_block).setVisible(false);
		menu.findItem(R.id.menu_addcontect).setVisible(false);

		menu.findItem(R.id.menu_Edit).setVisible(false);
		menu.findItem(R.id.menu_logout).setVisible(false);
		menu.findItem(R.id.menu_save).setVisible(false);
		super.onPrepareOptionsMenu(menu);
	}

	public void getContacts() {
		Cursor phones = getActivity().getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {

			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

			MyContact mc = new MyContact();
			mc.setName(name);
			mc.setNumber(phoneNumber);
			Number.add(mc);

		}
		phones.close();
	}

	public void fetchContacts() {

		try {
			constant.Set_flag(1, getActivity());

			DBAdapter db = new DBAdapter(getActivity());
			// if (!db.isOpen()) {
			db.open();
			// }
			// db.update_flag(0);
			db.delete_table();

			String phoneNumber = "";
			String email = "";

			Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
			String _ID = ContactsContract.Contacts._ID;
			String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
			String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

			Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
			String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

			Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
			String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
			String DATA = ContactsContract.CommonDataKinds.Email.DATA;

			StringBuffer output = new StringBuffer();

			ContentResolver contentResolver = getActivity()
					.getContentResolver();

			Cursor cursor = contentResolver.query(CONTENT_URI, null, null,
					null, null);

			// Loop for every contact in the phone
			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					// MyContact mc = new MyContact();

					String contact_id = cursor.getString(cursor
							.getColumnIndex(_ID));
					String name = cursor.getString(cursor
							.getColumnIndex(DISPLAY_NAME));

					int hasPhoneNumber = Integer
							.parseInt(cursor.getString(cursor
									.getColumnIndex(HAS_PHONE_NUMBER)));

					if (hasPhoneNumber > 0) {

						output.append("\n First Name:" + name);
						// mc.setName(name.toUpperCase());
						// Query and loop for every phone number of the contact
						Cursor phoneCursor = contentResolver.query(
								PhoneCONTENT_URI, null, Phone_CONTACT_ID
										+ " = ?", new String[] { contact_id },
								null);

						while (phoneCursor.moveToNext()) {
							phoneNumber = phoneCursor.getString(phoneCursor
									.getColumnIndex(NUMBER));
							output.append("\n Phone number:" + phoneNumber);
							// mc.setNumber(phoneNumber);
						}

						phoneCursor.close();

						// get Emails details
						// Query and loop for every email of the contact
						Cursor emailCursor = contentResolver.query(
								EmailCONTENT_URI, null, EmailCONTACT_ID
										+ " = ?", new String[] { contact_id },
								null);

						while (emailCursor.moveToNext()) {

							email = emailCursor.getString(emailCursor
									.getColumnIndex(DATA));
							if (email != null || !email.contentEquals("null")
									|| email.length() != 0) {
								output.append("\nEmail:" + email);
								// mc.setEmail(email);
							}
						}

						emailCursor.close();
						// Number.add(mc);

						db.insert(name.toUpperCase(), phoneNumber, email);
						name = "";
						phoneNumber = "";
						email = "";
					}

					output.append("\n");
				}

				// outputText.setText(output);
			}
			cursor.close();
			// db.update_flag(1);
			Calendar cal = Calendar.getInstance();
			// cal.add(Calendar.HOUR, 1);
			Date addhours = cal.getTime();
			String date2 = (new SimpleDateFormat("yyyy-MM-dd HH:mm"))
					.format(addhours);
			Log.i("System out", "Current time :" + date2);
			// db.update_DateTime(date2);
			db.close();
			constant.Set_flag(0, getActivity());
			read_context_from_database();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public class CustomComparator implements Comparator<MyContact> {
		@Override
		public int compare(MyContact o1, MyContact o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	public class Get_Contacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar1.setVisibility(View.VISIBLE);
			editText1.setEnabled(false);
			editText1.setClickable(false);
			btn_refresh.setEnabled(false);
			btn_refresh.setClickable(false);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Integer flag = constant.get_flag(getActivity());
			if (flag == 0) {
				read_context_from_database();
			} else {
				while (flag == 1) {
					flag = constant.get_flag(getActivity());
					// Log.i("System out", "sdsadsadsa :" + flag);
					if (flag == 0) {
						// if (GC.getStatus() == AsyncTask.Status.FINISHED) {
						// fetchContacts();
						read_context_from_database();
						// }
						break;
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			progressBar1.setVisibility(View.GONE);
			editText1.setEnabled(true);
			editText1.setClickable(true);
			btn_refresh.setEnabled(true);
			btn_refresh.setClickable(true);

			Resources res = getResources();
			if (null != Number && Number.size() != 0) {
				Collections.sort(Number, new Comparator<MyContact>() {

					@Override
					public int compare(MyContact lhs, MyContact rhs) {
						return lhs.getName().compareTo(rhs.getName());
					}
				});

			} else {
				Log.e("No Contact Found!!!", "");
			}

			// ContactAdapter = new MyContectAdapter(getActivity(), Number,
			// res);
			// lst_contacts.setAdapter(ContactAdapter);
			// ContactAdapter.notifyDataSetChanged();

			// ------------

			List<Row> rows = new ArrayList<Row>();
			int start = 0;
			int end = 0;
			String previousLetter = null;
			Object[] tmpIndexItem = null;
			Pattern numberPattern = Pattern.compile("[0-9]");

			MyContact mc = new MyContact();

			// for (String country : Number) {
			// }
			for (int i = 0; i < Number.size(); i++) {
				String st = Number.get(i).getName().toString();
				String number = Number.get(i).getNumber().toString();
				String email = "";
				if (Number.get(i).getEmail() != null) {
					email = Number.get(i).getEmail().toString();
				}

				String firstLetter = st.substring(0, 1);

				// Group numbers together in the scroller
				if (numberPattern.matcher(firstLetter).matches()) {
					firstLetter = "#";
				}

				// If we've changed to a new letter, add the previous letter to
				// the
				// alphabet scroller
				if (previousLetter != null
						&& !firstLetter.equals(previousLetter)) {
					end = rows.size() - 1;
					tmpIndexItem = new Object[3];
					tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
					tmpIndexItem[1] = start;
					tmpIndexItem[2] = end;

					alphabet.add(tmpIndexItem);

					start = end + 1;
				}

				// Check if we need to add a header row
				if (!firstLetter.equals(previousLetter)) {
					rows.add(new Section(firstLetter));
					sections.put(firstLetter, start);
				}

				// Add the country to the list
				rows.add(new Item(st, number, email));
				previousLetter = firstLetter;
			}

			if (previousLetter != null) {
				// Save the last letter
				tmpIndexItem = new Object[3];
				tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
				tmpIndexItem[1] = start;
				tmpIndexItem[2] = rows.size() - 1;
				alphabet.add(tmpIndexItem);
			}

			// ContactAdapter = new MyContectAdapter(getActivity(), Number,
			// res);
			// lst_contacts.setAdapter(ContactAdapter);
			// ContactAdapter.notifyDataSetChanged();
			ContactAdapter = new MyContectAdapter(getActivity(),
					My_Contacts_Fragment.this, Number);
			ContactAdapter.setRows(rows);
			lst_contacts.setAdapter(ContactAdapter);
			// .setListAdapter(ContactAdapter);

			updateList();
			v.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if (mGestureDetector.onTouchEvent(event)) {
						return true;
					} else {
						return false;
					}
					// return false;
				}
			});
			// ---------------------
			super.onPostExecute(result);
		}
	}

	public class Get_Contacts_manually extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar1.setVisibility(View.VISIBLE);
			alphabet.clear();
			editText1.setEnabled(false);
			editText1.setClickable(false);
			btn_refresh.setEnabled(false);
			btn_refresh.setClickable(false);
			lst_contacts.setVisibility(View.GONE);
			sideIndex.setVisibility(View.GONE);

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			fetchContacts();
			read_context_from_database();
			// Integer flag = constant.get_flag(getActivity());
			// if (flag == 0) {
			// read_context_from_database();
			// } else {
			// while (flag == 1) {
			// flag = constant.get_flag(getActivity());
			// // Log.i("System out", "sdsadsadsa :" + flag);
			// if (flag == 0) {
			// // if (GC.getStatus() == AsyncTask.Status.FINISHED) {
			// // }
			// break;
			// }
			// }
			// }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			progressBar1.setVisibility(View.GONE);

			editText1.setEnabled(true);
			editText1.setClickable(true);
			btn_refresh.setEnabled(true);
			btn_refresh.setClickable(true);
			lst_contacts.setVisibility(View.VISIBLE);
			sideIndex.setVisibility(View.VISIBLE);

			Resources res = getResources();
			if (null != Number && Number.size() != 0) {
				Collections.sort(Number, new Comparator<MyContact>() {

					@Override
					public int compare(MyContact lhs, MyContact rhs) {
						return lhs.getName().compareTo(rhs.getName());
					}
				});

			} else {
				Log.e("No Contact Found!!!", "");
			}

			// ContactAdapter = new MyContectAdapter(getActivity(), Number,
			// res);
			// lst_contacts.setAdapter(ContactAdapter);
			// ContactAdapter.notifyDataSetChanged();

			// ------------

			List<Row> rows = new ArrayList<Row>();
			int start = 0;
			int end = 0;
			String previousLetter = null;
			Object[] tmpIndexItem = null;
			Pattern numberPattern = Pattern.compile("[0-9]");

			MyContact mc = new MyContact();

			// for (String country : Number) {
			// }
			for (int i = 0; i < Number.size(); i++) {
				String st = Number.get(i).getName().toString();
				String number = Number.get(i).getNumber().toString();
				String email = "";
				if (Number.get(i).getEmail() != null) {
					email = Number.get(i).getEmail().toString();
				}

				String firstLetter = st.substring(0, 1);

				// Group numbers together in the scroller
				if (numberPattern.matcher(firstLetter).matches()) {
					firstLetter = "#";
				}

				// If we've changed to a new letter, add the previous letter to
				// the
				// alphabet scroller
				if (previousLetter != null
						&& !firstLetter.equals(previousLetter)) {
					end = rows.size() - 1;
					tmpIndexItem = new Object[3];
					tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
					tmpIndexItem[1] = start;
					tmpIndexItem[2] = end;

					alphabet.add(tmpIndexItem);

					start = end + 1;
				}

				// Check if we need to add a header row
				if (!firstLetter.equals(previousLetter)) {
					rows.add(new Section(firstLetter));
					sections.put(firstLetter, start);
				}

				// Add the country to the list
				rows.add(new Item(st, number, email));
				previousLetter = firstLetter;
			}

			if (previousLetter != null) {
				// Save the last letter
				tmpIndexItem = new Object[3];
				tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
				tmpIndexItem[1] = start;
				tmpIndexItem[2] = rows.size() - 1;
				alphabet.add(tmpIndexItem);
			}

			// ContactAdapter = new MyContectAdapter(getActivity(), Number,
			// res);
			// lst_contacts.setAdapter(ContactAdapter);
			// ContactAdapter.notifyDataSetChanged();

			ContactAdapter = new MyContectAdapter(getActivity(),
					My_Contacts_Fragment.this, Number);
			ContactAdapter.setRows(rows);
			lst_contacts.setAdapter(ContactAdapter);
			// .setListAdapter(ContactAdapter);

			updateList();
			v.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if (mGestureDetector.onTouchEvent(event)) {
						return true;
					} else {
						return false;
					}
					// return false;
				}
			});
			// ---------------------
			super.onPostExecute(result);
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			GC.cancel(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			GC.cancel(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void displayListItem() {
		LinearLayout sideIndex = (LinearLayout) v.findViewById(R.id.sideIndex);
		sideIndexHeight = sideIndex.getHeight();
		// compute number of pixels for every side index item
		double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

		// compute the item index for given event position belongs to
		int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

		// get the item (we can do it since we know item index)
		if (itemPosition < alphabet.size()) {
			Object[] indexItem = alphabet.get(itemPosition);
			int subitemPosition = sections.get(indexItem[0]);

			// ListView listView = (ListView) findViewById(android.R.id.list);
			lst_contacts.setSelection(subitemPosition);
		}
	}

	public void read_context_from_database() {
		Log.i("System out", "Read contact from database");
		Number.clear();
		// if (constant.flag) {
		// if (!db.isOpen()) {
		db.open();
		// }
		Cursor c = db.getalldata();
		if (c.getCount() != 0) {
			c.moveToFirst();
			while (c.moveToNext()) {
				MyContact mc = new MyContact();
				String id = c.getString(0);
				String name = c.getString(1);
				String number = c.getString(2);
				String email = c.getString(3);
				// Log.i("System out", "sdsdswd :" + id + ":" + name + ":" +
				// number
				// + ":" + email);
				mc.setId(id);
				mc.setName(name);
				mc.setNumber(number);
				mc.setEmail(email);

				Number.add(mc);
			}
		}
		c.close();
		// db.close();
		// }
	}

	/*
	 * public void show_dialog(final String number, final String Email) { final
	 * Dialog dialog = new Dialog(getActivity(),
	 * android.R.style.Theme_Holo_Dialog);
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * dialog.setContentView(R.layout.my_contacts_dialog);
	 * 
	 * Button btn_call = (Button) dialog.findViewById(R.id.btn_call); Button
	 * btn_text = (Button) dialog.findViewById(R.id.btn_text); Button btn_email
	 * = (Button) dialog.findViewById(R.id.btn_email); Button btn_cancel =
	 * (Button) dialog.findViewById(R.id.btn_cancel);
	 * 
	 * btn_call.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub showFragment(number); dialog.cancel(); } });
	 * btn_text.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub ShowMessageActivity(number); dialog.cancel(); } });
	 * 
	 * btn_email.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub if (Email != null) { ShowEmailActivity(Email); dialog.cancel(); } }
	 * }); btn_cancel.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub dialog.cancel(); } }); dialog.show(); }
	 */

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
	}
}
