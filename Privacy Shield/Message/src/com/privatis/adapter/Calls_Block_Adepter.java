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

import com.privaties.common.constant;
import com.privatis.message.R;
import com.privatis.model.Calls_Block;
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
public class Calls_Block_Adepter extends BaseAdapter {

    private Activity activity;
    public Resources res;
    private static LayoutInflater inflater = null;
    private ArrayList<Calls_Block> calls_blocks;

    public Calls_Block_Adepter(Activity activity, ArrayList<Calls_Block> calls_blocks, Resources res) {
        super();

        this.activity = activity;
        this.calls_blocks = calls_blocks;
        this.res = res;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return calls_blocks.size();
    }

    @Override
    public Object getItem(int position) {
        return calls_blocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView txt_Number, txt_date_time;
        public Button btn_unblock;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        try {
            ViewHolder holder;
            vi = inflater.inflate(R.layout.list_calls_blocks, null);
            holder = new ViewHolder();
            holder.txt_Number = (TextView) vi.findViewById(R.id.txt_number);
            holder.txt_date_time = (TextView) vi.findViewById(R.id.txt_time);


            holder.txt_Number.setText(calls_blocks.get(position).getPhoneNumber());
            holder.txt_date_time.setText(calls_blocks.get(position).getCreatedDate());

            holder.btn_unblock = (Button) vi.findViewById(R.id.btn_unblock);
            holder.btn_unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String BlockedOptoutId = calls_blocks.get(position).getBlockedOptoutId();
//                    Toast.makeText(activity, "" + email, Toast.LENGTH_LONG).show();

//                    calls_blocks.remove(position);
                    Block_Dialog(position, BlockedOptoutId);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vi;
    }


    public void Block_Dialog(final int position, final String BlockedOptoutId) {

        try {
            final Dialog dialog = new Dialog(activity,
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.block_user_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog.findViewById(R.id.btn_no);
            TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
            textView1.setText("Unblock number");
            TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);

            textView2
                    .setText("Are you sure you would like to unblock this number?");
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
                    Unblock_Number unblock_number = new Unblock_Number(BlockedOptoutId, position);
                    unblock_number.execute();
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class Unblock_Number extends AsyncTask<Void, Void, String> {

        public ProgressDialog progressDialog;
        public String BlockedOptoutId, data;
        public int position;

        public Unblock_Number(String BlockedOptoutId, int position) {
            super();
            this.BlockedOptoutId = BlockedOptoutId;
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
                URI website = new URI(activity.getString(R.string.host_name) + "UnBlockPhoneNumber?PhoneNumberId=" + BlockedOptoutId);
                Log.i("System out", "Response block Number:" + website);
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
                    Log.i("System out", "Response block Number:" + data);
                    String xmlresponse = data;
                    int start = xmlresponse.toString().indexOf(">") + 1;
                    int end = xmlresponse.toString().indexOf("</");
                    data = xmlresponse.toString().substring(start, end);

                    int start1 = data.toString().indexOf(">") + 1;
                    data = data.substring(start1, data.length());
                    Log.i("System out", "Response block Number:" + data);
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
                        calls_blocks.remove(position);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            notifyDataSetChanged();
        }
    }
}
