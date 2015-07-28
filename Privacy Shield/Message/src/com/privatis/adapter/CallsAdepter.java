package com.privatis.adapter;

import java.util.ArrayList;

import com.privatis.adapter.EmailAdapter.ViewHolder;
import com.privatis.message.R;
import com.privatis.model.Calls;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CallsAdepter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Calls> calls;
    public Resources res;
    private static LayoutInflater inflater = null;

    public CallsAdepter(Activity activity, ArrayList<Calls> calls, Resources res) {

        this.activity = activity;
        this.calls = calls;
        this.res = res;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return calls.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return calls.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public static class ViewHolder {
        public TextView txt_number, txt_name, txt_time;
        public ImageView img_play, img_block;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        try {
            ViewHolder holder;
//			if (convertView == null) {
            vi = inflater.inflate(R.layout.list_calls, null);

            holder = new ViewHolder();
            holder.txt_number = (TextView) vi.findViewById(R.id.txt_number);
            holder.txt_name = (TextView) vi.findViewById(R.id.txt_name);
            holder.txt_time = (TextView) vi.findViewById(R.id.txt_time);
            holder.img_block = (ImageView) vi.findViewById(R.id.img_block);
            holder.img_play = (ImageView) vi.findViewById(R.id.img_play);

            holder.txt_name.setText(calls.get(position).getName());
            holder.txt_number.setText(calls.get(position).getNumber());
            holder.txt_time.setText(calls.get(position).getTime());

            String play = calls.get(position).getVoice();
//            String block = calls.get(position).getBlock();

            if (play.toLowerCase().equalsIgnoreCase("false")) {
                holder.img_play.setVisibility(View.INVISIBLE);
            } else {
                holder.img_play.setVisibility(View.VISIBLE);
            }
//            if (block.equalsIgnoreCase("0")) {
//                holder.img_block.setVisibility(View.INVISIBLE);
//            } else {
//                holder.img_block.setVisibility(View.VISIBLE);
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
