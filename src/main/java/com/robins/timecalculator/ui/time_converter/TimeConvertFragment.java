package com.robins.timecalculator.ui.time_converter;

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

public class TimeConvertFragment extends Fragment {

    private EditText editTextHours, editTextMinutes, editTextDecimal, editTextTotalMinutes;
    private boolean isUpdating = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_convert, container, false);

        editTextHours = view.findViewById(R.id.editTextHours);
        editTextMinutes = view.findViewById(R.id.editTextMinutes);
        editTextDecimal = view.findViewById(R.id.editTextDecimal);
        editTextTotalMinutes = view.findViewById(R.id.editTextTotalMinutes);

        // Setze Standardwerte
        editTextHours.setText("0");
        editTextMinutes.setText("0");
        editTextDecimal.setText("0.00");
        editTextTotalMinutes.setText("0");

        editTextHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertFromHHMM(s.toString());
                    updateCalculation();
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
                    updateCalculation();
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

        editTextTotalMinutes.addTextChangedListener(new TextWatcher() {
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

            // Stunden und Minuten in die jeweiligen Felder eintragen
            editTextHours.setText(String.valueOf(hours));
            editTextMinutes.setText(String.valueOf(minutes));
            editTextTotalMinutes.setText(String.valueOf(totalMinutes));
        } catch (Exception ignored) {}
    }

    private void convertFromMinutes(String input) {
        try {
            int totalMinutes = Integer.parseInt(input);
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            double decimalHours = totalMinutes / 60.0;

            // Stunden, Minuten und Dezimalwert in die jeweiligen Felder eintragen
            editTextHours.setText(String.valueOf(hours));
            editTextMinutes.setText(String.valueOf(minutes));
            editTextDecimal.setText(String.format("%.2f", decimalHours));
            editTextTotalMinutes.setText(String.valueOf(totalMinutes)); // notwendig?
        } catch (Exception ignored) {}
    }

    // Methode zur Berechnung der Werte
    private void updateCalculation() {
        try {
            // Stunden und Minuten aus den Feldern holen
            int hours = Integer.parseInt(editTextHours.getText().toString());
            int minutes = Integer.parseInt(editTextMinutes.getText().toString());

            // Gesamtminuten berechnen
            int totalMinutes = (hours * 60) + minutes;
            editTextTotalMinutes.setText(String.valueOf(totalMinutes));

            // Dezimalwert berechnen
            double decimal = hours + (minutes / 60.0);
            editTextDecimal.setText(String.format("%.2f", decimal));
        } catch (NumberFormatException e) {
            // Falls keine gültigen Zahlen eingegeben wurden
            editTextTotalMinutes.setText("");
            editTextDecimal.setText("");
        }
    }
}