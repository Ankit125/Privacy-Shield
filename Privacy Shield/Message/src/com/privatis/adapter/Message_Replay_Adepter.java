package com.privatis.adapter;

import java.util.ArrayList;

import com.privatis.adapter.MessagesAdapter.ViewHolder;
import com.privatis.message.R;
import com.privatis.model.Message_Reply;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Message_Replay_Adepter extends BaseAdapter {

    private ArrayList<Message_Reply> message;
    private Activity activity;
    private static LayoutInflater inflater = null;
    private String PhoneNumber;

    public Message_Replay_Adepter(Activity activity,
                                  ArrayList<Message_Reply> message, String PhoneNumber) {
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.message = message;
        this.PhoneNumber = PhoneNumber;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return message.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public static class ViewHolder {

        public TextView txt_message_send, txt_time_send, txt_message_receive,
                txt_time_receive;
        public LinearLayout ll_receive, ll_reply;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        try {
            ViewHolder holder;
//			if (convertView == null) {
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_message_reply, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.ll_receive = (LinearLayout) vi
                    .findViewById(R.id.ll_receive);
            holder.ll_reply = (LinearLayout) vi.findViewById(R.id.ll_reply);

            holder.txt_message_receive = (TextView) vi.findViewById(R.id.txt_message_receive);
            holder.txt_time_receive = (TextView) vi.findViewById(R.id.txt_time_receive);
            holder.txt_message_send = (TextView) vi.findViewById(R.id.txt_message_send);
            holder.txt_time_send = (TextView) vi.findViewById(R.id.txt_time_send);

            holder.ll_receive.setVisibility(View.GONE);
            holder.ll_reply.setVisibility(View.GONE);

            if (PhoneNumber.equalsIgnoreCase(message.get(position).getPhoneSmsFrom())) {
                holder.txt_message_send.setText(message.get(position).getSmsMessage());
                holder.txt_time_send.setText(message.get(position).getCreatedDate());
                holder.ll_receive.setVisibility(View.GONE);
                holder.ll_reply.setVisibility(View.VISIBLE);
            } else {
                holder.txt_message_receive.setText(message.get(position).getSmsMessage());
                holder.txt_time_receive.setText(message.get(position).getCreatedDate());
                holder.ll_receive.setVisibility(View.VISIBLE);
                holder.ll_reply.setVisibility(View.GONE);
            }
//            if (message.get(position).isFlag()) {
//            } else {
//            }
//			} else {
//				holder = (ViewHolder) vi.getTag();
//			}
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return vi;
    }

}
