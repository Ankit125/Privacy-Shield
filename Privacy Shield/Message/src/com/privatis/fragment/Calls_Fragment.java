package com.privatis.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.privaties.common.constant;
import com.privatis.adapter.CallsAdepter;
import com.privatis.message.Call_DetailsActivity;
import com.privatis.message.MainActivity_all;
import com.privatis.message.R;
import com.privatis.model.Calls;
import com.privatis.model.Email;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Calls_Fragment extends Fragment {

    private ListView lst_call;
    private CallsAdepter adapter;
    private ArrayList<Calls> mCalls;
    Resources res;
    LinearLayout ll_calldetails;
    Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View v = inflater.inflate(R.layout.callactivity, container, false);
        try {
            // TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
            // tv.setText(getArguments().getString("msg"));
            // calls = new ArrayList<Calls>();

            ll_calldetails = (LinearLayout) v.findViewById(R.id.ll_calldetails);

//            setlist();
            res = getResources();
            mCalls = new ArrayList<Calls>();
            lst_call = (ListView) v.findViewById(R.id.lst_calls);
//            res = getResources();
//            adapter = new CallsAdepter(getActivity(), calls, res);
//            lst_call.setAdapter(adapter);

            Get_Call_List get_call_list = new Get_Call_List();
            get_call_list.execute();


            lst_call.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    // showFragment();

                    try {
                        // MainActivity_all.flag = 2;
                        // lst_call.setVisibility(View.GONE);
                        // ll_calldetails.setVisibility(View.VISIBLE);
                        //
                        // menu.findItem(R.id.menu_NewEmails).setVisible(false);
                        // menu.findItem(R.id.menu_search).setVisible(false);
                        //
                        // menu.findItem(R.id.menu_call).setVisible(true);
                        // menu.findItem(R.id.menu_block).setVisible(true);
                        // menu.findItem(R.id.menu_addcontect).setVisible(true);

                        // Toast.makeText(getActivity(), "test",
                        // Toast.LENGTH_LONG).show();
                        // String _id = calls.get(position).getId();
                        // Bundle bdl = new Bundle();
                        // bdl.putString("ID", _id);
                        Bundle bdl = new Bundle();
                        bdl.putString("CallId", mCalls.get(position).getCallId());
                        Intent intent = new Intent(getActivity(),
                                Call_DetailsActivity.class);
                        intent.putExtras(bdl);
                        startActivity(intent);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
            // getActivity().invalidateOptionsMenu();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return v;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static Calls_Fragment newInstance(String text) {

        Calls_Fragment f = new Calls_Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    // http://stackoverflow.com/questions/10024739/how-to-determine-when-fragment-becomes-visible-in-viewpager
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        // TODO Auto-generated method stub
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            // Toast.makeText(getActivity(), "resume",
            // Toast.LENGTH_SHORT).show();
            // res = getResources();
            // adapter = new CallsAdepter(getActivity(), calls, res);
            // lst_call.setAdapter(adapter);
            // adapter.notifyDataSetChanged();
        }
    }

//    public void setlist() {
//        try {
//            this.calls = new ArrayList<Calls>();
//            for (int i = 0; i <= 10; i++) {
//                Calls calls = new Calls();
//                if (i % 2 == 0) {
//                    calls.setBlock("1");
//                    calls.setPlay("1");
//                    calls.setTime("9:24am");
//                } else {
//                    calls.setBlock("0");
//                    calls.setPlay("0");
//                    calls.setTime("10:24pm");
//                }
//                calls.setName("Todd Heintz");
//                calls.setNumber("555-555-555");
//
//                this.calls.add(calls);
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public void showFragment() {

        try {
            getActivity().invalidateOptionsMenu();
            // Creating a fragment object
            Calls_details cFragment = new Calls_details();
            // Main_test cFragment = new Main_test();

            // Creating a Bundle object
            Bundle data = new Bundle();

            // Setting the position to the fragment
            cFragment.setArguments(data);

            // Getting reference to the FragmentManager
            FragmentManager fragmentManager = getChildFragmentManager();

            // Creating a fragment transaction
            FragmentTransaction ft = fragmentManager.beginTransaction();

            // Adding a fragment to the fragment transaction
            // ft.replace(R.id.llContainer, cFragment);
            // int id = mCurrentFragment.getView().getParent().getId();
            ft.replace(R.id.fragment_layout_support_land, cFragment);

            // Committing the transaction
            ft.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        this.menu = menu;
        menu.findItem(R.id.menu_NewEmails).setVisible(false);
        menu.findItem(R.id.menu_search).setVisible(true);

        menu.findItem(R.id.menu_call).setVisible(false);
        menu.findItem(R.id.menu_block).setVisible(false);
        menu.findItem(R.id.menu_addcontect).setVisible(false);

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                if (ll_calldetails.getVisibility() == View.VISIBLE) {
                    ll_calldetails.setVisibility(View.GONE);
                    lst_call.setVisibility(View.VISIBLE);
                } else {
                    // getActivity().finish();
                }

                break;
            case R.id.menu_addcontect:
                Add_caller_Dialog();
                break;
            case R.id.menu_block:
                Block_Dialog();
                break;
            case R.id.menu_call:
                break;
            default:

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public Fragment getVisibleFragment() {
        try {
            FragmentManager fragmentManager = getActivity()
                    .getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public boolean onBackPressed() {
        if (ll_calldetails != null) {
            if (ll_calldetails.getVisibility() == View.VISIBLE) {
                ll_calldetails.setVisibility(View.GONE);
                lst_call.setVisibility(View.VISIBLE);
                return true;
            } else {
                return false;
                // getActivity().finish();
            }
        } else {
            // getActivity().finish();
            return false;
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
            final Dialog dialog = new Dialog(getActivity(),
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

    public void showlistView() {
        lst_call.setVisibility(View.VISIBLE);
        ll_calldetails.setVisibility(View.GONE);
    }


    public class Get_Call_List extends AsyncTask<Void, Void, String> {
        String Member_id, data;
        public ProgressDialog progressDialog;

        public Get_Call_List() {
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
//                http://privatisapi.aaadev.info/direct.asmx/ListCalls?MemberId=1
                URI website = new URI(getString(R.string.host_name) + "ListCalls?MemberId=" + Member_id);
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
//                        for (int i = 0; i <= contacts.length(); i++) {
//                            JSONObject c = contacts.getJSONObject(i);
//                            Calls calls = new Calls();
//                            calls.setCallId(c.getString("CallId"));
//                            calls.setNumber(c.getString("Number"));
//                            calls.setName(c.getString("Name"));
//                            calls.setVoice(c.getString("Voice"));
//                            calls.setTime(c.getString("Time"));
//                            mCalls.add(calls);
//                        }
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            Calls calls = new Calls();
                            calls.setCallId(c.getString("CallId"));
                            calls.setNumber(c.getString("Number"));
                            calls.setName(c.getString("Name"));
                            calls.setVoice(c.getString("Voice"));
                            calls.setTime(c.getString("Time"));
                            mCalls.add(calls);
                        }

                        adapter = new CallsAdepter(getActivity(), mCalls, res);
                        lst_call.setAdapter(adapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
