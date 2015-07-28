package com.privatis.message;

import com.privaties.common.constant;
import com.privatis.message.R.color;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class SignInActivity extends FragmentActivity {

    private EditText edt_email, edt_password;
    private TextView txt_forgetpassword;
    private Button btn_signin;


    private static final String OPERATION_NAME = "SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.sign_in_actionbar,
                    null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.txt_title);
            mTitleTextView.setText("Sign In");

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setLogo(R.drawable.action_icon);

            edt_email = (EditText) findViewById(R.id.edt_email);
            edt_password = (EditText) findViewById(R.id.edt_password);
            btn_signin = (Button) findViewById(R.id.btn_signin);

            edt_email.setHintTextColor(color.blue);
            edt_password.setHintTextColor(color.blue);

            String forget = "Forget Password?";
            SpannableString spna_forget = new SpannableString(forget);
            spna_forget
                    .setSpan(new UnderlineSpan(), 0, spna_forget.length(), 0);

            txt_forgetpassword = (TextView) findViewById(R.id.txt_forget);
            txt_forgetpassword.setText(spna_forget);
            txt_forgetpassword.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(SignInActivity.this,
                            ForgetPasswordActivity.class);
                    startActivity(intent);
                }
            });

            btn_signin.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String email = edt_email.getText().toString().trim();
                    String password = edt_password.getText().toString().trim();
                    if (email.length() == 0) {
                        Toast.makeText(SignInActivity.this, getString(R.string.email_validation), Toast.LENGTH_LONG).show();
                        edt_email.requestFocus();
                    } else if (!constant.isEmailValid(email)) {
                        Toast.makeText(SignInActivity.this, getString(R.string.email_validation), Toast.LENGTH_LONG).show();
                        edt_email.requestFocus();
                    } else if (password.length() == 0) {
                        Toast.makeText(SignInActivity.this, getString(R.string.password_validation), Toast.LENGTH_LONG).show();
                        edt_password.requestFocus();
                    } else {
//                        call_webservices call = new call_webservices(email, password);
//                        call.execute();
                        constant.hide_keyboard(SignInActivity.this);
                        call_SignIn signin = new call_SignIn(email, password);
                        signin.execute();
//                        Intent intent = new Intent(SignInActivity.this,
//                                MainActivity.class);
//                        startActivity(intent);
                    }


                }
            });
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public class call_webservices extends AsyncTask<Void, Void, String> {
        String email, password, response;
        public ProgressDialog progressDialog;

        public call_webservices(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(SignInActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

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
            Object obj_response = null;

            try {
                httpTransport.debug = true;
                httpTransport.call(constant.SOAP_ACTION + OPERATION_NAME, envelope);
                obj_response = envelope.getResponse();
                Log.i("System out", "Response :" + obj_response.toString());
                response = obj_response.toString();
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
            Toast.makeText(SignInActivity.this, "" + response, Toast.LENGTH_LONG).show();
        }
    }


    public class call_SignIn extends AsyncTask<Void, Void, String> {
        String email, password;
        public ProgressDialog progressDialog;
        String data;

        public call_SignIn(String email, String password) {
            this.email = email;
            this.password = password;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(SignInActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
//                http://stackoverflow.com/questions/13705494/android-http-get-request
                BufferedReader in = null;

                HttpClient client = new DefaultHttpClient();
//            client.getConnectionManager().getSchemeRegistry().register(getMockedScheme());

//                URI website = new URI("http://privatisapi.aaadev.info//direct.asmx/SignIn?email=noiknine@privatis.com&password=@Test1234");
                URI website = new URI(getString(R.string.host_name) + "SignIn?email=" + email + "&password=" + password);
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
                constant.Set_Member_Id(Member_Id, SignInActivity.this);
                SignInActivity.this.finish();
                Intent intent = new Intent(SignInActivity.this,
                        MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(SignInActivity.this, "" + error, Toast.LENGTH_LONG).show();
            }
        }
    }
}
