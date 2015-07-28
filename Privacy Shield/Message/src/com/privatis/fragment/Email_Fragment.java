package com.privatis.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.actionbarsherlock.app.SherlockFragment;
//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuInflater;
//import com.actionbarsherlock.view.MenuItem;
//import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.privaties.common.constant;
import com.privatis.adapter.EmailAdapter;
import com.privatis.message.ComposeEmailActivity;
import com.privatis.message.Emails_DetailsActivity;
import com.privatis.message.R;
import com.privatis.model.Email;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Email_Fragment extends Fragment {
    private EditText edt_search;
    private ImageView img_menu, img_message;
    private ListView lst_message;
    public ArrayList<Email> mEmail;
    private EmailAdapter adapter;
    Resources res;
    public String data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.message, container, false);

        try {
            // constant.Action_bar(false, getActivity(), "Message");

            // TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
            // tv.setText(getArguments().getString("msg"));
            mEmail = new ArrayList<Email>();
//            setListData();
            res = getResources();
            lst_message = (ListView) v.findViewById(R.id.lst_message);
//            adapter = new EmailAdapter(getActivity(), mEmail, res);
//            lst_message.setAdapter(adapter);

            Get_Email_data email = new Get_Email_data();
            email.execute();

            lst_message.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String messageid = mEmail.get(position).getMessageId();
                    String SenderEmailId = mEmail.get(position).getSender_Email_Id();
                    String Subject = mEmail.get(position).getSubject();
                    String Body = mEmail.get(position).getBody();
                    String Time = mEmail.get(position).getTime();

                    Log.i("System out", "Click on :" + messageid);

                    Bundle bdl = new Bundle();
                    bdl.putString("messageid", messageid);
//                    bdl.putString("SenderEmailId", SenderEmailId);
//                    bdl.putString("Subject", Subject);
//                    bdl.putString("Body", Body);
//                    bdl.putString("Time", Time);
                    Intent intent = new Intent(getActivity(),
                            Emails_DetailsActivity.class);
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
    }

    public static Email_Fragment newInstance(String text) {

        Email_Fragment f = new Email_Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

//    public void setListData() {
//        try {
//            for (int i = 0; i <= 10; i++) {
//                Email mess = new Email();
//                mess.setEmail("johndoe@privatis.com");
//                mess.setName("Acure NSX for Sale");
//                Log.i("System out", "sdsdsd :" + i % 2);
//                if (i / 2 == 0) {
//                    mess.setTime("9:24am");
//                    mess.setFlag("Received");
//                } else {
//                    mess.setTime("10:24pm");
//                    mess.setFlag("send");
//                }
//                mess.setNotes("Other issues of WordTips discuss the undocumented RAND function in Word and how it can be used to generate random text.");
//                mEmail.add(mess);
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    // @Override
    // public void onPrepareOptionsMenu(Menu menu) {
    // // TODO Auto-generated method stub
    // super.onPrepareOptionsMenu(menu);
    //
    // menu.findItem(R.id.menu_NewEmails).setVisible(false);
    // menu.findItem(R.id.menu_search).setVisible(false);
    //
    // }

    // @Override
    // public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // // TODO Auto-generated method stub
    // super.onCreateOptionsMenu(menu, inflater);
    // inflater.inflate(R.menu.activity_main_actions, menu);
    //
    // }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        try {
            menu.findItem(R.id.menu_NewEmails).setVisible(true);
            menu.findItem(R.id.menu_search).setVisible(true);
            menu.findItem(R.id.menu_block).setVisible(false);
            menu.findItem(R.id.menu_NewEmails).setOnMenuItemClickListener(
                    new OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(getActivity(),
                                    ComposeEmailActivity.class);
                            startActivity(intent);
                            return false;
                        }

                        // @Override
                        // public boolean onMenuItemClick(
                        // com.actionbarsherlock.view.MenuItem item) {
                        // // TODO Auto-generated method stub
                        // // Toast.makeText(getActivity(), "test app",
                        // // Toast.LENGTH_LONG).show();
                        // Intent intent = new Intent(getActivity(),
                        // ComposeEmailActivity.class);
                        // startActivity(intent);
                        // return false;
                        // }
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
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public class Get_Email_data extends AsyncTask<Void, Void, String> {
        public ProgressDialog progressDialog;

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
//             URI website = new URI("http://privatisapi.aaadev.info//direct.asmx/SignIn?email=noiknine@privatis.com&password=@Test1234");
//                URI website = new URI("http://50.2.223.175/emailslistprivatis.json");
//                URI website = new URI("http://50.2.223.175/emaillistjson_faulty.json");
                URI website = new URI(getString(R.string.host_name) + "ListEmails?memberId=" + constant.get_Member_Id(getActivity()));
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
                    if (c1.has("Error")) {
                        String error = c1.getString("Error");
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i <= contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            Email email = new Email();
                            email.setMessageId(c.getString("MessageId"));
                            email.setSender_Email_Id(c.getString("Sender Email Id"));
                            email.setSubject(c.getString("Subject"));
                            email.setBody(c.getString("Body"));
                            email.setTime(c.getString("Time"));
                            mEmail.add(email);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new EmailAdapter(getActivity(), mEmail, res);
            lst_message.setAdapter(adapter);

        }
    }
}
