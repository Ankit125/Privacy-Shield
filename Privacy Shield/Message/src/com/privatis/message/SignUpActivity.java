package com.privatis.message;

import com.privaties.common.constant;
import com.privatis.message.R.color;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class SignUpActivity extends Activity {
    private EditText edt_fname, edt_lname, edt_email, edt_phonenumber, edt_password;
    private TextView textView1;
    private Button btn_signup;
    String data;
    private static final String OPERATION_NAME = "SignUp";
//    private static final String SOAP_ACTION = "http://pc.aaadev.info/";
//    private static final String WSDL_TARGET_NAMESPACE = "http://privatisapi.aaadev.info/";
//    private static final String SOAP_ADDRESS = "http://privatisapi.aaadev.info/direct.asmx?op=";


//    private static final String MAIN_REQUEST_URL = "http://privatisapi.aaadev.info/direct.asmx";
//    private static final String NAMESPACE = "http://privatisapi.aaadev.info/";
//    private static final String SOAP_ACTION = "http://pc.aaadev.info/";

//    http://privatisapi.aaadev.info/direct.asmx?op=SignUp
//    http://batch.aaadev.info/email.asmx?op=SendCalls"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.sign_in_actionbar, null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.txt_title);
            mTitleTextView.setText("Sign Up");
            mActionBar.setLogo(R.drawable.action_icon);

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);

            edt_fname = (EditText) findViewById(R.id.edt_fname);
            edt_lname = (EditText) findViewById(R.id.edt_lname);
            edt_email = (EditText) findViewById(R.id.edt_email);
            edt_phonenumber = (EditText) findViewById(R.id.edt_phonenumber);
            edt_password = (EditText) findViewById(R.id.edt_password);

            btn_signup = (Button) findViewById(R.id.btn_signup);


            edt_email.setHintTextColor(color.blue);
            edt_password.setHintTextColor(color.blue);
            textView1 = (TextView) findViewById(R.id.textView1);
            String htmlText = "<html>By logging in you are agreeing to\nPrivacy Shield's <a href=\"http://www.google.com/\">Terms &amp; Privacy</a></html>";
            textView1.setText(Html.fromHtml(htmlText));
            textView1.setClickable(true);
            textView1.setMovementMethod(LinkMovementMethod.getInstance());

            btn_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fname = edt_email.getText().toString().trim();
                    String lname = edt_lname.getText().toString().trim();
                    String email = edt_email.getText().toString().trim();
                    String number = edt_phonenumber.getText().toString().trim();
                    String password = edt_password.getText().toString().trim();
                    if (fname.length() == 0) {
                        Toast.makeText(SignUpActivity.this, getString(R.string.fname_validation), Toast.LENGTH_LONG).show();
                        edt_fname.requestFocus();
                    } else if (lname.length() == 0) {
                        Toast.makeText(SignUpActivity.this, getString(R.string.lname_validation), Toast.LENGTH_LONG).show();
                        edt_lname.requestFocus();
                    } else if (email.length() == 0) {
                        Toast.makeText(SignUpActivity.this, getString(R.string.email_validation), Toast.LENGTH_LONG).show();
                    } else if (!constant.isEmailValid(email)) {
                        Toast.makeText(SignUpActivity.this, getString(R.string.email_validation), Toast.LENGTH_LONG).show();
                    } else if (number.length() == 0) {
                        Toast.makeText(SignUpActivity.this, getString(R.string.phone_validation), Toast.LENGTH_LONG).show();
                        edt_phonenumber.requestFocus();
                    } else if (password.length() == 0) {
                        Toast.makeText(SignUpActivity.this, getString(R.string.password_validation), Toast.LENGTH_LONG).show();
                    } else {
//                        call_webservices SU = new call_webservices(email, password);
//                        SU.execute();
                        constant.hide_keyboard(SignUpActivity.this);
                        call_SignUp signup = new call_SignUp(fname, lname, email, number, password);
                        signup.execute();
                    }
                }
            });

        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public class call_webservices extends AsyncTask<Void, Void, String> {
        public String email, password, Http_response;
        public ProgressDialog progressDialog;

        public call_webservices(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(SignUpActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            // http://www.codeproject.com/Articles/304302/Calling-Asp-Net-Webservice-ASMX-From-an-Android-Ap
            SoapObject request = new SoapObject(constant.WSDL_TARGET_NAMESPACE,
                    OPERATION_NAME);

            PropertyInfo pi_email = new PropertyInfo();
            pi_email.setName("User");
            pi_email.setValue(this.email);
            pi_email.setType(String.class);
            request.addProperty(pi_email);

            PropertyInfo pi_password = new PropertyInfo();
            pi_password.setName("Password");
            pi_password.setValue(this.password);
            pi_password.setType(String.class);
            request.addProperty(pi_password);

            Log.i("System out", "Response :" + request);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
//            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(constant.SOAP_ADDRESS + OPERATION_NAME, 60000);
            Object response = null;

            try {
                httpTransport.debug = true;
                httpTransport.call(constant.SOAP_ACTION + OPERATION_NAME, envelope);
                response = envelope.getResponse();
                Log.i("System out", "Response :" + response.toString());
                Http_response = response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "" + Http_response, Toast.LENGTH_LONG).show();
//            try {
//
//                JSONObject job = new JSONObject(Http_response);
////                String Error="1223";
////                if(job.has("Error")) {
//                String Error = job.getString("Error");
////                }
//                Toast.makeText(SignUpActivity.this, "" + Error, Toast.LENGTH_LONG).show();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

    }


    public class call_SignUp extends AsyncTask<Void, Void, String> {
        public String fname, lname, email, number, password;
        public ProgressDialog progressDialog;

        public call_SignUp(String fname, String lname, String email, String number, String password) {
            this.fname = fname;
            this.lname = lname;
            this.email = email;
            this.number = number;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(SignUpActivity.this);
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
                URI website = new URI(getString(R.string.host_name) + "SignUp?FirstName=" + fname + "&LastName=" + lname + "&Email=" + email + "&Phone=" + number + "&Password=" + password);
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
            String status = "", Member_Id = "", error = "";
            try {
                if (data.startsWith("[{")) {
                    JSONObject jobject = new JSONObject();
                    JSONArray contacts = new JSONArray(data);
                    JSONObject c = contacts.getJSONObject(0);
                    if (c.has("Error")) {
                        error = c.getString("Error");
                    } else {
                        status = c.getString("status");
                        Member_Id = c.getString("Member_Id");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status.length() != 0 && status.toString().toLowerCase().equalsIgnoreCase("success")) {
                constant.Set_Member_Id(Member_Id, SignUpActivity.this);
                SignUpActivity.this.finish();
                Intent intent = new Intent(SignUpActivity.this,
                        MainActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(SignUpActivity.this, "" + error, Toast.LENGTH_LONG).show();
                Show_error(error);
            }
        }
    }

    public void Show_error(String message) {

        try {
            final Dialog dialog = new Dialog(SignUpActivity.this,
                    android.R.style.Theme_Holo_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.invalid_user_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog.findViewById(R.id.btn_no);

            TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
//            textView1.setText("Block Number");

            TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
            textView2.setText(message);

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
                }
            });
            // ...

            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
