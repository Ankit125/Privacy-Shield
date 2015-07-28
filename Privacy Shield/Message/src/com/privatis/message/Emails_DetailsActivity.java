package com.privatis.message;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.net.URI;
import java.net.URISyntaxException;

public class Emails_DetailsActivity extends FragmentActivity {
    public String messageid, Subject, SenderEmailId, Body, Time;
    public TextView txt_Sender_Email_Id, txt_Subject, txt_time, txt_Body;
    public WebView webView;
    public String data, EmailFrom = "", EmailBody = "";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.email_details_activity);

        try {
            // getActionBar().setBackgroundDrawable(
            // getResources().getDrawable(R.drawable.actionbarbg));
            //
            // getActionBar().setTitle("");
            // // Enable ActionBar app icon to behave as action to toggle nav
            // // drawer
            // getActionBar().setLogo(R.drawable.action_icon);
            // getActionBar().setHomeButtonEnabled(true);
            // getActionBar().setDisplayHomeAsUpEnabled(true);

            Bundle bdl = getIntent().getExtras();
            if (bdl != null) {
                messageid = bdl.getString("messageid");
//                SenderEmailId = bdl.getString("SenderEmailId");
//                Subject = bdl.getString("Subject");
//                Body = bdl.getString("Body");
//                Time = bdl.getString("Time");
            }

            txt_Sender_Email_Id = (TextView) findViewById(R.id.txt_Sender_Email_Id);
            txt_Subject = (TextView) findViewById(R.id.txt_Subject);
            txt_Body = (TextView) findViewById(R.id.txt_Body);
            txt_time = (TextView) findViewById(R.id.txt_time);
            webView = (WebView) findViewById(R.id.webView);


            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            webView.setLongClickable(false);
            webView.setHapticFeedbackEnabled(false);
            ActionBar mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            View mCustomView = mInflater.inflate(R.layout.action_email_details,
                    null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            ImageView img_action_reply = (ImageView) mCustomView
                    .findViewById(R.id.img_action_reply);
            ImageView img_action_forward = (ImageView) mCustomView
                    .findViewById(R.id.img_action_forward);
            ImageView img_action_delete = (ImageView) mCustomView
                    .findViewById(R.id.img_action_delete);
            ImageView img_action_block = (ImageView) mCustomView
                    .findViewById(R.id.img_action_block);
            LinearLayout ll_back = (LinearLayout) mCustomView
                    .findViewById(R.id.ll_back);

            img_action_reply.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Bundle bdl = new Bundle();
                    String emailid = txt_Sender_Email_Id.getText().toString().trim();
                    String Subject = txt_Subject.getText().toString().trim();
                    bdl.putString("mailid", emailid);
                    bdl.putString("subject", Subject);
                    bdl.putString("body", EmailBody);
                    Intent intent = new Intent(Emails_DetailsActivity.this,
                            Email_Replay_Activity.class);
                    intent.putExtras(bdl);
                    startActivity(intent);
                }
            });
            img_action_forward.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Bundle bdl = new Bundle();
                    String emailid = txt_Body.getText().toString().trim();
                    String Subject = txt_Subject.getText().toString().trim();
                    bdl.putString("body", EmailBody);
                    bdl.putString("subject", Subject);
                    Intent intent1 = new Intent(Emails_DetailsActivity.this,
                            Email_Forword_Activity.class);
                    intent1.putExtras(bdl);
                    startActivity(intent1);
                }
            });
            img_action_block.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Block_Dialog();
                }
            });
            ll_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Emails_DetailsActivity.this.finish();
                }
            });

            img_action_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteEmail_Dialog();
                }
            });
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setLogo(R.drawable.action_icon);


