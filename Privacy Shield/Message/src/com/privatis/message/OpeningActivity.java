package com.privatis.message;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class OpeningActivity extends Activity {

    private LinearLayout ll_signin, ll_signup;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.actionbar, null);

            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            mActionBar.setCustomView(mCustomView);
            mActionBar.hide();
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setLogo(R.drawable.action_icon);
            mActionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.actionbarbg));

            ll_signin = (LinearLayout) findViewById(R.id.ll_signin);
            ll_signup = (LinearLayout) findViewById(R.id.ll_signup);

            ll_signin.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    OpeningActivity.this.finish();
                    Intent intent = new Intent(OpeningActivity.this,
                            SignInActivity.class);
                    startActivity(intent);
                    // OpeningActivity.this.finish();
                }
            });

            ll_signup.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    OpeningActivity.this.finish();
                    Intent intent = new Intent(OpeningActivity.this,
                            SignUpActivity.class);
                    startActivity(intent);
                    // OpeningActivity.this.finish();
                }
            });
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
