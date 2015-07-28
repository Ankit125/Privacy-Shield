package com.privatis.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.privatis.message.Emails_DetailsActivity;
import com.privatis.message.R;
import com.privatis.model.Email;

public class EmailAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Email> mmessage;
    public Resources resourse;
    private static LayoutInflater inflater = null;

    public EmailAdapter(Activity activity, ArrayList<Email> message,
                        Resources resourse) {

        try {
            this.activity = activity;
            this.mmessage = message;
            this.resourse = resourse;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mmessage.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mmessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public static class ViewHolder {

        public TextView txt_Sender_Email_Id, txt_Subject, txt_time, txt_receive, txt_Body;
        WebView webView2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View vi = convertView;
        try {
            ViewHolder holder;
//            if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_email, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.txt_Sender_Email_Id = (TextView) vi.findViewById(R.id.txt_Sender_Email_Id);
            holder.txt_Subject = (TextView) vi.findViewById(R.id.txt_Subject);
            holder.txt_time = (TextView) vi.findViewById(R.id.txt_time);
            holder.txt_receive = (TextView) vi
                    .findViewById(R.id.txt_receive);
            holder.txt_Body = (TextView) vi.findViewById(R.id.txt_Body);
            holder.webView2 = (WebView) vi.findViewById(R.id.webView2);

            /************ Set holder with LayoutInflater ************/

            holder.txt_Sender_Email_Id.setText(mmessage.get(position).getSender_Email_Id());
            holder.txt_Subject.setText(mmessage.get(position).getSubject());
            holder.txt_time.setText(mmessage.get(position).getTime());
//				holder.txt_receive.setText(mmessage.get(position).getFlag());
//            String html = "<html><body>" + mmessage.get(position).getBody().replace(" dir='ltr'", "") + "</body></html>";
            String EmailBody = mmessage.get(position).getBody();
            String html = (EmailBody.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
//            holder.txt_Body.setText(Html.fromHtml(mmessage.get(position).getBody()));
//            holder.webView2.setClickable(false);
//            holder.webView2.setEnabled(false);
//            holder.webView2.setOnKeyListener(null);
            holder.webView2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            holder.webView2.setLongClickable(false);
            holder.webView2.setHapticFeedbackEnabled(false);
            holder.webView2.loadData(html, "text/html", "utf-8");
            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageid = mmessage.get(position).getMessageId();
                    Bundle bdl = new Bundle();
                    bdl.putString("messageid", messageid);
//                    bdl.putString("SenderEmailId", SenderEmailId);
//                    bdl.putString("Subject", Subject);
//                    bdl.putString("Body", Body);
//                    bdl.putString("Time", Time);
                    activity.finish();
                    Intent intent = new Intent(activity,
                            Emails_DetailsActivity.class);
                    intent.putExtras(bdl);
                    activity.startActivity(intent);
                }
            });
            vi.setTag(holder);
//            } else {
//                holder = (ViewHolder) vi.getTag();
//            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return vi;
    }

}
