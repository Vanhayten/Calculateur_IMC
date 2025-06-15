package com.example.calculateurimc;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.calculateurimc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String weightStr = binding.editTextWeight.getText().toString().trim();
        String heightStr = binding.editTextHeight.getText().toString().trim();

        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr) / 100.0; // Convert cm to meters

            if (weight <= 0 || height <= 0) {
                return;
            }

            double bmi = weight / (height * height);
            displayResult(bmi);

        } catch (NumberFormatException e) {
            // Handle invalid input
        }
    }

    private void displayResult(double bmi) {
        // Show result card
        binding.cardResult.setVisibility(View.VISIBLE);

        // Display BMI value
        String bmiFormatted = String.format("%.1f", bmi);
        binding.textViewBMI.setText(bmiFormatted);

        // Determine category and set appropriate image and text
        if (bmi < 18.5) {
            binding.textViewCategory.setText(getString(R.string.underweight));
            binding.textViewRange.setText("< 18.5");
            binding.imageViewCategory.setImageResource(R.drawable.ic_underweight);
            binding.textViewCategory.setTextColor(ContextCompat.getColor(this, R.color.underweight_color));
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            binding.textViewCategory.setText(getString(R.string.normal_weight));
            binding.textViewRange.setText("18.5 - 24.9");
            binding.imageViewCategory.setImageResource(R.drawable.ic_normal);
            binding.textViewCategory.setTextColor(ContextCompat.getColor(this, R.color.normal_color));
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            binding.textViewCategory.setText(getString(R.string.overweight));
            binding.textViewRange.setText("25.0 - 29.9");
            binding.imageViewCategory.setImageResource(R.drawable.ic_overweight);
            binding.textViewCategory.setTextColor(ContextCompat.getColor(this, R.color.overweight_color));
        } else if (bmi >= 30.0 && bmi <= 34.9) {
            binding.textViewCategory.setText(getString(R.string.obesity));
            binding.textViewRange.setText("30.0 - 34.9");
            binding.imageViewCategory.setImageResource(R.drawable.ic_obesity);
            binding.textViewCategory.setTextColor(ContextCompat.getColor(this, R.color.obesity_color));
        } else if (bmi >= 35.0) {
            binding.textViewCategory.setText(getString(R.string.severe_obesity));
            binding.textViewRange.setText("≥ 35.0");
            binding.imageViewCategory.setImageResource(R.drawable.ic_obesity_severe);
            binding.textViewCategory.setTextColor(ContextCompat.getColor(this, R.color.severe_obesity_color));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}