package com.privatis.message;

import android.app.Activity;
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

public class ComposeEmailActivity extends FragmentActivity {

    private EditText edt_to, edt_subject, edt_compose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composeemail);

        try {
            Bundle bdl = getIntent().getExtras();
            String Email = "";
            if (bdl != null) {
                Email = bdl.getString("Email");
            }

            edt_to = (EditText) findViewById(R.id.edt_to);
            edt_to.setText(Email);
            edt_subject = (EditText) findViewById(R.id.edt_subject);
            edt_compose = (EditText) findViewById(R.id.edt_compose);

//            edt_to.setHintTextColor(Color.rgb(33, 116, 184));
//            edt_subject.setHintTextColor(Color.rgb(33, 116, 184));
//            edt_compose.setHintTextColor(Color.rgb(33, 116, 184));

            getActionBar().setBackgroundDrawable(
                    getResources().getDrawable(R.drawable.actionbarbg));

            // Enable ActionBar app icon to behave as action to toggle nav
            // drawer
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setLogo(R.drawable.action_icon);

//            String MaskEmail = constant.get_MaskedEmail(ComposeEmailActivity.this);
//            Toast.makeText(ComposeEmailActivity.this, "" + MaskEmail, Toast.LENGTH_LONG).show();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        MenuInflater inflaters = getMenuInflater();
        inflaters.inflate(R.menu.compose_email, menu);
        return super.onCreateOptionsMenu(menu);
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


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.menu_sendEmails).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String MaskEmail = constant.get_MaskedEmail(ComposeEmailActivity.this);
                String To = edt_to.getText().toString().trim();
                String Subject = edt_subject.getText().toString().trim();
                String Body = edt_compose.getText().toString().trim();

                if (To.length() == 0 || !constant.isEmailValid(To)) {
                    Toast.makeText(ComposeEmailActivity.this, "" + getString(R.string.To), Toast.LENGTH_LONG).show();
                    edt_to.requestFocus();
                } else if (Subject.length() == 0) {
                    Toast.makeText(ComposeEmailActivity.this, "" + getString(R.string.Subject), Toast.LENGTH_LONG).show();
                    edt_subject.requestFocus();
                } else if (Body.length() == 0) {
                    Toast.makeText(ComposeEmailActivity.this, "" + getString(R.string.Body), Toast.LENGTH_LONG).show();
                    edt_compose.requestFocus();
                } else {
                    constant.hide_keyboard(ComposeEmailActivity.this);
                    Send_Mail send_mail = new Send_Mail(MaskEmail, To, Subject, Body);
                    send_mail.execute();
                }
                return false;
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }


    public class Send_Mail extends AsyncTask<Void, Void, String> {
        public String MaskEmail, To, Subject, Body, data, MemberId;
        public ProgressDialog progressDialog;

        public Send_Mail(String MaskEmail, String To, String Subject, String Body) {
            try {
                this.MaskEmail = MaskEmail;
                this.To = To;
                this.Subject = URLEncoder.encode(Subject, "utf-8");
                this.Body = URLEncoder.encode(Body, "utf-8");
                MemberId = constant.get_Member_Id(ComposeEmailActivity.this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(ComposeEmailActivity.this);
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
                        Toast.makeText(ComposeEmailActivity.this, "" + getString(R.string.Success_send), Toast.LENGTH_LONG).show();
                        ComposeEmailActivity.this.finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
