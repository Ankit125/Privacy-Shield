package com.privatis.fragment;

import com.privaties.common.constant;
import com.privatis.adapter.Calls_Block_Adepter;
import com.privatis.adapter.Email_Block_Adepter;
import com.privatis.message.R;
import com.privatis.model.Calls;
import com.privatis.model.Calls_Block;
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

public class Blocked_Calls extends Fragment {


    private ListView lst_block_emails;
    private Calls_Block_Adepter adapter;
    private ArrayList<Calls_Block> mCall_Block;
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
            mTitleTextView.setText("Blocked Numbers");

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);

            res = getResources();
            mCall_Block = new ArrayList<Calls_Block>();
            lst_block_emails = (ListView) v.findViewById(R.id.lst_block_number);


            get_Block_list_calls get_block_list_calls = new get_Block_list_calls();
            get_block_list_calls.execute();
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


    public class get_Block_list_calls extends AsyncTask<Void, Void, String> {

        public ProgressDialog progressDialog;
        String Member_id, data;

        public get_Block_list_calls() {
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
                URI website = new URI(getString(R.string.host_name) + "BlockNumberList?MemberId=" + Member_id);
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
                            Calls_Block calls_block = new Calls_Block();
                            calls_block.setBlockedOptoutId(c.getString("BlockedOptoutId"));
                            calls_block.setCreatedDate(c.getString("CreatedDate"));
                            calls_block.setMemberId(c.getString("MemberId"));
                            calls_block.setPhoneNumber(c.getString("PhoneNumber"));
                            mCall_Block.add(calls_block);
                        }

                        adapter = new Calls_Block_Adepter(getActivity(), mCall_Block, res);
                        lst_block_emails.setAdapter(adapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
