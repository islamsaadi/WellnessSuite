package com.islamsaadi.wellnessfit;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.islamsaadi.wellnesscore.BaseMainActivity;
import com.islamsaadi.wellnesscore.HealthCalculator;
import com.islamsaadi.wellnessfit.databinding.ActivityMainFitBinding;

public class MainActivityFit extends BaseMainActivity {
    private ActivityMainFitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainFitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setSharedHeader();
        this.setSharedFormInputs();

        binding.btnCalcBMI.setOnClickListener(v -> {
            String w = sharedFormInputs.editWeight.getText().toString().trim();
            String h = sharedFormInputs.editHeight.getText().toString().trim();
            if (TextUtils.isEmpty(w) || TextUtils.isEmpty(h)) {
                Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double weight = Double.parseDouble(w);
                double height = Double.parseDouble(h);
                double bmi = HealthCalculator.calculateBMI(weight, height);
                var cat = HealthCalculator.getBMICategory(bmi);
                binding.resultBMI.setText(
                        "BMI: " + String.format("%.2f", bmi) + "\n" + cat.category
                );
                binding.resultBMI.setTextColor(cat.color);
            } catch (NumberFormatException ex) {
                Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnCalcWater.setOnClickListener(v -> {
            String w = sharedFormInputs.editWeight.getText().toString().trim();
            if (TextUtils.isEmpty(w)) {
                Toast.makeText(this, "Please enter weight", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double weight = Double.parseDouble(w);
                double water = HealthCalculator.calculateWaterIntake(weight);
                binding.resultWater.setText(
                        "Water: " + String.format("%.2f", water) + " L/day"
                );
            } catch (NumberFormatException ex) {
                Toast.makeText(this, "Invalid input.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setSharedHeader() {
        sharedHeader = binding.headerContainer;
        sharedHeader.headerLogo.setImageResource(R.drawable.logo_fit);
    }

    @Override
    protected void setSharedFormInputs() {
        sharedFormInputs = binding.formInputsContainer;
    }
}
