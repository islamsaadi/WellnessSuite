package com.islamsaadi.wellnessdiet;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.islamsaadi.wellnesscore.BaseMainActivity;
import com.islamsaadi.wellnesscore.HealthCalculator;
import com.islamsaadi.wellnessdiet.databinding.ActivityMainDietBinding;

public class MainActivityDiet extends BaseMainActivity {
    private ActivityMainDietBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the main activity layout
        binding = ActivityMainDietBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setSharedHeader();
        this.setSharedFormInputs();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.activity_levels,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerActivity.setAdapter(adapter);

        binding.btnCalcBMIANDBMR.setOnClickListener(v -> {
            String ageStr    = binding.editAge.getText().toString().trim();
            String weightStr = sharedFormInputs.editWeight.getText().toString().trim();
            String heightStr = sharedFormInputs.editHeight.getText().toString().trim();

            if (TextUtils.isEmpty(ageStr) ||
                    TextUtils.isEmpty(weightStr) ||
                    TextUtils.isEmpty(heightStr)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int    age    = Integer.parseInt(ageStr);
                double weight = Double.parseDouble(weightStr);
                double height = Double.parseDouble(heightStr);
                String gender = getSelectedGender();

                // BMI + category + color
                double bmi = HealthCalculator.calculateBMI(weight, height);
                var    cat = HealthCalculator.getBMICategory(bmi);
                binding.resultBMI
                        .setText("BMI: " + String.format("%.2f", bmi) + "\n" + cat.category);
                binding.resultBMI.setTextColor(cat.color);

                // BMR
                double bmr = HealthCalculator.calculateBMR(gender, age, weight, height);
                binding.resultBMR.setText("BMR: " + String.format("%.2f", bmr));
            } catch (NumberFormatException ex) {
                Toast.makeText(this, "Invalid numbers entered", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnCalcCalories.setOnClickListener(v -> {
            String ageStr    = binding.editAge.getText().toString().trim();
            String weightStr = sharedFormInputs.editWeight.getText().toString().trim();
            String heightStr = sharedFormInputs.editHeight.getText().toString().trim();

            if (TextUtils.isEmpty(ageStr) ||
                    TextUtils.isEmpty(weightStr) ||
                    TextUtils.isEmpty(heightStr)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int    age    = Integer.parseInt(ageStr);
                double weight = Double.parseDouble(weightStr);
                double height = Double.parseDouble(heightStr);
                String gender = getSelectedGender();

                double bmr = HealthCalculator.calculateBMR(gender, age, weight, height);
                String activityLevel = binding
                        .spinnerActivity
                        .getSelectedItem()
                        .toString()
                        .toLowerCase();
                double calories = HealthCalculator.estimateCalories(bmr, activityLevel);
                binding.resultCalories.setText(
                        "Calories: " + String.format("%.2f", calories)
                );
            } catch (NumberFormatException ex) {
                Toast.makeText(this, "Invalid numbers entered", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setSharedHeader() {
        sharedHeader = binding.headerContainer;
        sharedHeader.headerLogo.setImageResource(R.drawable.logo_diet);
    }

    @Override
    protected void setSharedFormInputs() {
        sharedFormInputs = binding.formInputsContainer;
    }

    private String getSelectedGender() {
        int id = binding.radioGroupGender.getCheckedRadioButtonId();
        if (id == binding.radioMale.getId())   return "male";
        if (id == binding.radioFemale.getId()) return "female";
        return "male";
    }
}
