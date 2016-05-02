package com.example.maxime.supfitness;

import android.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.maxime.supfitness.adapter.TabAdapter;
import com.example.maxime.supfitness.fragment.NumberPickerFragment;
import com.example.maxime.supfitness.tab.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


    private SlidingTabLayout Tab;
    private ViewPager view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        view=(ViewPager) findViewById(R.id.vp);
        view.setAdapter(new TabAdapter (getSupportFragmentManager(), this));

        Tab=(SlidingTabLayout) findViewById(R.id.Tab);
        Tab.setDistributeEvenly(true);
        Tab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        Tab.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        Tab.setCustomTabView(R.layout.tab_layout, R.id.tv_tab);
        Tab.setViewPager(view);


    }
    //Show num picker
    public void showNumPickerDialog(View view) {

        DialogFragment numberPickerFragment = new NumberPickerFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        numberPickerFragment.show(fragmentManager, "numberPicker");
    }

}
