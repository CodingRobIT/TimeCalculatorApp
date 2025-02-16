package com.robins.timecalculator.ui.start_end_time;

import com.robins.timecalculator.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartEndTimeFragment extends Fragment {

    private EditText startTimeEditText, endTimeEditText, pauseEditText;
    private Button startButton, stopButton, calculateButton;
    private TextView resultTextView;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_end_time, container, false);

        // Initialisierung der Views
        startTimeEditText = view.findViewById(R.id.startTimeEditText);
        endTimeEditText = view.findViewById(R.id.endTimeEditText);
        pauseEditText = view.findViewById(R.id.pauseEditText);
        startButton = view.findViewById(R.id.startButton);
        stopButton = view.findViewById(R.id.stopButton);
        calculateButton = view.findViewById(R.id.calculateButton);
        resultTextView = view.findViewById(R.id.resultTextView);

        // Für den textWatcher um festzustellen ob die Daten geändert wurden und somit gespeichert werden können
        startTimeEditText.addTextChangedListener(textWatcher);
        endTimeEditText.addTextChangedListener(textWatcher);
        pauseEditText.addTextChangedListener(textWatcher);

        // Laden der gespeicherten Werte
        loadSavedData();

        // Start-Button Logic
        startButton.setOnClickListener(v -> {
            String currentTime = timeFormat.format(new Date());
            startTimeEditText.setText(currentTime);
            saveData();
        });

        // Stop-Button Logic
        stopButton.setOnClickListener(v -> {
            String currentTime = timeFormat.format(new Date());
            endTimeEditText.setText(currentTime);
            saveData();
        });

        // Berechnung der Zeitdifferenz
        calculateButton.setOnClickListener(v -> {
            try {
                String startTimeStr = startTimeEditText.getText().toString();
                String endTimeStr = endTimeEditText.getText().toString();
                String pauseStr = pauseEditText.getText().toString();

                if (startTimeStr.isEmpty() || endTimeStr.isEmpty()) {
                    Toast.makeText(getContext(), "Bitte beide Zeiten eingeben.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date startTime = timeFormat.parse(startTimeStr);
                Date endTime = timeFormat.parse(endTimeStr);

                long diffInMillis = endTime.getTime() - startTime.getTime();
                long diffInMinutes = diffInMillis / (1000 * 60);  // Umrechnung in Minuten

                int pauseMinutes = pauseStr.isEmpty() ? 0 : Integer.parseInt(pauseStr);
                long netMinutes = diffInMinutes - pauseMinutes;

                // Berechnung der Stunden und Minuten
                long hours = netMinutes / 60;
                long minutes = netMinutes % 60;

                // Berechnung der Dezimalstunden
                double decimalHours = netMinutes / 60.0;

                // Anzeige der Ergebnisse
                resultTextView.setText("Gesamtzeit: " + netMinutes + " Minuten\n" +
                        "Das entspricht: " + hours + " Stunden " + minutes + " Minuten\n" +
                        "ist in Dezimal: " + String.format("%.2f", decimalHours) + " Dezimalstunden");

                // Speichern der Daten
                saveData();
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Fehler beim Parsen der Zeiten.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void saveData() {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = getActivity()
                    .getSharedPreferences("TimeCalculatorPrefs", getContext().MODE_PRIVATE)
                    .edit();

            String startTime = startTimeEditText.getText().toString();
            String endTime = endTimeEditText.getText().toString();
            String pauseTime = pauseEditText.getText().toString();

            editor.putString("startTime", startTime);
            editor.putString("endTime", endTime);
            editor.putString("pauseTime", pauseTime);
            editor.apply();
        }
    }

    private void loadSavedData() {
        if (getActivity() != null) {
            // Zugriff auf die SharedPreferences der App
            SharedPreferences prefs = getActivity().getSharedPreferences("TimeCalculatorPrefs", getContext().MODE_PRIVATE);

            // Werte aus SharedPreferences abrufen, falls vorhanden, sonst "NOT_FOUND" als Standardwert setzen
            String start = prefs.getString("startTime", "NOT_FOUND");
            String end = prefs.getString("endTime", "NOT_FOUND");
            String pause = prefs.getString("pauseTime", "NOT_FOUND");

            // Falls kein Wert gespeichert ist ("NOT_FOUND"), setzen wir ein leeres Feld, um Fehler zu vermeiden
            // Ein Problem war, dass zuvor null-Werte zu unerwartetem Verhalten führten
            startTimeEditText.setText(start.equals("NOT_FOUND") ? "" : start);
            endTimeEditText.setText(end.equals("NOT_FOUND") ? "" : end);
            pauseEditText.setText(pause.equals("NOT_FOUND") ? "" : pause);
        }
    }

    // Überwacht Änderungen in den Textfeldern und speichert die Eingaben automatisch
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            saveData(); // Speichert die Daten, nachdem sich der Text geändert hat
        }
    };
}
