package com.example.maxime.supfitness.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.maxime.supfitness.fragment.CurvesFragment;
import com.example.maxime.supfitness.fragment.MapFragment;
import com.example.maxime.supfitness.fragment.WeightFragment;

/**
 * Created by Maxime on 04/04/2016.
 */
public class TabAdapter extends FragmentPagerAdapter {

    String[] titles = {"Weight", "Curves", "Map"};

    public TabAdapter(FragmentManager fm, Context c) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;

        if (position == 0) {
            frag = new WeightFragment();
        } else if (position == 1){
            frag = new CurvesFragment( );
        } else if (position == 2){
            frag = new MapFragment();// SupportMapFragment(); get just a map
        }

        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){

        return titles[position];
    }
}
