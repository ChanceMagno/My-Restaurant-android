package com.example.guest.myrestaurants.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyRestaurantsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mRestaurants;


    public MyRestaurantsArrayAdapter(Context mContext, int resource, String[] mRestaurants) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mRestaurants = mRestaurants;
    }

    @Override
    public Object getItem(int position) {
        String restaurant = mRestaurants[position];
        return String.format("%s Serves great: %s", restaurant);
    }

    @Override
    public int getCount() {
        return mRestaurants.length;
    }
}