//            txt_Sender_Email_Id.setText(SenderEmailId);
//            txt_Subject.setText(Subject);
//            txt_Body.setText(Html.fromHtml(Body));
//            txt_time.setText(Time);
            Get_Email_details get_email_details = new Get_Email_details(messageid);
            get_email_details.execute();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        // MenuInflater inflaters = getMenuInflater();
        // inflaters.inflate(R.menu.emails_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_replay:
                Intent intent = new Intent(Emails_DetailsActivity.this,
                        Email_Replay_Activity.class);
                startActivity(intent);

                break;
            case R.id.menu_forword:
                Intent intent1 = new Intent(Emails_DetailsActivity.this,
                        Email_Forword_Activity.class);
                startActivity(intent1);
                break;
            case android.R.id.home:
                this.finish();
                break;
            case R.id.menu_block:
                Block_Dialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Block_Dialog() {

        try {
            final Dialog dialog = new Dialog(Emails_DetailsActivity.this,
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.block_user_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog.findViewById(R.id.btn_no);

            TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
            textView1.setText("Block Email");

            TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
            textView2
                    .setText("Are you sure you would like to block this email?");
            btn_no.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            btn_yes.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
//                    EmailFrom = "johndoe@privatis.com";
                    Block_Email block_email = new Block_Email(EmailFrom);
                    block_email.execute();
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void DeleteEmail_Dialog() {

        try {
            final Dialog dialog = new Dialog(Emails_DetailsActivity.this,
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.block_user_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog.findViewById(R.id.btn_no);

            TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
            textView1.setText("Delete Email");

            TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
            textView2
                    .setText("Are you sure you would like to delete this email?");
            btn_no.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            btn_yes.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                    Delete_Email delete_email = new Delete_Email();
                    delete_email.execute();
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class Get_Email_details extends AsyncTask<Void, Void, String> {
        String messageid;
        public ProgressDialog progressDialog;

        public Get_Email_details(String messageid) {
            this.messageid = messageid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Emails_DetailsActivity.this);
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
//             URI website = new URI("http://privatisapi.aaadev.info//direct.asmx/SignIn?email=noiknine@privatis.com&password=@Test1234");
//                URI website = new URI("http://50.2.223.175/emailslistprivatis.json");
                URI website = new URI(getString(R.string.host_name) + "RetrieveEmailById?EmailId=" + messageid);
                Log.i("System out", "Response :" + website);
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
                Log.i("System out", "Response :" + data);
                if (data.startsWith("<?")) {
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response :" + data);
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

                    JSONArray contacts = new JSONArray(data);
                    JSONObject c = contacts.getJSONObject(0);
                    if (c.has("status")) {
                        String status = c.getString("status");
                        if (status.contains("not found")) {
                            Toast.makeText(Emails_DetailsActivity.this, "" + status, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String EmailId = c.getString("EmailId");
                        String CreatedDate = c.getString("CreatedDate");
                        String MemberId = c.getString("MemberId");
                        EmailFrom = c.getString("EmailFrom");
                        String EmailTo = c.getString("EmailTo");
                        String EmailSubject = c.getString("EmailSubject");
                        EmailBody = c.getString("EmailBody");

                        txt_Sender_Email_Id.setText(EmailFrom);
                        txt_Subject.setText(EmailSubject);

                        String html = (EmailBody.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
//                        txt_Body.setText(Html.fromHtml(EmailBody));
                        webView.loadData(html, "text/html", "utf-8");
                        int start = CreatedDate.indexOf(" ") + 1;
                        String st = CreatedDate.substring(start, CreatedDate.length());
                        txt_time.setText(st);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class Block_Email extends AsyncTask<Void, Void, String> {

        public ProgressDialog progressDialog;
        public String MemberId, Email, data;

        public Block_Email(String email) {
            this.Email = email;
            MemberId = constant.get_Member_Id(Emails_DetailsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Emails_DetailsActivity.this);
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
//             http://privatisapi.aaadev.info/direct.asmx/BlockEmail?MemberId=string&Email=string
                URI website = new URI(getString(R.string.host_name) + "BlockEmail?MemberId=" + MemberId + "&Email=" + Email);
                Log.i("System out", "Response block :" + website);
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
                Log.i("System out", "Response block :" + data);
                if (data.startsWith("<?")) {
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response block :" + data);
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
                    String status = c.getString("status");
                    if (status.length() != 0 && status.toString().toLowerCase().equalsIgnoreCase("success")) {
                        Toast.makeText(Emails_DetailsActivity.this, "" + getString(R.string.Block_mail), Toast.LENGTH_LONG).show();
                        Emails_DetailsActivity.this.finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class Delete_Email extends AsyncTask<Void, Void, String> {
        public ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Emails_DetailsActivity.this);
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
//            http://privatisapi.aaadev.info/direct.asmx/DeleteEmail?EmailId=string
                URI website = new URI(getString(R.string.host_name) + "DeleteEmail?EmailId=" + messageid);
                Log.i("System out", "Response :" + website);
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
                Log.i("System out", "Response tetete :" + data);
                if (data.startsWith("<?")) {
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response :" + data);
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
                    String status = c.getString("status");
                    if (status.length() != 0 && status.toString().toLowerCase().equalsIgnoreCase("success")) {
                        Toast.makeText(Emails_DetailsActivity.this, "" + getString(R.string.delete_mail), Toast.LENGTH_LONG).show();

                        Emails_DetailsActivity.this.finish();
                        Bundle bdl = new Bundle();
                        bdl.putInt("position", 0);
                        Intent intent = new Intent(Emails_DetailsActivity.this, MainActivity_all.class);
                        intent.putExtras(bdl);
                        startActivity(intent);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
