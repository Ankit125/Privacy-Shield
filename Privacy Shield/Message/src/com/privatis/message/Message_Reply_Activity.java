package com.privatis.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.adapter.Message_Replay_Adepter;
import com.privatis.adapter.MessagesAdapter;
import com.privatis.model.Message;
import com.privatis.model.Message_Reply;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Message_Reply_Activity extends FragmentActivity {

    private ListView lst_message;

    private ArrayList<Message_Reply> message;
    private Message_Replay_Adepter adepter;
    public String PhoneNumber;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.message_replay);

        try {
            Bundle bdl = getIntent().getExtras();
            if (bdl != null) {
                PhoneNumber = bdl.getString("PhoneNumber");
            }

            ActionBar mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(
                    R.layout.actionbar_message_reply, null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.txt_title);
            mTitleTextView.setText(PhoneNumber);
            mTitleTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Message_Reply_Activity.this.finish();
                }
            });
            ImageView img_back = (ImageView) mCustomView
                    .findViewById(R.id.img_back);
            img_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Message_Reply_Activity.this.finish();
                }
            });
            ImageView img_block = (ImageView) mCustomView
                    .findViewById(R.id.img_block);
            img_block.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Block_Dialog();
                }
            });

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setLogo(R.drawable.action_icon);

            lst_message = (ListView) findViewById(R.id.lst_message);

            message = new ArrayList<Message_Reply>();

            Get_Message_Thread get_message_thread = new Get_Message_Thread();
            get_message_thread.execute();
//            setListData();
//            adepter = new Message_Replay_Adepter(Message_Reply_Activity.this,
//                    message);
//            lst_message.setAdapter(adepter);

            // ActionBar actionbar = getActionBar();
            // actionbar.setCustomView(R.layout.actionbar_message_reply);
            //
            // TextView txt_title = (TextView)
            // actionbar.getCustomView().findViewById(
            // R.id.txt_title);
            // txt_title.setText("818-221-2414");
            //
            // // actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
            // // | ActionBar.DISPLAY_SHOW_HOME);
            // actionbar.setDisplayShowHomeEnabled(true);
            // actionbar.setDisplayShowTitleEnabled(true);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public void setListData() {
//
//        try {
//            for (int i = 0; i <= 10; i++) {
//                Message_Reply message = new Message_Reply();
//                message.setMessage("");
//                message.setTime("");
//                if (i % 2 == 0) {
//                    message.setFlag(true);
//                } else {
//                    message.setFlag(false);
//                }
//
//                this.message.add(message);
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void Block_Dialog() {

        try {
            final Dialog dialog = new Dialog(Message_Reply_Activity.this,
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.block_user_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog.findViewById(R.id.btn_no);

            TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
            textView1.setText("Block Number");

            TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
            textView2
                    .setText("Are you sure you would like to block this number?");
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
                    Block_Message block_message = new Block_Message();
                    block_message.execute();
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public class Get_Message_Thread extends AsyncTask<Void, Void, String> {
        public ProgressDialog progressDialog;
        String MemberId, data;

        public Get_Message_Thread() {
            super();
            MemberId = constant.get_Member_Id(getApplicationContext());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Message_Reply_Activity.this);
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
//             http://privatisapi.aaadev.info/direct.asmx/RetrieveSMSThreadPhone?MemberId=21&FromPhoneNumber=8186340951
                URI website = new URI(getString(R.string.host_name) + "RetrieveSMSThreadPhone?MemberId=" + MemberId + "&FromPhoneNumber=" + PhoneNumber);
                Log.i("System out", "Response Message Thread:" + website);
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
                    Log.i("System out", "Response Message Thread:" + data);
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response Message Thread:" + data);
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
                    JSONObject c1 = contacts.getJSONObject(0);
                    if (c1.has("status")) {
                        String error = c1.getString("status");
                        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            Message_Reply message_reply = new Message_Reply();
                            message_reply.setSmsId(c.getString("SmsId"));
                            message_reply.setCreatedDate(c.getString("CreatedDate"));
                            message_reply.setMemberId(c.getString("MemberId"));
                            message_reply.setPhoneSmsFrom(c.getString("PhoneSmsFrom"));
                            message_reply.setPhoneSmsTo(c.getString("PhoneSmsTo"));
                            message_reply.setSmsMessage(c.getString("SmsMessage"));
                            message.add(message_reply);
                        }

                        adepter = new Message_Replay_Adepter(Message_Reply_Activity.this,
                                message, PhoneNumber);
                        lst_message.setAdapter(adepter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class Block_Message extends AsyncTask<Void, Void, String> {
        public ProgressDialog progressDialog;
        public String MemberId, data;

        public Block_Message() {
            super();
            MemberId = constant.get_Member_Id(getApplicationContext());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Message_Reply_Activity.this);
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
//               http://privatisapi.aaadev.info/direct.asmx/BlockNumber?MemberId=string&PhoneNumber=string
                URI website = new URI(getString(R.string.host_name) + "BlockNumber?MemberId=" + MemberId + "&PhoneNumber=" + PhoneNumber);
                Log.i("System out", "Response block message :" + website);
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
                    Log.i("System out", "Response block message :" + data);
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response block message :" + data);
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
                        Toast.makeText(Message_Reply_Activity.this, "" + getString(R.string.Block_mail), Toast.LENGTH_LONG).show();
                        Message_Reply_Activity.this.finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
