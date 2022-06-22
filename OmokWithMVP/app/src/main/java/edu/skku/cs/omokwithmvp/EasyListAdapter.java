package edu.skku.cs.omokwithmvp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EasyListAdapter extends BaseAdapter {
    private ArrayList<RankingUserModel> items;
    private Context mContext;

    EasyListAdapter (ArrayList<RankingUserModel> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_view_cell, viewGroup, false);
        }

        TextView rankingNumberTextView = view.findViewById(R.id.textView18);
        TextView userNickNameTextView  = view.findViewById(R.id.textView19);
        TextView userNameTextView      = view.findViewById(R.id.textView20);
        TextView userScoreTextView     = view.findViewById(R.id.textView21);

        rankingNumberTextView.setText(String.valueOf(i + 1));
        userNickNameTextView.setText(items.get(i).getNickname());
        userNameTextView.setText(items.get(i).getName());
        userScoreTextView.setText("Score : " + items.get(i).getScore());
        return view;
    }
}
