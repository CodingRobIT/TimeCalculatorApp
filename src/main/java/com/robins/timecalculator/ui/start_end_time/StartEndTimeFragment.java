package com.robins.timecalculator.ui.start_end_time;

import com.robins.timecalculator.databinding.FragmentStartEndTimeBinding;
import com.robins.timecalculator.R;

import android.os.Bundle;
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

        // Start-Button Logic
        startButton.setOnClickListener(v -> {
            String currentTime = timeFormat.format(new Date());
            startTimeEditText.setText(currentTime);
        });

        // Stop-Button Logic
        stopButton.setOnClickListener(v -> {
            String currentTime = timeFormat.format(new Date());
            endTimeEditText.setText(currentTime);
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
                        "ist in Deziaml: " + String.format("%.2f", decimalHours) + " Dezimalstunden");
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Fehler beim Parsen der Zeiten.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
