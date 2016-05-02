package com.example.maxime.supfitness.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by clement on 04/04/16.
 */
public class Weight {
    private int id;
    private int weight;
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    private String date;

    public Weight(){
    }
    public Weight(int weight){
        this.weight = weight;
        this.date = df.format(c.getTime());
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }
    public String getDate() {
        return date;
    }
    public int getId() {return id;}

}
