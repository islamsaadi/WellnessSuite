package com.islamsaadi.wellnesscore;

import android.graphics.Color;

public class HealthCalculator {

    public static class BMICategoryResult {
        public final String category;
        public final int color;

        public BMICategoryResult(String category, int color) {
            this.category = category;
            this.color = color;
        }
    }

    public static double calculateBMI(double weightKg, double heightCm) {
        double heightMeters = heightCm / 100.0;
        return weightKg / (heightMeters * heightMeters);
    }

    public static double calculateWaterIntake(double weightKg) {
        // A common recommendation is about 35 mL per kg.
        // Convert mL to liters by dividing 1000.
        return (weightKg * 35) / 1000.0;
    }


    public static double calculateBMR(String gender, int age, double weightKg, double heightCm) {
        if (gender.equalsIgnoreCase("male")) {
            return 10 * weightKg + 6.25 * heightCm - 5 * age + 5;
        } else {
            return 10 * weightKg + 6.25 * heightCm - 5 * age - 161;
        }
    }

    public static double estimateCalories(double bmr, String activityLevel) {
        switch (activityLevel.toLowerCase()) {
            case "light":
                return bmr * 1.375;
            case "moderate":
                return bmr * 1.55;
            case "active":
                return bmr * 1.725;
            default:
                return bmr * 1.2;
        }
    }

    public static BMICategoryResult getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return new BMICategoryResult("Underweight", Color.RED);
        } else if (bmi < 25) {
            // Normal weight – green
            return new BMICategoryResult("Normal weight", Color.rgb(9, 121, 105));
        } else if (bmi < 30) {
            // Overweight – orange
            return new BMICategoryResult("Overweight", Color.rgb(255, 165, 0));
        } else {
            // Obesity – red
            return new BMICategoryResult("Obesity", Color.RED);
        }
    }
}
