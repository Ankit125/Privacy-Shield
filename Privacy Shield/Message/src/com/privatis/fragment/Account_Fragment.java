package com.privatis.fragment;

import android.accounts.Account;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.message.OpeningActivity;
import com.privatis.message.R;

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

public class Account_Fragment extends Fragment implements OnClickListener {

    Menu Mmenu;
    private EditText edt_fname, edt_lname, edt_remail, edt_masked_email,
            edt_rphone, edt_masked_phone, edt_accounttype, edt_accountsttus;
    private TextView txt_expirationdate;

    public int year;
    public int month;
    public int day;
    static final int DATE_PICKER_ID = 1111;
    Boolean flag = true;
    String data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View v = inflater.inflate(R.layout.account_fragment, container, false);

        try {
            // return super.onCreateView(inflater, container,
            // savedInstanceState);
            setHasOptionsMenu(true);

            edt_fname = (EditText) v.findViewById(R.id.edt_fname);
            edt_lname = (EditText) v.findViewById(R.id.edt_lname);
            edt_remail = (EditText) v.findViewById(R.id.edt_remail);
            edt_masked_email = (EditText) v.findViewById(R.id.edt_memail);
            edt_rphone = (EditText) v.findViewById(R.id.edt_rphone);
            edt_masked_phone = (EditText) v.findViewById(R.id.edt_mphone);
            edt_accounttype = (EditText) v.findViewById(R.id.edt_accounttype);
            edt_accountsttus = (EditText) v
                    .findViewById(R.id.edt_accountstatus);
            txt_expirationdate = (TextView) v
                    .findViewById(R.id.edt_expirationdate);
            // txt_expirationdate.setOnClickListener(this);

            Edittext_Enable_Disable(false);

            edt_masked_phone.setKeyListener(null);
            edt_accounttype.setKeyListener(null);
            edt_accountsttus.setKeyListener(null);
            txt_expirationdate.setKeyListener(null);
            // --------------
            ActionBar mActionBar = getActivity().getActionBar();
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            // mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setLogo(R.drawable.action_icon);
            LayoutInflater mInflater = LayoutInflater.from(getActivity());

            View mCustomView = mInflater.inflate(R.layout.actionbar_myaccount,
                    null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.txt_title);
            mTitleTextView.setText("My Account");

            final ImageView img_edit = (ImageView) mCustomView
                    .findViewById(R.id.img_edit);

            img_edit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (flag) {
                        Edittext_Enable_Disable(true);
                        img_edit.setImageResource(R.drawable.action_save_account);
                        flag = false;

                    } else {
                        Edittext_Enable_Disable(false);
                        img_edit.setImageResource(R.drawable.action_edit_account);
                        flag = true;
                        Toast.makeText(getActivity(), "Update Account", Toast.LENGTH_LONG).show();
                        String Member_id = constant.get_Member_Id(getActivity());
                        String Fname = edt_fname.getText().toString().trim();
                        String lname = edt_lname.getText().toString().trim();
                        String email = edt_remail.getText().toString().trim();
                        String phone = edt_rphone.getText().toString().trim();
                        if (Fname.length() == 0) {
                            Toast.makeText(getActivity(), "" + getString(R.string.fname_validation), Toast.LENGTH_LONG).show();
                        } else if (lname.length() == 0) {
                            Toast.makeText(getActivity(), "" + getString(R.string.lname_validation), Toast.LENGTH_LONG).show();
                        } else if (email.length() == 0 && !constant.isEmailValid(email)) {
                            Toast.makeText(getActivity(), "" + getString(R.string.email_validation), Toast.LENGTH_LONG).show();
                        } else if (phone.length() == 0) {
                            Toast.makeText(getActivity(), "" + getString(R.string.phone_validation), Toast.LENGTH_LONG).show();
                        } else {
                            constant.hide_keyboard(getActivity());
                            Update_Account UA = new Update_Account(Member_id, Fname, lname, email, phone);
                            UA.execute();
                        }

                    }
                }
            });
            ImageView img_logout = (ImageView) mCustomView
                    .findViewById(R.id.img_logout);
            img_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constant.Set_Member_Id("", getActivity());
                    Intent intent = new Intent(getActivity(),
                            OpeningActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });


            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);

            get_Member_data get_data = new get_Member_data(constant.get_Member_Id(getActivity()));
            get_data.execute();
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
            Mmenu = menu;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            // case android.R.id.home:
            // getActivity().finish();
            // break;
            case R.id.menu_Edit:
                Edittext_Enable_Disable(true);

                Mmenu.findItem(R.id.menu_NewEmails).setVisible(false);
                Mmenu.findItem(R.id.menu_search).setVisible(false);

                Mmenu.findItem(R.id.menu_call).setVisible(false);
                Mmenu.findItem(R.id.menu_block).setVisible(false);
                Mmenu.findItem(R.id.menu_addcontect).setVisible(false);

                Mmenu.findItem(R.id.menu_Edit).setVisible(false);
                Mmenu.findItem(R.id.menu_logout).setVisible(true);
                Mmenu.findItem(R.id.menu_save).setVisible(true);

                break;
            case R.id.menu_save:
                Edittext_Enable_Disable(false);

                Mmenu.findItem(R.id.menu_NewEmails).setVisible(false);
                Mmenu.findItem(R.id.menu_search).setVisible(false);

                Mmenu.findItem(R.id.menu_call).setVisible(false);
                Mmenu.findItem(R.id.menu_block).setVisible(false);
                Mmenu.findItem(R.id.menu_addcontect).setVisible(false);

                Mmenu.findItem(R.id.menu_Edit).setVisible(true);
                Mmenu.findItem(R.id.menu_logout).setVisible(true);
                Mmenu.findItem(R.id.menu_save).setVisible(false);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    public void Edittext_Enable_Disable(Boolean flag) {
        try {
            edt_fname.setEnabled(flag);
            edt_lname.setEnabled(flag);
            edt_remail.setEnabled(flag);
            edt_masked_email.setEnabled(flag);
            edt_rphone.setEnabled(flag);
            edt_masked_phone.setEnabled(flag);
            edt_accounttype.setEnabled(flag);
            edt_accountsttus.setEnabled(flag);
            txt_expirationdate.setClickable(flag);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class get_Member_data extends AsyncTask<Void, Void, String> {

        String Member_id;
        public ProgressDialog progressDialog;

        public get_Member_data(String Member_id) {
            this.Member_id = Member_id;
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
//             URI website = new URI("http://privatisapi.aaadev.info//direct.asmx/SignIn?email=noiknine@privatis.com&password=@Test1234");
                URI website = new URI(getString(R.string.host_name) + "RetrieveAccount?MemberId=" + Member_id);
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
            String error;
            try {
                if (data.startsWith("[{")) {
                    JSONObject jobject = new JSONObject();
                    JSONArray contacts = new JSONArray(data);
                    JSONObject c = contacts.getJSONObject(0);
                    if (c.has("Error")) {
                        error = c.getString("Error");
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                    } else {
                        String FirstName = c.getString("FirstName");
                        String LastName = c.getString("LastName");
                        String RealEmail = c.getString("RealEmail");
                        String MaskedEmail = c.getString("MaskedEmail");
                        String RealPhone = c.getString("RealPhone");
                        String MaskedPhone = c.getString("MaskedPhone");
                        String AccountType = c.getString("AccountType");
                        String AccountStatus = c.getString("AccountStatus");
//                    Member_Id = c.getString("Member_Id");

                        edt_fname.setText(FirstName);
                        edt_lname.setText(LastName);
                        edt_remail.setText(RealEmail);
                        edt_masked_email.setText(MaskedEmail);
                        edt_rphone.setText(RealPhone);
                        edt_masked_phone.setText(MaskedPhone);
                        edt_accounttype.setText(AccountType);
                        edt_accountsttus.setText(AccountStatus);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class Update_Account extends AsyncTask<Void, Void, String> {
        public String MemberId, FirstName, LastName, Email, RealPhone;
        public ProgressDialog progressDialog;

        public Update_Account(String MemberId, String fname, String lname, String email, String rphone) {
            this.MemberId = MemberId;
            this.FirstName = fname;
            this.LastName = lname;
            this.Email = email;
            this.RealPhone = rphone;
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
                URI website = new URI(getString(R.string.host_name) + "UpdateAccount?MemberId=" + MemberId + "&FirstName=" + FirstName + "&LastName=" + LastName + "&Email=" + Email + "&RealPhone=" + RealPhone);
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
                    JSONObject c = contacts.getJSONObject(0);
                    String status = c.getString("Status");
                    if (status.length() != 0 && status.toString().toLowerCase().equalsIgnoreCase("success")) {
                        Toast.makeText(getActivity(), "" + getString(R.string.Success_update), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
