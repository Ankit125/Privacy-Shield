package com.privatis.message;

import com.privaties.common.constant;
import com.privatis.adapter.MenuListAdapter;
import com.privatis.fragment.Account_Fragment;
import com.privatis.fragment.Blocked_Calls;
import com.privatis.fragment.Blocked_Emails;
import com.privatis.fragment.Email_Fragment;
import com.privatis.fragment.Main_Menu;
import com.privatis.fragment.My_Contacts_Fragment;
import com.privatis.fragment.Premium_Fragment;
import com.privatis.fragment.Premium_Fragment_new;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends FragmentActivity {

    int mPosition = -1;
    // String mTitle = "";

    // Array of strings storing country names
    String[] mCountries;

    // Array of strings to initial counts
    String[] mCount = new String[]{"", "", "", "", "", "", "", "", "", ""};

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mDrawer;
    // -------------------------------
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    String[] title;
    String[] subtitle;
    MenuListAdapter mMenuAdapter;
    private int position_list = 0;

    // Fragment Main_Menu = new Main_Menu();

    Menu menu;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        try {
            View header = getLayoutInflater().inflate(R.layout.drawer_header,
                    null);
            header.setOnClickListener(null);

            mTitle = mDrawerTitle = getTitle();

            // Get the Title
            mTitle = mDrawerTitle = getTitle();

            // Generate title
            // title = new String[] { "Message", "My Account", "Go Premium",
            // "Learn More", "Help" };

            // title = new String[] { "Message", "My Account", "Blocked Calls",
            // "Blocked Emails", "Upgrade Account", "Learn More", "Help",
            // "My Contacts" };

            title = new String[]{"Message", "My Contacts", "Blocked Numbers",
                    "Blocked Emails", "My Account", "Upgrade", "Logout"};

            // Generate subtitle
            subtitle = new String[]{" ", " "};

            // Generate icon
            // icon = new int[] { R.drawable.btn_epaper_icon_selector,
            // R.drawable.btn_article_icon_selector };

            // Locate DrawerLayout in drawer_main.xml
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            // Locate ListView in drawer_main.xml
            mDrawerList = (ListView) findViewById(R.id.listview_drawer);

            // must be called before setadapter
            mDrawerList.addHeaderView(header);

            // Set a custom shadow that overlays the main content when the
            // drawer
            // opens
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                    GravityCompat.START);
            mMenuAdapter = new MenuListAdapter(MainActivity.this, title,
                    subtitle);

            // Set the menu item zero by default.
            mMenuAdapter.setSelectedItem(0);

            // Set the MenuListAdapter to the ListView
            mDrawerList.setAdapter(mMenuAdapter);

            // Capture listview menu item click
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

            // get the action bar
            // getSupportActionBar().setBackgroundDrawable(new
            // ColorDrawable(Color.GRAY));

            // getActionBar().setBackgroundDrawable(
            // getResources().getDrawable(R.drawable.actionbarbg));
            // getActionBar().setTitle("");
            // // getActionBar().setTitle("Menu");
            // // Enable ActionBar app icon to behave as action to toggle nav
            // drawer
            // getActionBar().setHomeButtonEnabled(true);
            // getActionBar().setDisplayHomeAsUpEnabled(true);

            // --------------
            ActionBar mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setLogo(R.drawable.action_icon);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.actionbar, null);

            // mActionBar.setBackgroundDrawable(getResources().getDrawable(
            // R.drawable.actionbarbg));

            mActionBar.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor("#055ba6")));

            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.txt_title);
            mTitleTextView.setText("Menu");

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            // --------------

            // ActionBarDrawerToggle ties together the the proper interactions
            // between the sliding drawer and the action bar app icon
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.action_logo, R.string.drawer_open,
                    R.string.drawer_close) {

                public void onDrawerClosed(View view) {
                    // getActionBar().setTitle(mTitle);
                    // getActionBar().setTitle("Menu");
                    // calling onPrepareOptionsMenu() to show action bar icons
                    super.onDrawerClosed(view);
                }

                public void onDrawerOpened(View drawerView) {

                    // Set the title on the action when drawer open
                    // getActionBar().setTitle(mDrawerTitle);
                    // getActionBar().setTitle("Menu");
                    super.onDrawerOpened(drawerView);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            showFragment(0);
            get_Member_data get_member_data = new get_Member_data();
            get_member_data.execute();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    public void showFragment(int position) {

        try {
            // Currently selected country
            // mTitle = mCountries[position];

            // Creating a fragment object
            Main_Menu cFragment = new Main_Menu();
            // Main_test cFragment = new Main_test();

            // Creating a Bundle object
            Bundle data = new Bundle();

            // Setting the index of the currently selected item of mDrawerList
            data.putInt("position", position);

            // Setting the position to the fragment
            cFragment.setArguments(data);
            // cFragment.

            // Getting reference to the FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Creating a fragment transaction
            FragmentTransaction ft = fragmentManager.beginTransaction();

            // Adding a fragment to the fragment transaction
            // ft.replace(R.id.content_frame, cFragment);
            String st = String.valueOf(position);
            ft.replace(R.id.llContainer, cFragment, st);

            // Committing the transaction
            ft.commit();

            mDrawerLayout.closeDrawer(mDrawerList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // ListView click listener in the navigation drawer
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            try {
                // selectItem(position - 1);
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                // showFragment(position - 1);
                if (position == 1) {
                    ShowMessage();
                } else if (position == 2) {
                    showMyContects();
                } else if (position == 3) {
                    showBloackcalls();
                } else if (position == 4) {
                    showBloackemails();
                } else if (position == 5) {
                    // getActionBar().setTitle("");
                    showAccountFragment();
                } else if (position == 6) {
                    showPremiumFragment();
                } else if (position == 7) {
                    constant.Set_Member_Id("", MainActivity.this);
                    Intent intent = new Intent(MainActivity.this,
                            OpeningActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                } else if (position == 8) {

                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        MenuInflater inflaters = getMenuInflater();
        inflaters.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {

            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }

        // if (item.getItemId() == R.id.menu_NewEmails) {
        // Email_Fragment myFragment = (Email_Fragment)
        // getSupportFragmentManager()
        // .findFragmentByTag("0");
        // if (myFragment != null && myFragment.isVisible()) {
        // Toast.makeText(MainActivity.this, "press new Email",
        // Toast.LENGTH_LONG).show();
        // }
        // }

        return super.onOptionsItemSelected(item);
    }

    public void showAccountFragment() {
        try {
            Account_Fragment cFragment = new Account_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.llContainer, cFragment);
            ft.commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showPremiumFragment() {
        // getActionBar().setTitle("Go Premium");
        // Premium_Fragment cFragment = new Premium_Fragment();
        // FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentTransaction ft = fragmentManager.beginTransaction();
        // ft.replace(R.id.llContainer, cFragment);
        // ft.commit();
        // mDrawerLayout.closeDrawer(mDrawerList);

        try {
            Premium_Fragment_new cFragment = new Premium_Fragment_new();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.llContainer, cFragment);
            ft.commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void showBloackcalls() {
        try {
            Blocked_Calls cFragment = new Blocked_Calls();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.llContainer, cFragment);
            ft.commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showBloackemails() {
        try {
            Blocked_Emails cFragment = new Blocked_Emails();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.llContainer, cFragment);
            ft.commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showMyContects() {
        try {
            My_Contacts_Fragment cFragment = new My_Contacts_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.llContainer, cFragment);
            ft.commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void ShowMessage() {
        try {
            Bundle bdl = new Bundle();
            bdl.putInt("position", 1);
            Intent intent = new Intent(MainActivity.this,
                    MainActivity_all.class);
            intent.putExtras(bdl);
            startActivity(intent);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setActionBarTitle(String title) {
        getActionBar().setTitle(title);
    }


    public class get_Member_data extends AsyncTask<Void, Void, String> {

        String Member_id, data;
        public ProgressDialog progressDialog;

        public get_Member_data() {
            this.Member_id = constant.get_Member_Id(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = constant.createProgressDialog(MainActivity.this);
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
                        Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_LONG).show();
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
                        constant.Set_Member_data(MainActivity.this, FirstName, LastName, RealEmail, MaskedEmail, RealPhone, MaskedPhone, AccountType, AccountStatus);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}