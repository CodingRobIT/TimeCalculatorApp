package com.robins.timecalculator.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robins.timecalculator.R;

public class HomeFragment extends Fragment {

    private EditText editTextHHMM, editTextDecimal, editTextMinutes;
    private boolean isUpdating = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        editTextHHMM = view.findViewById(R.id.editTextHHMM);
        editTextDecimal = view.findViewById(R.id.editTextDecimal);
        editTextMinutes = view.findViewById(R.id.editTextMinutes);

        editTextHHMM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertFromHHMM(s.toString());
                    isUpdating = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        editTextDecimal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertFromDecimal(s.toString());
                    isUpdating = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        editTextMinutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertFromMinutes(s.toString());
                    isUpdating = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void convertFromHHMM(String input) {
        if (input.contains("h")) {
            String[] parts = input.split("h");
            try {
                int hours = Integer.parseInt(parts[0].trim());
                int minutes = parts.length > 1 ? Integer.parseInt(parts[1].trim()) : 0;
                int totalMinutes = hours * 60 + minutes;
                double decimalHours = hours + minutes / 60.0;

                editTextDecimal.setText(String.format("%.2f", decimalHours));
                editTextMinutes.setText(String.valueOf(totalMinutes));
            } catch (Exception ignored) {}
        }
    }

    private void convertFromDecimal(String input) {
        try {
            double decimal = Double.parseDouble(input);
            int totalMinutes = (int) (decimal * 60);
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;

            editTextHHMM.setText(hours + "h " + minutes + "m");
            editTextMinutes.setText(String.valueOf(totalMinutes));
        } catch (Exception ignored) {}
    }

    private void convertFromMinutes(String input) {
        try {
            int totalMinutes = Integer.parseInt(input);
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            double decimalHours = totalMinutes / 60.0;

            editTextHHMM.setText(hours + "h " + minutes + "m");
            editTextDecimal.setText(String.format("%.2f", decimalHours));
        } catch (Exception ignored) {}
    }
}