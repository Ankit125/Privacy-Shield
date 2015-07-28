package com.privatis.message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.service.PrivatisService;

public class SpleshActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);


//        Toast.makeText(SpleshActivity.this, "" + constant.get_Member_Id(SpleshActivity.this), Toast.LENGTH_LONG).show();
        try {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        Thread.sleep(3000);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Intent intent;
                                if (constant.get_Member_Id(SpleshActivity.this).equalsIgnoreCase("")) {
                                    intent = new Intent(SpleshActivity.this,
                                            OpeningActivity.class);
                                } else {
                                    intent = new Intent(SpleshActivity.this,
                                            MainActivity.class);
                                }
                                startActivity(intent);
                                SpleshActivity.this.finish();

                            }
                        });
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

            // DBAdapter db = new DBAdapter(getApplicationContext());
            // db.open();
            // String currentDateTimeString =
            // DateFormat.getDateTimeInstance().format(
            // new Date());

            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            String date1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm"))
                    .format(date);
            Log.i("System out", "Current time :" + date1);
            // add one hours
            // cal.add(Calendar.HOUR, -1);
            // Date addhours = cal.getTime();
            // String date2 = (new SimpleDateFormat("yyyy-MM-dd HH:mm"))
            // .format(addhours);
            // Log.i("System out", "Current time :" + date2);

            // db.insert_sync(0, date1);// for testing
            // db.insert_sync(1, date1);
            // db.close();
            if (!isMyServiceRunning(PrivatisService.class)) {
                startService(new Intent(SpleshActivity.this,
                        PrivatisService.class));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	/*
     * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.splesh, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
