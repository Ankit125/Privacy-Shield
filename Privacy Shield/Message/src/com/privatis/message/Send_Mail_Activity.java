package com.privatis.message;

import android.app.ActionBar;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.privatis.fragment.My_Contacts_Fragment;

public class Send_Mail_Activity extends FragmentActivity {
	private EditText edt_number;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.send_message);

		try {
			Bundle bdl = getIntent().getExtras();
			String number = "";
			if (bdl != null) {
				number = bdl.getString("Number");
			}
			ActionBar mActionBar = getActionBar();
			mActionBar.setDisplayShowHomeEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setLogo(R.drawable.action_icon);
			// mActionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater mInflater = LayoutInflater.from(this);

			View mCustomView = mInflater.inflate(R.layout.send_message_actionbar,
					null);

			mActionBar.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.actionbarbg));

			TextView mTitleTextView = (TextView) mCustomView
					.findViewById(R.id.txt_title);
			mTitleTextView.setText("Message");

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);

			edt_number = (EditText) findViewById(R.id.edt_number);
			edt_number.setText(number);

			ImageView imageView2 = (ImageView) mCustomView
					.findViewById(R.id.img_contact);
			imageView2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					My_Contacts_Fragment cFragment = new My_Contacts_Fragment();
					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.llContainer, cFragment);
					ft.commit();
				}
			});
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflaters = getMenuInflater();
		inflaters.inflate(R.menu.activity_main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.findItem(R.id.menu_NewEmails).setVisible(false);
		menu.findItem(R.id.menu_search).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

}
