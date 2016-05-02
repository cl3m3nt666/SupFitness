package com.example.maxime.supfitness.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxime.supfitness.MainActivity;
import com.example.maxime.supfitness.Model.DatabaseHandle;
import com.example.maxime.supfitness.Model.Weight;
import com.example.maxime.supfitness.R;
import com.example.maxime.supfitness.fragment.WeightFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by clement on 06/04/16.
 */

public class MyListAdaper extends ArrayAdapter<String> {
    private int layout;
    static DatabaseHandle db;
    private List<String> mObjects;
    private ArrayList<Weight> w = new ArrayList<>(); //Array for return database
    private Activity acty;
    private Context context;
    private WeightFragment weightFragment;


    //Constructor
    public MyListAdaper(Context context, int resource, ArrayList<String> objects, MainActivity acty, WeightFragment weightFragment) {
        super(context, resource, objects);
        this.mObjects = objects;
        this.layout = resource;
        this.acty = acty;
        this.context = context;
        this.weightFragment = weightFragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            /** Instance viewHolder **/

            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
            viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);

            viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
            viewHolder.button.setBackgroundColor(Color.rgb(63, 81, 181));
            viewHolder.button.setTextColor(Color.rgb(255, 255, 255));
            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(600,75);
            lp1.setMargins(580, 30, 0, 0);
            //viewHolder.button.setLayoutParams(lp1);

            convertView.setTag(viewHolder);
            db = new DatabaseHandle(acty, null, null, 1);
            generateListContent();
        }
        mainViewholder = (ViewHolder) convertView.getTag();
        mainViewholder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Popup for Ui
                try {
                    Toast.makeText(getContext(),
                            "Your Weight (" + w.get(position).getWeight() + " Kg) on "+ w.get(position).getDate() +" was deleted ",
                            Toast.LENGTH_SHORT).show();
                    //Delete id in database
                    db.deleteId(w.get(position).getId()); //Get Id object and delete
                    /** UPDATE LIST **/
                    //Fragment fragment = getFragmentManager().findFragmentById(R.id.WeightLayout);
                    ((MainActivity)acty).getFragmentManager().findFragmentById(R.layout.fragment_weight);
                    weightFragment.updateListView();

                    generateListContent();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Can not remove the element", Toast.LENGTH_SHORT).show();
                    Log.e("ArryList Adaper", "Error " + e.toString());
                }


                //Update ListView
                //((Fragment) acty).updateListView(); //Update ListView

            }
        });
        mainViewholder.title.setText(getItem(position));
        return convertView;
    }
    private void generateListContent() {
        w = db.getAll();
        Collections.reverse(w);
    }

}

