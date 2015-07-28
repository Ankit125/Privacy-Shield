package com.privatis.fragment;

import com.privaties.common.constant;
import com.privatis.adapter.CallsAdepter;
import com.privatis.adapter.Email_Block_Adepter;
import com.privatis.message.R;
import com.privatis.model.Calls;
import com.privatis.model.Email_Block;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class Blocked_Emails extends Fragment {


    private ListView lst_block_emails;
    private Email_Block_Adepter adapter;
    private ArrayList<Email_Block> mEmail;
    Resources res;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater
                .inflate(R.layout.blockcallsfragment, container, false);
        try {
            setHasOptionsMenu(true);
            // return super.onCreateView(inflater, container,
            // savedInstanceState);
            // --------------
            ActionBar mActionBar = getActivity().getActionBar();
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setLogo(R.drawable.action_icon);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(getActivity());

            View mCustomView = mInflater.inflate(
                    R.layout.block_call__emails_actionbar, null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.txt_title);
            mTitleTextView.setText("Blocked Emails");

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);


            res = getResources();
            mEmail = new ArrayList<Email_Block>();
            lst_block_emails = (ListView) v.findViewById(R.id.lst_block_number);

            get_Block_list_email get_block_list_email = new get_Block_list_email();
            get_block_list_email.execute();

        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        try {
            menu.findItem(R.id.menu_NewEmails).setVisible(false);
            menu.findItem(R.id.menu_search).setVisible(false);

            menu.findItem(R.id.menu_call).setVisible(false);
            menu.findItem(R.id.menu_block).setVisible(false);
            menu.findItem(R.id.menu_addcontect).setVisible(false);

            menu.findItem(R.id.menu_Edit).setVisible(false);
            menu.findItem(R.id.menu_logout).setVisible(false);
            menu.findItem(R.id.menu_save).setVisible(false);
            super.onPrepareOptionsMenu(menu);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class get_Block_list_email extends AsyncTask<Void, Void, String> {

        public ProgressDialog progressDialog;
        String Member_id, data;

        public get_Block_list_email() {
            super();
            Member_id = constant.get_Member_Id(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(getActivity());
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
//               http://privatisapi.aaadev.info/direct.asmx/BlockEmailList?MemberId=10
                URI website = new URI(getString(R.string.host_name) + "BlockEmailList?MemberId=" + Member_id);
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
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            Email_Block email_block = new Email_Block();
                            email_block.setEmailid(c.getString("EmailId"));
                            email_block.setEmail(c.getString("Email"));
                            email_block.setDate(c.getString("Date"));
                            email_block.setTime(c.getString("Time"));
                            mEmail.add(email_block);
                        }

                        adapter = new Email_Block_Adepter(getActivity(), mEmail, res);
                        lst_block_emails.setAdapter(adapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
