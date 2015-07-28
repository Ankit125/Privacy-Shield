package com.privatis.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.privatis.message.R;

public class Keypad_Fragment_new extends Fragment implements OnClickListener {

	private LinearLayout ll_one, ll_two, ll_three, ll_four, ll_five, ll_six,
			ll_seven, ll_eight, ll_nine, ll_zero, ll_star, ll_hash;
	private TextView txt_number;
	private ImageView img_remove;
	private Button btn_call;
	private LinearLayout ll_delete, ll_Call;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		View v = inflater.inflate(R.layout.keypad, container, false);
		View v = inflater.inflate(R.layout.simple_dialer, container, false);
		try {
			setHasOptionsMenu(true);

			String PhoneNumber = getArguments().getString("PhoneNumber");
			// Toast.makeText(getActivity(), PhoneNumber,
			// Toast.LENGTH_LONG).show();

			ll_one = (LinearLayout) v.findViewById(R.id.ll_one);
			ll_two = (LinearLayout) v.findViewById(R.id.ll_two);
			ll_three = (LinearLayout) v.findViewById(R.id.ll_three);
			ll_four = (LinearLayout) v.findViewById(R.id.ll_four);
			ll_five = (LinearLayout) v.findViewById(R.id.ll_five);
			ll_six = (LinearLayout) v.findViewById(R.id.ll_six);
			ll_seven = (LinearLayout) v.findViewById(R.id.ll_seven);
			ll_eight = (LinearLayout) v.findViewById(R.id.ll_eight);
			ll_nine = (LinearLayout) v.findViewById(R.id.ll_nine);
			ll_zero = (LinearLayout) v.findViewById(R.id.ll_zero);
			ll_hash = (LinearLayout) v.findViewById(R.id.ll_hash);
			ll_star = (LinearLayout) v.findViewById(R.id.ll_star);
			txt_number = (TextView) v.findViewById(R.id.txt_number);
			if (PhoneNumber != null && PhoneNumber.length() != 0) {
				txt_number.setText(PhoneNumber);
			}

			// img_remove = (ImageView) v.findViewById(R.id.img_remove);
			ll_delete = (LinearLayout) v.findViewById(R.id.ll_delete);
			ll_delete.setOnClickListener(this);
			ll_Call = (LinearLayout) v.findViewById(R.id.ll_Call);
			ll_Call.setOnClickListener(this);
			// btn_call = (Button) v.findViewById(R.id.btn_call);

			ll_one.setOnClickListener(this);
			ll_two.setOnClickListener(this);
			ll_three.setOnClickListener(this);
			ll_four.setOnClickListener(this);
			ll_five.setOnClickListener(this);
			ll_six.setOnClickListener(this);
			ll_seven.setOnClickListener(this);
			ll_eight.setOnClickListener(this);
			ll_nine.setOnClickListener(this);
			ll_zero.setOnClickListener(this);
			ll_star.setOnClickListener(this);
			ll_hash.setOnClickListener(this);
			// img_remove.setOnClickListener(this);
			// btn_call.setOnClickListener(this);

			// img_remove.setOnLongClickListener(new OnLongClickListener() {

			// @Override
			// public boolean onLongClick(View v) {
			// // TODO Auto-generated method stub
			// txt_number.setText("");
			// return false;
			// }
			// });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	public static Keypad_Fragment_new newInstance(String text) {

		Keypad_Fragment_new f = new Keypad_Fragment_new();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// String st = getArguments().getString("msg");
	// Toast.makeText(getActivity(), st, Toast.LENGTH_LONG).show();
	// super.onCreate(savedInstanceState);
	// }

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		try {
			menu.findItem(R.id.menu_NewEmails).setVisible(false);
			menu.findItem(R.id.menu_search).setVisible(false);
			menu.findItem(R.id.menu_keypad_add).setVisible(true);
			super.onPrepareOptionsMenu(menu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String number;
		switch (v.getId()) {
		case R.id.ll_one:
			number = txt_number.getText().toString().trim();
			number = number + "1";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_two:
			number = txt_number.getText().toString().trim();
			number = number + "2";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_three:
			number = txt_number.getText().toString().trim();
			number = number + "3";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_four:
			number = txt_number.getText().toString().trim();
			number = number + "4";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_five:
			number = txt_number.getText().toString().trim();
			number = number + "5";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_six:
			number = txt_number.getText().toString().trim();
			number = number + "6";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_seven:
			number = txt_number.getText().toString().trim();
			number = number + "7";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_eight:
			number = txt_number.getText().toString().trim();
			number = number + "8";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_nine:
			number = txt_number.getText().toString().trim();
			number = number + "9";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_zero:
			number = txt_number.getText().toString().trim();
			number = number + "0";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_hash:
			number = txt_number.getText().toString().trim();
			number = number + "#";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		case R.id.ll_star:
			number = txt_number.getText().toString().trim();
			number = number + "*";
			txt_number.setText(number);
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		// case R.id.img_remove:
		// String st = txt_number.getText().toString().trim();
		// txt_number.setText(remove_number(st));
		// break;
		case R.id.ll_delete:
			String st = txt_number.getText().toString().trim();
			txt_number.setText(remove_number(st));
			v.playSoundEffect(SoundEffectConstants.CLICK);
			break;
		// case R.id.btn_call:
		// String num = txt_number.getText().toString().trim();
		// if (num.length() != 0) {
		// Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
		// + num));
		// startActivity(intent);
		// }
		// break;
		case R.id.ll_Call:
			v.playSoundEffect(SoundEffectConstants.CLICK);
			String num = txt_number.getText().toString().trim();
			if (num.length() != 0) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ num));
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	public String remove_number(String st) {
		String s = "";
		if (st != null && st.length() != 0) {
			s = st.substring(0, st.length() - 1);
		}
		return s;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.menu_keypad_add) {
			My_Contacts_Fragment cFragment = new My_Contacts_Fragment();
			FragmentManager fragmentManager = getActivity()
					.getSupportFragmentManager();
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.replace(R.id.llContainer, cFragment);
			ft.commit();
		}
		return super.onOptionsItemSelected(item);
	}
}
