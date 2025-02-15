package com.robins.timecalculator.ui.start_end_time;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.robins.timecalculator.databinding.FragmentStartEndTimeBinding;

public class StartEndTimeFragment extends Fragment {

    private FragmentStartEndTimeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StartEndTimeViewModel dashboardViewModel =
                new ViewModelProvider(this).get(StartEndTimeViewModel.class);

        binding = FragmentStartEndTimeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStartEndTime;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}