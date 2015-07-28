package com.privaties.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.privatis.message.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class constant {

    // public static boolean flag = true;

    public static final String SOAP_ACTION = "http://pc.aaadev.info/";
    public static final String WSDL_TARGET_NAMESPACE = "http://privatisapi.aaadev.info/";
    public static final String SOAP_ADDRESS = "http://privatisapi.aaadev.info/direct.asmx?op=";


    public static void Set_flag(Integer flag, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Editor editor = pref.edit();
        editor.putInt("Sync_flag", flag);
        editor.commit();
    }

    public static Integer get_flag(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Integer flag = pref.getInt("Sync_flag", 0);
        return flag;
    }


    public static void Set_Member_Id(String Member_Id, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Editor editor = pref.edit();
        editor.putString("Member_Id", Member_Id);
        editor.commit();
    }

    public static String get_Member_Id(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String flag = pref.getString("Member_Id", "");
        return flag;
    }

    public static void Set_Member_data(Context context, String FirstName, String LastName, String RealEmail, String MaskedEmail, String RealPhone, String MaskedPhone, String AccountType, String AccountStatus) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        Editor editor = pref.edit();
        editor.putString("FirstName", FirstName);
        editor.putString("LastName", LastName);
        editor.putString("RealEmail", RealEmail);
        editor.putString("MaskedEmail", MaskedEmail);
        editor.putString("RealPhone", RealPhone);
        editor.putString("MaskedPhone", MaskedPhone);
        editor.putString("AccountType", AccountType);
        editor.putString("AccountStatus", AccountStatus);
        editor.commit();
    }

    public static String get_FirstName(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String FirstName = pref.getString("FirstName", "");
        return FirstName;
    }

    public static String get_LastName(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String LastName = pref.getString("LastName", "");
        return LastName;
    }

    public static String get_RealEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String RealEmail = pref.getString("RealEmail", "");
        return RealEmail;
    }

    public static String get_MaskedEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String MaskedEmail = pref.getString("MaskedEmail", "");
        return MaskedEmail;
    }

    public static String get_RealPhone(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String RealPhone = pref.getString("RealPhone", "");
        return RealPhone;
    }

    public static String get_MaskedPhone(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String MaskedPhone = pref.getString("MaskedPhone", "");
        return MaskedPhone;
    }

    public static String get_AccountType(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String AccountType = pref.getString("AccountType", "");
        return AccountType;
    }

    public static String get_AccountStatus(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String AccountStatus = pref.getString("AccountStatus", "");
        return AccountStatus;
    }


    public static boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
//		http://stackoverflow.com/questions/16980404/display-progressdialog-without-text-android
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);
        // dialog.setMessage(Message);
        return dialog;
    }

    public static void hide_keyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
