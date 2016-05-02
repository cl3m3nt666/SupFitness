package com.example.maxime.supfitness.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maxime.supfitness.MainActivity;
import com.example.maxime.supfitness.R;
import com.example.maxime.supfitness.adapter.MyListAdaper;
import com.example.maxime.supfitness.Model.DatabaseHandle;
import com.example.maxime.supfitness.Model.Weight;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment {

    static DatabaseHandle db;

    //List for getAll() -> all values in database
    private static ArrayList<Weight> w = new ArrayList<>();
    //List String for display ListView weight
    private static ArrayList<String> displayW = new ArrayList<>();

    private static ListView lv;
    //My adaper
    private static MyListAdaper adaper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        db = new DatabaseHandle(getActivity(), null, null, 1); //db instance
        generateListContent();
        concatForDisplay();
        adaper = new MyListAdaper(getContext(), R.layout.menu_list, displayW, (MainActivity) getActivity(), WeightFragment.this);

        //ListView
        lv = (ListView) view.findViewById(R.id.listview);
        lv.setAdapter(adaper);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //For test
                Toast.makeText(getActivity(), "Your weight at " + w.get(position).getDate(), Toast.LENGTH_SHORT).show();
                updateListView(); //Update values
            }
        });

        return view;

    }


    //Method for update values in ListView
    public static void updateListView() {
        adaper.clear();

        generateListContent();
        concatForDisplay();

        adaper.notifyDataSetChanged();
    }
    //Refresh List Weight
    private static void generateListContent() {
        w = db.getAll();
        Collections.reverse(w);
    }

    //List string for display
    private static void concatForDisplay() {
        displayW.clear();
        for(int i = 0; i < w.size(); i++){
            displayW.add("\n "+w.get(i).getDate() + "\n " + w.get(i).getWeight() + " Kg \n");
            //this.displayW.add("test");
        }

    }
    private void generateListContentOrderByWeight() {
        //w = db.getAll();
        //Collections.reverse(w);
        //Collections.reverseOrder();
        //Collections.sort(w);
    }

    public WeightFragment() {
        // Required empty public constructor
    }



}
