package com.example.maxime.supfitness.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.maxime.supfitness.Model.DatabaseHandle;
import com.example.maxime.supfitness.Model.Weight;
import com.github.mikephil.charting.charts.LineChart;
import com.example.maxime.supfitness.R;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurvesFragment extends Fragment {

    private LineChart lineChart;
    private RelativeLayout relativeLayout;
    private  DatabaseHandle db;

    public CurvesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume(){
        super.onResume();

        final ArrayList<Weight> weights = db.getAll();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i < weights.size(); i++){
                    final int y = i;
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addData(y);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curves, container, false);
        // Inflate the layout for this fragment

        relativeLayout = (RelativeLayout) view.findViewById(R.id.CurvesLayout);
        db = new DatabaseHandle(getActivity(), null, null, 1);
        createChart();
        return view;
    }

    private void createChart(){

        // Create line chart
        lineChart = (LineChart) relativeLayout.findViewById(R.id.lineChart);
        // Customize line chart
        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("Please make sure you have enter some Weight");

        // Enable value highlighting
        lineChart.setHighlightPerTapEnabled(true);

        // Enable touch gesture
        lineChart.setTouchEnabled(true);

        // Scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        // Enable pinch zoom to avoid scaling x and y separately
        lineChart.setPinchZoom(true);

        // Alternative background color
        lineChart.setBackgroundColor(Color.WHITE);

        // Data
        LineData lineData = new LineData();
        lineData.setValueTextColor(Color.BLUE);

        // Add it to the chart
        lineChart.setData(lineData);

        // Legend object
        Legend legend = lineChart.getLegend();

        // Custom legend
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.BLUE);

        // X Axis
        XAxis x = lineChart.getXAxis();
        x.setTextColor(Color.BLUE);
        x.setDrawGridLines(true);
        x.setAvoidFirstLastClipping(true);

        // Y Axis
        YAxis y = lineChart.getAxisLeft();
        y.setTextColor(Color.BLUE);
        y.setAxisMaxValue(maxWeight() + 10);
        y.setAxisMinValue(0);
        y.setDrawGridLines(true);

        lineChart.getAxisRight().setEnabled(false);

    }

    public void addData(int i){
        LineData data = lineChart.getData();
        ArrayList<Weight> weights = db.getAll();

        if(data != null){
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);

            if(set == null){
                set = createSet();
                data.addDataSet(set);
            }

            data.addXValue(weights.get(i).getDate());
            data.addEntry(
                    new Entry((float) (weights.get(i).getWeight()), set
                            .getEntryCount()), 0);

            lineChart.notifyDataSetChanged();
            lineChart.setVisibleXRange(6, 6);
            lineChart.moveViewToX(data.getXValCount() - 7);

        }
    }

    private LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null, "Weight");
        set.setDrawCubic(true);
        set.setCubicIntensity(0);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setCircleSize(5f);
        set.setFillAlpha(0);
        set.setHighLightColor(ColorTemplate.getHoloBlue());
        set.setValueTextColor(Color.BLUE);
        set.setValueTextSize(10f);

        return set;
    }

    public float maxWeight(){
        ArrayList<Weight> weights = db.getAll();
        float max = 0;

        for(int i=0; i < weights.size(); i++){
            if(weights.get(i).getWeight() > max){
                max = weights.get(i).getWeight();
            }
        }

        return max;
    }
}
