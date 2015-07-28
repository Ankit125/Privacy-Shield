package com.privatis.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.message.R;
import com.privatis.model.Email_Block;

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

/**
 * Created by Privatis on 07/28/2015.
 */
public class Email_Block_Adepter extends BaseAdapter {

    private Activity activity;
    public Resources res;
    private static LayoutInflater inflater = null;
    private ArrayList<Email_Block> emails;


    public Email_Block_Adepter(Activity activity, ArrayList<Email_Block> emails, Resources res) {
        super();

        this.activity = activity;
        this.emails = emails;
        this.res = res;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return emails.size();
    }

    @Override
    public Object getItem(int position) {
        return emails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView txt_Email, txt_date_time;
        public Button btn_unblock;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        try {
            ViewHolder holder;
            vi = inflater.inflate(R.layout.list_calls_blocks, null);
            holder = new ViewHolder();
            holder.txt_Email = (TextView) vi.findViewById(R.id.txt_number);
            holder.txt_date_time = (TextView) vi.findViewById(R.id.txt_time);


            holder.txt_Email.setText(emails.get(position).getEmail());
            holder.txt_date_time.setText(emails.get(position).getDate() + "" + emails.get(position).getTime());

            holder.btn_unblock = (Button) vi.findViewById(R.id.btn_unblock);
            holder.btn_unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emails.get(position).getEmailid();
//                    Toast.makeText(activity, "" + email, Toast.LENGTH_LONG).show();
                    Block_Dialog(position, email);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vi;
    }


    public void Block_Dialog(final int position, final String email) {

        try {
            final Dialog dialog = new Dialog(activity,
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.block_user_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog.findViewById(R.id.btn_no);
            TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
            textView1.setText("Unblock email");
            TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);

            textView2
                    .setText("Are you sure you would like to unblock this email?");
            btn_no.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            btn_yes.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                    Unblock_email unblock_email = new Unblock_email(email, position);
                    unblock_email.execute();
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class Unblock_email extends AsyncTask<Void, Void, String> {

        public ProgressDialog progressDialog;
        public String Email, data;
        public int position;

        public Unblock_email(String email, int position) {
            super();
            this.Email = email;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(activity);
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
//                 http://privatisapi.aaadev.info/direct.asmx/UnBlockEmail?EmailId=
                URI website = new URI(activity.getString(R.string.host_name) + "UnBlockEmail?EmailId=" + Email);
                Log.i("System out", "Response block email:" + website);
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
                    Log.i("System out", "Response block email:" + data);
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response block email:" + data);
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
//                        Toast.makeText(Email_Forword_Activity.this, "" + getString(R.string.Success_send), Toast.LENGTH_LONG).show();
//                        Email_Forword_Activity.this.finish();
                        emails.remove(position);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            notifyDataSetChanged();
        }
    }

}
