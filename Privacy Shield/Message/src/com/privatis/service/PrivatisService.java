package com.privatis.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.privaties.common.constant;
import com.privatis.databasae.DBAdapter;
import com.privatis.model.MyContact;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class PrivatisService extends Service {

	private static Timer timer = new Timer();
	private Context context;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		context = this;
		startService();
	}

	private void startService() {
		// 5 minutes
		// timer.scheduleAtFixedRate(new mainTask(), 0, (5000 * 60));
		// DBAdapter db = new DBAdapter(context);
		// if (!db.isOpen()) {
		// db.open();
		// }
		// db.update_flag(0);
		// db.close();
		// 1 hours
		timer.scheduleAtFixedRate(new mainTask(), 0, (1000 * 60 * 60));

	}

	private class mainTask extends TimerTask {
		public void run() {
			// Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
			// DBAdapter db = new DBAdapter(context);
			// db.open();
			// Cursor c = db.getalldatafromsync();
			// String time = c.getString(1);
			// db.close();
			// Calendar cal = Calendar.getInstance();
			// Date date = cal.getTime();
			// String date1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm"))
			// .format(date);
			// Log.i("System out", "Current time :" + date1);
			// if (time.equals(date1)) {
			Log.i("System out", "Synchronous task");
			fetchContacts();
			// }
		}
	}

	public void fetchContacts() {

		try {
			constant.Set_flag(1, context);

			DBAdapter db = new DBAdapter(context);
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

			ContentResolver contentResolver = context.getContentResolver();

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
			constant.Set_flag(0, context);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
