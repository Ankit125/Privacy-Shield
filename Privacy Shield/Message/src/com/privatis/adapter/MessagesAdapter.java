package com.privatis.adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.privatis.message.R;
import com.privatis.model.Message;
import com.privatis.model.Email;

public class MessagesAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Message> mmessage;
    private ArrayList<Message> original_mmessage;
    public Resources resourse;
    private static LayoutInflater inflater = null;

    public MessagesAdapter(Activity activity, ArrayList<Message> Email,
                           Resources resourse) {

        this.activity = activity;
        this.mmessage = Email;
        this.resourse = resourse;

        original_mmessage = new ArrayList<Message>();
        original_mmessage.addAll(Email);

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        public TextView txt_phone, txt_time, txt_notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View vi = convertView;
        try {
            ViewHolder holder;
//			if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_messages, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.txt_phone = (TextView) vi.findViewById(R.id.txt_phone);
            holder.txt_time = (TextView) vi.findViewById(R.id.txt_time);
            holder.txt_notes = (TextView) vi.findViewById(R.id.txt_notes);

            /************ Set holder with LayoutInflater ************/

            holder.txt_phone.setText(mmessage.get(position).getSender_Mobile_Number());
            holder.txt_time.setText(mmessage.get(position).getTime());
            holder.txt_notes.setText(mmessage.get(position).getBody());

            vi.setTag(holder);
//			} else {
//				holder = (ViewHolder) vi.getTag();
//			}
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return vi;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mmessage.clear();
        if (charText.length() == 0) {
            mmessage.addAll(original_mmessage);
        } else {
            for (Message ms : original_mmessage) {
                if (ms.getSender_Mobile_Number().contains(charText)) {
                    mmessage.add(ms);
                }
            }
        }
        notifyDataSetChanged();
    }
}
