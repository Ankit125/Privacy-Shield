package com.privatis.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.privatis.message.R;

public class Keypad_Fragment extends Fragment implements OnClickListener {
	private Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six,
			btn_seven, btn_eight, btn_nine, btn_zero, btn_has, btn_star,
			btn_call;
	private TextView txt_number;
	private ImageView img_remove;

	// private OutgoingCallReceiver outgoingReceiver;
	private String start_time, end_time, Name, Number;

	private static final String SOAP_ACTION = "http://batch.aaadev.info/SendCalls";
	private static final String OPERATION_NAME = "SendCalls";
	private static final String WSDL_TARGET_NAMESPACE = "http://batch.aaadev.info/";
	private static final String SOAP_ADDRESS = "http://batch.aaadev.info/email.asmx?op=SendCalls";
	private ProgressBar progressBar1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.dialer, container, false);

		btn_one = (Button) v.findViewById(R.id.btn_one);
		btn_two = (Button) v.findViewById(R.id.btn_two);
		btn_three = (Button) v.findViewById(R.id.btn_three);
		btn_four = (Button) v.findViewById(R.id.btn_four);
		btn_five = (Button) v.findViewById(R.id.btn_five);
		btn_six = (Button) v.findViewById(R.id.btn_six);
		btn_seven = (Button) v.findViewById(R.id.btn_seven);
		btn_eight = (Button) v.findViewById(R.id.btn_eight);
		btn_nine = (Button) v.findViewById(R.id.btn_nine);
		btn_zero = (Button) v.findViewById(R.id.btn_zero);
		btn_star = (Button) v.findViewById(R.id.btn_star);
		btn_has = (Button) v.findViewById(R.id.btn_has);
		btn_one = (Button) v.findViewById(R.id.btn_one);
		btn_call = (Button) v.findViewById(R.id.btn_call);
		img_remove = (ImageView) v.findViewById(R.id.img_remove);

		txt_number = (TextView) v.findViewById(R.id.txt_number);

		progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
		progressBar1.setVisibility(View.GONE);

		btn_one.setOnClickListener(this);
		btn_two.setOnClickListener(this);
		btn_three.setOnClickListener(this);
		btn_four.setOnClickListener(this);
		btn_five.setOnClickListener(this);
		btn_six.setOnClickListener(this);
		btn_seven.setOnClickListener(this);
		btn_eight.setOnClickListener(this);
		btn_nine.setOnClickListener(this);
		btn_zero.setOnClickListener(this);
		btn_star.setOnClickListener(this);
		btn_has.setOnClickListener(this);
		btn_call.setOnClickListener(this);
		img_remove.setOnClickListener(this);

		return v;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	public static Keypad_Fragment newInstance(String text) {

		Keypad_Fragment f = new Keypad_Fragment();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String number;
		switch (v.getId()) {
		case R.id.btn_one:
			number = txt_number.getText().toString().trim();
			number = number + "1";
			txt_number.setText(number);
			break;
		case R.id.btn_two:
			number = txt_number.getText().toString().trim();
			number = number + "2";
			txt_number.setText(number);
			break;
		case R.id.btn_three:
			number = txt_number.getText().toString().trim();
			number = number + "3";
			txt_number.setText(number);
			break;
		case R.id.btn_four:
			number = txt_number.getText().toString().trim();
			number = number + "4";
			txt_number.setText(number);
			break;
		case R.id.btn_five:
			number = txt_number.getText().toString().trim();
			number = number + "5";
			txt_number.setText(number);
			break;
		case R.id.btn_six:
			number = txt_number.getText().toString().trim();
			number = number + "6";
			txt_number.setText(number);
			break;
		case R.id.btn_seven:
			number = txt_number.getText().toString().trim();
			number = number + "7";
			txt_number.setText(number);
			break;
		case R.id.btn_eight:
			number = txt_number.getText().toString().trim();
			number = number + "8";
			txt_number.setText(number);
			break;
		case R.id.btn_nine:
			number = txt_number.getText().toString().trim();
			number = number + "9";
			txt_number.setText(number);
			break;
		case R.id.btn_zero:
			number = txt_number.getText().toString().trim();
			number = number + "0";
			txt_number.setText(number);
			break;
		case R.id.btn_star:
			number = txt_number.getText().toString().trim();
			number = number + "*";
			txt_number.setText(number);
			break;
		case R.id.btn_has:
			number = txt_number.getText().toString().trim();
			number = number + "#";
			txt_number.setText(number);

			break;

		case R.id.btn_call:
			number = txt_number.getText().toString().trim();
			Number = txt_number.getText().toString().trim();
			if (number != null && number.length() == 0) {
				Toast.makeText(getActivity(), "You missed to type the number!",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ number));
				startActivity(intent);
			}
			break;
		case R.id.img_remove:
			String st = txt_number.getText().toString().trim();
			txt_number.setText(remove_number(st));
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
}
