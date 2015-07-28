package com.privatis.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.privatis.fragment.Keypad_Fragment_new;
import com.privatis.message.ComposeEmailActivity;
import com.privatis.message.R;
import com.privatis.message.Send_Mail_Activity;
import com.privatis.model.MyContact;

public class MyContectAdapter extends BaseAdapter {
	Activity activity;
	Fragment fragment;
	public List<MyContact> Number;
	public ArrayList<MyContact> original_Number;
	Resources resourse;
	private static LayoutInflater inflater = null;

	public static abstract class Row {
	}

	public static final class Section extends Row {
		public final String text;

		public Section(String text) {
			this.text = text;
		}
	}

	public static final class Item extends Row {
		public final String Name;
		public final String Number;
		public final String email;

		public Item(String name, String number, String email) {
			this.Name = name;
			this.Number = number;
			this.email = email;
		}
	}

	private List<Row> rows;

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		// return super.getItemViewType(position);
		if (getItem(position) instanceof Section) {
			return 1;
		} else {
			return 0;
		}
	}

	public MyContectAdapter(Activity activity, Fragment f,
			List<MyContact> Number) {
		this.activity = activity;
		this.fragment = f;
		this.Number = Number;
		original_Number = new ArrayList<MyContact>();
		original_Number.addAll(Number);
	}

	// ----------------------
	// public MyContectAdapter(Activity activity, List<MyContact> Number,
	// Resources resourse) {
	// this.activity = activity;
	// this.Number = Number;
	// this.resourse = resourse;
	// inflater = (LayoutInflater) activity
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//
	// original_Number = new ArrayList<MyContact>();
	// original_Number.addAll(Number);
	// }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return Number.size();
		return rows.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		// return Number.get(arg0);
		return rows.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public static class ViewHolder {

		public TextView txt_number, txt_Name, txt_email;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;

		try {
			if (getItemViewType(position) == 0) { // Item
				ViewHolder holder;
				// if (convertView == null) {

				/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
				LayoutInflater inflater = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				vi = inflater.inflate(R.layout.list_contects, null);

				/****** View Holder Object to contain tabitem.xml file elements ******/

				holder = new ViewHolder();
				final Item item = (Item) getItem(position);
				holder.txt_number = (TextView) vi.findViewById(R.id.txt_number);
				// holder.txt_number.setText(Number.get(position).getNumber());
				if (item.Number.length() == 0) {
					holder.txt_number.setVisibility(View.GONE);
				} else {
					holder.txt_number.setText(item.Number);
				}
				holder.txt_Name = (TextView) vi.findViewById(R.id.txt_Name);
				// holder.txt_Name.setText(Number.get(position).getName());
				if (item.Name.length() == 0)
					holder.txt_Name.setVisibility(View.GONE);
				else
					holder.txt_Name.setText(item.Name);

				holder.txt_email = (TextView) vi.findViewById(R.id.txt_email);
				// holder.txt_email.setText(Number.get(position).getEmail());
				if (item.email != null && item.email.length() == 0)
					holder.txt_email.setVisibility(View.GONE);
				else
					holder.txt_email.setText(item.email);
				// } else {
				// holder = (ViewHolder) vi.getTag();
				// }

				vi.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						show_dialog(item.Number, item.email);
					}
				});

			} else {
				// if (vi == null) {
				LayoutInflater inflater = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				vi = (LinearLayout) inflater.inflate(R.layout.row_section,
						parent, false);
				// }

				Section section = (Section) getItem(position);
				TextView textView = (TextView) vi.findViewById(R.id.textView1);
				textView.setText(section.text);
				textView.setVisibility(View.GONE);
				vi.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vi;

	}

	public void filter(String charText) {
		try {
			// http://www.androidbegin.com/tutorial/android-search-listview-using-filter/
			charText = charText.toLowerCase(Locale.getDefault());
			Number.clear();
			List<Row> rows = new ArrayList<Row>();
			if (charText.length() == 0) {
				Number.addAll(original_Number);
				int i = 0;
				for (MyContact MC : original_Number) {
					Number.add(MC);
					rows.add(new Item(Number.get(i).getName(), Number.get(i)
							.getNumber(), Number.get(i).getEmail()));
					i++;
				}
				setRows(rows);
			} else {
				int i = 0;
				for (MyContact MC : original_Number) {
					if (MC.getName().toLowerCase(Locale.getDefault())
							.contains(charText)) {
						Number.add(MC);
						rows.add(new Item(Number.get(i).getName(), Number
								.get(i).getNumber(), Number.get(i).getEmail()));
						i++;
					}
				}
				setRows(rows);
			}
			notifyDataSetChanged();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void show_dialog(final String number, final String Email) {
		final Dialog dialog = new Dialog(activity,
				android.R.style.Theme_Holo_Dialog);
		try {
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.my_contacts_dialog);

			Button btn_call = (Button) dialog.findViewById(R.id.btn_call);
			Button btn_text = (Button) dialog.findViewById(R.id.btn_text);
			Button btn_email = (Button) dialog.findViewById(R.id.btn_email);
			Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

			btn_call.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showFragment(number);
					// Toast.makeText(activity, number,
					// Toast.LENGTH_LONG).show();
					dialog.cancel();
				}
			});
			btn_text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ShowMessageActivity(number);
					dialog.cancel();
				}
			});

			btn_email.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (Email != null) {
						ShowEmailActivity(Email);
						dialog.cancel();
					}
				}
			});
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			dialog.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showFragment(String PhoneNumber) {

		try {
			Keypad_Fragment_new cFragment = new Keypad_Fragment_new();
			// Creating a Bundle object
			Bundle data = new Bundle();
			// Setting the index of the currently selected item of mDrawerList
			data.putString("PhoneNumber", PhoneNumber);
			// Setting the position to the fragment
			cFragment.setArguments(data);
			// cFragment.
			// Getting reference to the FragmentManager
			FragmentManager fragmentManager = fragment.getFragmentManager();// getSupportFragmentManager();
			// Creating a fragment transaction
			FragmentTransaction ft = fragmentManager.beginTransaction();
			// Adding a fragment to the fragment transaction
			// ft.replace(R.id.content_frame, cFragment);
			ft.replace(R.id.llContainer, cFragment);
			// Committing the transaction
			ft.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ShowMessageActivity(String Number) {
		try {
			Intent intent = new Intent(activity, Send_Mail_Activity.class);
			Bundle bdl = new Bundle();
			bdl.putString("Number", Number);
			intent.putExtras(bdl);
			activity.startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ShowEmailActivity(String Email) {
		try {
			Intent intent = new Intent(activity, ComposeEmailActivity.class);
			Bundle bdl = new Bundle();
			bdl.putString("Email", Email);
			intent.putExtras(bdl);
			activity.startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
