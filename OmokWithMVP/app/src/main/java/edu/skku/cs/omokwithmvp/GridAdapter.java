package edu.skku.cs.omokwithmvp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private ArrayList<Position> items;
    private Context mContext;

    GridAdapter (ArrayList<Position> items, Context mContext) {
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
            view = layoutInflater.inflate(R.layout.grid_view_cell, viewGroup, false);
        }

        ImageView imageView = view.findViewById(R.id.imageView5);
        // imageView.setImageResource(R.drawable.lettero);

        if (items.get(i).getPlayer() == 1) {
            imageView.setImageResource(R.drawable.circumference);
        }
        else if (items.get(i).getPlayer() == 2){
            imageView.setImageResource(R.drawable.close);
        }
        else if (items.get(i).getPlayer() == 3) {
            imageView.setImageResource(R.drawable.flag);
        }
        else if (items.get(i).getPlayer() == 4) {
            imageView.setImageResource(R.drawable.redflag);
        }
        else if (items.get(i).getPlayer() == -1) {
            imageView.setImageResource(0);
        }
        return view;
    }
}