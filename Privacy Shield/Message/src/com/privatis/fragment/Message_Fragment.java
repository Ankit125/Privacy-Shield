package com.privatis.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.adapter.CallsAdepter;
import com.privatis.adapter.MessagesAdapter;
import com.privatis.message.Message_Reply_Activity;
import com.privatis.message.R;
import com.privatis.message.Send_Mail_Activity;
import com.privatis.model.Calls;
import com.privatis.model.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Message_Fragment extends Fragment {

    private EditText edt_search;
    private ImageView img_menu, img_message;
    private ListView lst_message;
    public ArrayList<Message> mMessage;
    private MessagesAdapter adapter;
    Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.message, container, false);

        try {
            // constant.Action_bar(false, getActivity(), "Message");

            // TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
            // tv.setText(getArguments().getString("msg"));
            mMessage = new ArrayList<Message>();
//            setListData();
            res = getResources();
            lst_message = (ListView) v.findViewById(R.id.lst_message);
//            adapter = new MessagesAdapter(getActivity(), mMessage, res);
//            lst_message.setAdapter(adapter);

            Get_Message_Data get_message_data = new Get_Message_Data();
            get_message_data.execute();

            lst_message.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String PhoneNumber = mMessage.get(position).getSender_Mobile_Number();
                    Bundle bdl = new Bundle();
                    bdl.putString("PhoneNumber", PhoneNumber);
                    Intent intent = new Intent(getActivity(),
                            Message_Reply_Activity.class);
                    intent.putExtras(bdl);
                    startActivity(intent);
                }
            });
            setHasOptionsMenu(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return v;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static Message_Fragment newInstance(String text) {

        Message_Fragment f = new Message_Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);

        return f;
    }

//    public void setListData() {
//        for (int i = 0; i <= 10; i++) {
//            Message mess = new Message();
//            mess.setPhone("818-221-2414");
//            Log.i("System out", "sdsdsd :" + i % 2);
//            if (i / 2 == 0) {
//                mess.setTime("9:24am");
//            } else {
//                mess.setTime("10:24pm");
//            }
//            mess.setNotes("Other issues of WordTips discuss the undocumented RAND function in Word and how it can be used to generate random text.");
//
//            message.add(mess);
//        }
//    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.findItem(R.id.menu_NewEmails).setVisible(true);
        menu.findItem(R.id.menu_search).setVisible(true);
        menu.findItem(R.id.menu_block).setVisible(false);

        menu.findItem(R.id.menu_NewEmails).setOnMenuItemClickListener(
                new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(getActivity(),
                                Send_Mail_Activity.class);
                        startActivity(intent);
                        return false;
                    }

                });
        menu.findItem(R.id.menu_block).setOnMenuItemClickListener(
                new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        Block_Dialog();
                        return false;
                    }
                });
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        return super.onOptionsItemSelected(item);
    }

    public void Block_Dialog() {

        try {
            final Dialog dialog = new Dialog(getActivity(),
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.block_user_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog.findViewById(R.id.btn_no);
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
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity()
                .getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));

        SearchView.OnQueryTextListener textChangeListener = new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        };

        searchView.setOnQueryTextListener(textChangeListener);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public class Get_Message_Data extends AsyncTask<Void, Void, String> {
        public ProgressDialog progressDialog;
        String MemberId, data;

        public Get_Message_Data() {
            super();
            MemberId = constant.get_Member_Id(getActivity());
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
//              http://privatisapi.aaadev.info/direct.asmx/ListMessage?MemberId=21
                URI website = new URI(getString(R.string.host_name) + "ListMessage?MemberId=" + MemberId);
                Log.i("System out", "Response Message:" + website);
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
                    Log.i("System out", "Response Message:" + data);
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response Message:" + data);
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
                            Message message = new Message();
                            message.setMessageId(c.getString("MessageId"));
                            message.setSender_Mobile_Number(c.getString("Sender Mobile Number"));
                            message.setBody(c.getString("Body"));
                            message.setTime(c.getString("Time"));
                            mMessage.add(message);
                        }

                        adapter = new MessagesAdapter(getActivity(), mMessage, res);
                        lst_message.setAdapter(adapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
