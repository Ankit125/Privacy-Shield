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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.adapter.CallsAdepter;
import com.privatis.adapter.Message_Replay_Adepter;
import com.privatis.fragment.Calls_Fragment;
import com.privatis.model.Calls;
import com.privatis.model.Message_Reply;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Call_DetailsActivity extends FragmentActivity {
    public String CallId;
    public TextView txt_phonenumber, txt_notes;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.call_details);

        try {
            Bundle bdl = getIntent().getExtras();
            if (bdl != null) {
                CallId = bdl.getString("CallId");
            }

            ActionBar mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_call_details,
                    null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            LinearLayout ll_back = (LinearLayout) mCustomView
                    .findViewById(R.id.ll_back);
            ImageView img_action_block = (ImageView) mCustomView
                    .findViewById(R.id.img_action_block);

            ImageView img_action_delete = (ImageView) mCustomView
                    .findViewById(R.id.img_action_delete);

            ll_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Call_DetailsActivity.this.finish();
                }
            });
            img_action_block.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Block_Dialog();
                }
            });

            img_action_delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Add_caller_Dialog();
                }
            });
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setLogo(R.drawable.action_icon);

            txt_phonenumber = (TextView) findViewById(R.id.txt_phonenumber);
            txt_notes = (TextView) findViewById(R.id.txt_notes);

            Get_Call_Details getCallDetails = new Get_Call_Details(CallId);
            getCallDetails.execute();

        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public void Block_Dialog() {

        try {
            final Dialog dialog = new Dialog(Call_DetailsActivity.this,
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
                    Block_Calls block_calls = new Block_Calls(txt_phonenumber.getText().toString().trim());
                    block_calls.execute();
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void Add_caller_Dialog() {
        try {
            final Dialog dialog = new Dialog(Call_DetailsActivity.this,
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.add_caller_dialog);

            EditText edt_name = (EditText) dialog.findViewById(R.id.editText1);
            Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
            Button btn_ok = (Button) dialog.findViewById(R.id.bn_ok);

            btn_cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            btn_ok.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public class Get_Call_Details extends AsyncTask<Void, Void, String> {
        String CallId, data;
        public ProgressDialog progressDialog;

        public Get_Call_Details(String CallId) {
            this.CallId = CallId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Call_DetailsActivity.this);
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
//                http://privatisapi.aaadev.info/direct.asmx/CallsDetails?CallId=2
                URI website = new URI(getString(R.string.host_name) + "CallsDetails?CallId=" + CallId);
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
                if (data.startsWith("<?")) {
                    Log.i("System out", "Response :" + data);
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
                    JSONObject c1 = contacts.getJSONObject(0);
                    if (c1.has("status")) {
                        String error = c1.getString("status");
                        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                    } else {
//                        for (int i = 0; i <= contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(0);
                        txt_phonenumber.setText(c.getString("Number"));
                        txt_notes.setText(c.getString("Notes"));
//                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class Block_Calls extends AsyncTask<Void, Void, String> {

        String phonenumber, data, MemberID;
        public ProgressDialog progressDialog;

        public Block_Calls(String number) {
            this.phonenumber = number;
            this.MemberID = constant.get_Member_Id(Call_DetailsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(Call_DetailsActivity.this);
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
//                http://privatisapi.aaadev.info/direct.asmx/CallsDetails?CallId=2
                URI website = new URI(getString(R.string.host_name) + "BlockNumber?MemberId=" + MemberID + "&PhoneNumber=" + phonenumber);
                Log.i("System out", "Response block calls :" + website);
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
                    Log.i("System out", "Response block calls :" + data);
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response block calls :" + data);
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
                        Toast.makeText(Call_DetailsActivity.this, "" + getString(R.string.Block_mail), Toast.LENGTH_LONG).show();
                        Call_DetailsActivity.this.finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
