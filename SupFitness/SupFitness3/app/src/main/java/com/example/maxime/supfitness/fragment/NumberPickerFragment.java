package com.example.maxime.supfitness.fragment;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.maxime.supfitness.R;
import com.example.maxime.supfitness.Model.DatabaseHandle;
import com.example.maxime.supfitness.Model.Weight;

/**
 * Created by clement on 05/04/16.
 */

public class NumberPickerFragment extends DialogFragment {
    DatabaseHandle db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setTitle(R.string.TitlePickTime);
        dialog.setContentView(R.layout.number_picker_dialog);

        Button b1 = (Button) dialog.findViewById(R.id.button1);
        Button b2 = (Button) dialog.findViewById(R.id.button2);

        db = new DatabaseHandle(getActivity(), null, null, 1);

        final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberPicker1);

        np.setMaxValue(200);
        np.setMinValue(0);
        np.setValue(60);


        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Weight w = new Weight(np.getValue());
                db.addWeight(w);  //Add Weight in DataBase

                /** UPDATE LIST **/
                WeightFragment.updateListView();

                Toast.makeText(getActivity(), "The weight is added", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        return dialog;
    }
}

