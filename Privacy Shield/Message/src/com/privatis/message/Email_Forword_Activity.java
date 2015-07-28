package com.privatis.message;

import android.app.ProgressDialog;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class Email_Forword_Activity extends FragmentActivity {
    private EditText edt_email, edt_subject;
    private EditText edt_body;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.email_forword_activity);

        try {
            String body = "", subject = "";
            Bundle bdl = getIntent().getExtras();
            if (bdl != null) {
                body = bdl.getString("body");
                subject = bdl.getString("subject");
            }

            edt_email = (EditText) findViewById(R.id.edt_email);
            edt_subject = (EditText) findViewById(R.id.edt_subject);
            edt_body = (EditText) findViewById(R.id.txt_body);

            edt_subject.setText("Fwd: " + subject);
            edt_body.setText(body);
//            edt_body.setSelection(0);

//			edt_email.setHintTextColor(Color.parseColor("#bbcfdf"));
//			edt_subject.setHintTextColor(Color.parseColor("#bbcfdf"));


            edt_subject.setKeyListener(null);

            getActionBar().setBackgroundDrawable(
                    getResources().getDrawable(R.drawable.actionbarbg));

            getActionBar().setTitle("Forward");

            // Enable ActionBar app icon to behave as action to toggle nav
            // drawer
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setLogo(R.drawable.action_icon);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        try {
            MenuInflater inflaters = getMenuInflater();
            inflaters.inflate(R.menu.emails_details, menu);

            menu.findItem(R.id.menu_forword).setVisible(false);
            menu.findItem(R.id.menu_replay).setVisible(false);
            menu.findItem(R.id.menu_delete).setVisible(true);
            menu.findItem(R.id.menu_send).setVisible(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.menu_send).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String To = edt_email.getText().toString().trim();
                String subject = edt_subject.getText().toString().trim();
                String body = edt_body.getText().toString().trim();
                if (To.length() == 0 || !constant.isEmailValid(To)) {
                    Toast.makeText(getApplicationContext(), "" + getString(R.string.email_validation), Toast.LENGTH_LONG).show();
                    edt_email.requestFocus();
                } else {
                    constant.hide_keyboard(Email_Forword_Activity.this);
                    Send_Mail send_mail = new Send_Mail(To, subject, body);
                    send_mail.execute();
                }
                return false;
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public class Send_Mail extends AsyncTask<Void, Void, String> {
        public String MaskEmail, To, Subject, Body, data, MemberId;
        public ProgressDialog progressDialog;

        public Send_Mail(String To, String Subject, String Body) {
            try {
                this.MaskEmail = constant.get_MaskedEmail(Email_Forword_Activity.this);
                this.To = To;
                this.Subject = URLEncoder.encode(Subject, "utf-8");
                this.Body = URLEncoder.encode(Body, "utf-8");
                MemberId = constant.get_Member_Id(Email_Forword_Activity.this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Email_Forword_Activity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                BufferedReader in = null;
                HttpClient client = new DefaultHttpClient();
                URI website = new URI(getString(R.string.host_name) + "SendingEmail?MemberId=" + MemberId + "&EmailFrom=" + MaskEmail + "&EmailTo=" + To + "&EmailSubject=" + Subject + "&EmailBody=" + Body + "&action=" + URLEncoder.encode("Sending Email", "utf-8"));

                Log.i("System out", "Response Email :" + website);
                HttpGet request = new HttpGet();
                request.setURI(website);
                HttpResponse response = client.execute(request);
                response.getStatusLine().getStatusCode();

                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String l = "";
                String nl = System.getProperty("line.separator");
                while ((l = in.readLine()) != null) {
                    sb.append(l + nl);
                }
                in.close();
                data = sb.toString();
                if (data.startsWith("<?")) {
                    Log.i("System out", "Response Email :" + data);
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response Email :" + data);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            try {
                if (data.startsWith("[{")) {
                    JSONObject jobject = new JSONObject();
                    JSONArray contacts = new JSONArray(data);
                    JSONObject c = contacts.getJSONObject(0);
                    String status = c.getString("Status");
                    if (status.length() != 0 && status.toString().toLowerCase().equalsIgnoreCase("success")) {
                        Toast.makeText(Email_Forword_Activity.this, "" + getString(R.string.Success_send), Toast.LENGTH_LONG).show();
                        Email_Forword_Activity.this.finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
